pragma solidity ^0.4.23;

import "./Ownable.sol";
import "./librarys/strings.sol";
import "./FeeManager.sol";
import "./Vss.sol";
import "./User.sol";

contract Trade is Ownable {

    using strings for *;

    // Separate to split a string
    string constant SEPARATE = ",";

    uint256 public maxHosterNum = 11;

    // Contract controls fee payment of host/arbitraion
    FeeManager public feeManager;
    // Contract validate encrypted shard, recover buyer/seller's private key from shard
    Vss public vss;
    // Hoster contract
    Hoster public hosterContract;

    struct TradeOrder {

        // Buyer of a order
        address buyer;

        // Seller of a order
        address seller;

        // Hoster whose public key used to encrypt buyer/seller's shard
        address[] hosters;

        // Hosted buyer/seller's encrypted shard
        // Hoster's id(address) => user type => info
        mapping(address => mapping(uint256 => EncryptedShard)) hosterShard;

        // Decrypted shard
        mapping(uint256 => mapping(address => EncryptedShard)) decryptedShard;

        // Record how many shard of buyer/seller have been uploaded
        // User type => len
        mapping(uint256 => uint256) decryptedShardLen;

        // Info of judge
        JudgeInfo judgeInfo;

        // Number at least to recover private key
        uint256 N;
    }

    // Type of data user want to update
    enum UpdateTo { Buyer, Seller }

    // Type of user who win the conflict
    enum WhoWin { Init, Buyer, Seller }

    struct EncryptedShard {
        // Encrypted shard string
        string shard;
    }

    struct JudgeInfo {
        // Private key recovered only when conflict occurs
        string recoverPrivKey;

        // state of this order
        // can indicate who(user/seller) wins when conflict occurs
        WhoWin state;
    }

    mapping(uint256 => TradeOrder) order;

    event CreateOrder(
        uint256 orderID, 
        address indexed buyer,
        address indexed seller,
        address[] hosters
    );

    event UploadEncryptedShard(
        uint256 orderID,
        address indexed who,
        UpdateTo userType,
        string  shards
    );

    event RecoverPrivKey(
        uint256 orderID,
        UpdateTo userType,
        string  privKey
    );

    event UploadDecryptedShards(
        uint256 orderID,
        address indexed hoster,
        UpdateTo userType,
        string  decryptedShard
    );


    // Function can only be called by buyer
    modifier onlyBuyer(uint256 _orderID) {
        require(msg.sender == order[_orderID].buyer);
        _;
    }


    // Function can only be called by seller
    modifier onlySeller(uint256 _orderID) {
        require(msg.sender == order[_orderID].seller);
        _;
    }


    // Function can only be called by shard hoster
    modifier onlyHoster(uint256 _orderID) {
        require(isUserHoster(_orderID));
        _;
    }


    // Function can only be called by ThemisUser
    modifier onlyThemisUser() {
        require(hosterContract != address(0));
        require(hosterContract.isThemisUser(msg.sender));
        _;
    }


    // Init with feeManager and vss
    function Trade(address _feeManager, address _vss) public {
        require(_feeManager != address(0));
        require(_vss != address(0));

        feeManager = FeeManager(_feeManager);
        vss = Vss(_vss);
    }


    // Update hoster contract address
    function updateHosterContract(address _hoster) public onlyOwner returns(bool) {
        require(_hoster != address(0));

        hosterContract = Hoster(_hoster);
        return true;
    }


    // Update max number of hosers a order can have
    function updateMaxHosterNum(uint256 _maxHosterNum) public onlyOwner returns(bool) {
        require(_maxHosterNum >= 3);

        maxHosterNum = _maxHosterNum;
        return true;
    }


    /**
     * @dev Create a new order
     * @param _orderID ID of a order
     * @param _seller Seller of a order
     * @param _hosterNum Number of hosters will be used for host/encrypt
     */
    function createNewTradeOrder(
        uint256 _orderID,
        address _seller,
        uint256 _hosterNum
    )
        public
        payable
        onlyThemisUser
        returns(bool)
    {
        // Ensure orderID haven't been used before
        require(order[_orderID].buyer == address(0));
        require(order[_orderID].seller == address(0));
        require(_seller != address(0));
        // _hosterNum should be a even number, support by shamir
        require(_hosterNum % 2 != 0);
        require(_hosterNum <= maxHosterNum);

        // _seller should be themis user too
        require(hosterContract.isThemisUser(_seller));

        order[_orderID].seller = _seller;
        order[_orderID].buyer = msg.sender;

        // Get hosters
        address[] memory hosters = hosterContract.getHosters(_hosterNum);

        // Host/arbitration need at least three hosters
        require(hosters.length >= 3);

        // Hoster.length should be even
        // Occur when hosterNum bigger than length of all hosters
        if (hosters.length > 0 && hosters.length % 2 == 0) {
            address[] memory res = getPreviousItems(hosters, hosters.length - 1);
            order[_orderID].hosters = res;
            order[_orderID].N = res.length / 2 + 1;
            assert(feeManager.payFee.value(msg.value)(_orderID, feeManager.getHostServiceType(), msg.sender, res));
            emit CreateOrder(_orderID, msg.sender, _seller, res);
            return true;
        }

        order[_orderID].hosters = hosters;
        order[_orderID].N = hosters.length / 2 + 1;

        assert(feeManager.payFee.value(msg.value)(_orderID, feeManager.getHostServiceType(), msg.sender, hosters));
        emit CreateOrder(_orderID, msg.sender, _seller, hosters);
        return true;
    }


    /**
     * @dev Seller upload buyer's encrypted shard, which treated as requesting arbitration
     * @param _orderID ID of order
     * @param _shard Encrypted shard of buyer's private key
     */
    function uploadBuyerShardFromSeller(
        uint256 _orderID,
        string _shard
    )
        public
        payable
        onlySeller(_orderID)
        returns(bool)
    {
        // Pay arbitration fee
        address[] memory _hosterID = order[_orderID].hosters;
        assert(feeManager.payFee.value(msg.value)(_orderID, feeManager.getArbitrationServiceType(), msg.sender, _hosterID));
        return uploadEncryptedShard(_orderID, UpdateTo.Buyer, _shard);
    }


    /**
     * @dev Seller upload buyer's encrypted shard, which treated as requesting arbitration
     * @param _orderID ID of order
     * @param _shard Encrypted shard of buyer's private key
     */
     function uploadSellerShardFromBuyer(
        uint256 _orderID,
        string _shard
    )
        public
        payable
        onlyBuyer(_orderID)
        returns(bool)
    {
        // Pay arbitration fee
        address[] memory _hosterID = order[_orderID].hosters;
        assert(feeManager.payFee.value(msg.value)(_orderID, feeManager.getArbitrationServiceType(), msg.sender, _hosterID));
        return uploadEncryptedShard(_orderID, UpdateTo.Seller, _shard);
    }


    /**
     * @dev Get encrypted shard of buer
     * @param _orderID ID of order
     * @param _hosterID ID of hoster
     */
    function getBuyerShardByHosterID(
        uint256 _orderID,
        address _hosterID
    )
        public
        view
        returns(string)
    {
        return getShardByHosterID(_orderID, UpdateTo.Buyer, _hosterID);
    }


    /**
     * @dev Get encrypted shard of seller
     * @param _orderID ID of order
     * @param _hosterID ID of hoster
     */
    function getSellerShardByHosterID(
        uint256 _orderID,
        address _hosterID
    )
        public
        view
        returns(string)
    {
        return getShardByHosterID(_orderID, UpdateTo.Seller, _hosterID);
    }


    /**
     * @dev Get buyer of order
     * @param _orderID ID of order
     */
    function getBuyer(uint256 _orderID) public view returns(address) {
        return order[_orderID].buyer;
    }


    /**
     * @dev Get seller of order
     * @param _orderID ID of order
     */
    function getSeller(uint256 _orderID) public view returns(address) {
        return order[_orderID].seller;
    }


    /**
     * @dev Get hosters
     * @param _orderID ID of order
     */
    function getShardHosters(uint256 _orderID) public view returns(address[]) {
        return order[_orderID].hosters;
    }


    /**
     * @dev Get buyer's decrypted shard which decrypted by hoster
     * @param _orderID ID of order
     * @param _hoster ID of hoster who decrypte shard
     */
    function getBuyerDecryptedShard(
        uint256 _orderID,
        address _hoster
    )
        public
        onlySeller(_orderID)
        view
        returns(string)
    {
        return getDecryptedShard(_orderID, UpdateTo.Buyer, _hoster);
    }


    /**
     * @dev Get seller's decrypted shard which decrypted by hoster
     * @param _orderID ID of order
     * @param _hoster ID of hoster who decrypte shard
     */
    function getSellerDecryptedShard(
        uint256 _orderID,
        address _hoster
    )
        public
        onlyBuyer(_orderID)
        view
        returns(string)
    {
        return getDecryptedShard(_orderID, UpdateTo.Seller, _hoster);
    }


    /**
     * @dev Hoster upload buyer's decrypted shard
     * @dev Hoster can only upload one's shard of buyer or seller
     * @param _orderID ID of order
     * @param _decryptedShard Decrypted shard of buyer's private key
     */
    function uploadBuyerDecryptedShard(
        uint256 _orderID,
        string _decryptedShard
    )
        public
        onlyHoster(_orderID)
        returns(bool)
    {
        // Hoster haven't upload seller's shard
        require(order[_orderID].decryptedShard[uint256(UpdateTo.Seller)][msg.sender].shard.toSlice().empty());

        uploadDecryptedShard(_orderID, UpdateTo.Buyer, _decryptedShard);
        return true;
    }


    /**
     * @dev Hoster upload seller's decrypted shard
     * @dev Hoster can only upload one's shard of buyer or seller
     * @param _orderID ID of order
     * @param _decryptedShard Decrypted shard of seller's private key
     */
    function uploadSellerDecryptedShard(
        uint256 _orderID,
        string _decryptedShard
    )
        public
        onlyHoster(_orderID)
        returns(bool)
    {
        // Hoster haven't upload seller's shard
        require(order[_orderID].decryptedShard[uint256(UpdateTo.Buyer)][msg.sender].shard.toSlice().empty());

        uploadDecryptedShard(_orderID, UpdateTo.Seller, _decryptedShard);
        return true;
    }


    /**
     * @dev Hoster upload decrypted shard which indicate that he judged buyer/seller has win the conflict
     * @dev Upload buyer shard means Seller has win the conflict
     * @param _orderID ID of order
     * @param _userType Whose' shard you upload
     * @param _decryptedShard Decrypted shard of user type's private key
     */
    function uploadDecryptedShard(
        uint256 _orderID,
        UpdateTo _userType,
        string _decryptedShard
    )
        internal
        returns(bool)
    {
        // Check this share is right or not
        require(vss.verifyShare(_decryptedShard));

        uint256 storageType = uint256(_userType);

        // Indicate first upload
        if (order[_orderID].decryptedShard[storageType][msg.sender].shard.toSlice().empty()) {
            order[_orderID].decryptedShardLen[storageType] = order[_orderID].decryptedShardLen[storageType] + 1;
        }
        order[_orderID].decryptedShard[storageType][msg.sender].shard = _decryptedShard;

        // Cover private key when shards length is bigger than N
        // TODO wait for vss completed to change
        if (order[_orderID].decryptedShardLen[storageType] >= order[_orderID].N) {
            // This mean who wins
            if (_userType == UpdateTo.Buyer) {
                order[_orderID].judgeInfo.state = WhoWin.Seller;
            }

            if (_userType == UpdateTo.Seller) {
                order[_orderID].judgeInfo.state = WhoWin.Buyer;
            }
        }

        emit UploadDecryptedShards(_orderID, msg.sender, _userType, _decryptedShard);
        return true;
    }


    /**
     * @dev Validate a hoster of a user
     * @param _orderID ID of order
     */
    function isUserHoster(
        uint256 _orderID
    )
        public
        view 
        returns(bool) 
    {
        bool isHoster = false;
        for (uint256 i = 0; i < order[_orderID].hosters.length; i++) {
            if (order[_orderID].hosters[i] == msg.sender) {
                isHoster = true;
                break;
            }
        }

        return isHoster;
    }

    /**
     * @dev Store user's encrypted shard
     * @param _orderID ID of order
     * @param _updateType Type of whose encrypted shard will be updated
     * @param _encryptedShard Encrypted shard of private key
     */
    function uploadEncryptedShard(
        uint256 _orderID,
        UpdateTo _updateType,
        string _encryptedShard
    )
        internal
        returns(bool)
    {
        var delim = SEPARATE.toSlice();
        var s = _encryptedShard.toSlice();
        var shardArrayLength = s.count(delim) + 1;

        // _shard like "encryptedShard,encryptedShard,......"
        // it's length should be same with _hosterID
        address[] memory _hosters = order[_orderID].hosters;
        require(shardArrayLength == _hosters.length);

        var storageType = uint256(_updateType);

        // Will cover value before if this is not the first time
        for (uint256 i = 0; i < shardArrayLength; i++) {
            var tmpShard = s.split(delim).toString();

            order[_orderID].hosterShard[_hosters[i]][storageType].shard = tmpShard;
        }

        emit UploadEncryptedShard(_orderID, msg.sender, _updateType, _encryptedShard);
        return true;
    }


    /**
     * @dev Get buyer/seller's encrypted shard of private key
     * @param _orderID ID of order
     * @param _userType Type of user whose encrypted shard you want to get
     * @param _hosterID ID of hoster
     */
    function getShardByHosterID(
        uint256 _orderID,
        UpdateTo _userType,
        address _hosterID
    )
        internal
        view
        returns(string)
    {
        return order[_orderID].hosterShard[_hosterID][uint256(_userType)].shard;
    }


    /**
     * @dev Get decrypted shard which is decrypted by hoster
     * @param _orderID ID of order
     * @param _userType Type of user whose decrypted shard want to get
     * @param _hoster ID of hoster who decrypte shard
     */
    function getDecryptedShard(
        uint256 _orderID,
        UpdateTo _userType,
        address _hoster
    )
        internal
        view
        returns(string)
    {
        return order[_orderID].decryptedShard[uint256(_userType)][_hoster].shard;
    }


    /**
     * @dev Return previous item of a address array
     * @param _ori Original address array
     */
    function getPreviousItems(address[] _ori, uint256 _num) internal pure returns(address[]) {
        require(_num <= _ori.length);

        address[] memory res = new address[](_num);
        for (uint256 i = 0; i < _num; i++) {
            res[i] = _ori[i];
        }

        return res;
    }
}
