pragma solidity ^0.4.23;

import "./Ownable.sol";
import "./librarys/strings.sol";
import "./FeeManager.sol";
import "./Vss.sol";

contract Trade is Ownable {

    using strings for *;

    // Separate to split a string
    string constant SEPARATE = ",";

    // Contract controls fee payment of host/arbitraion
    FeeManager feeManager;
    // Contract validate encrypted shard, recover buyer/seller's private key from shard
    Vss vss;

    struct TradeOrder {

        // Buyer of a order
        address buyer;

        // Seller of a order
        address seller;

        // Hoster whose public key used to encrypt buyer/seller's shard
        // Hoster type => hoster ids
        mapping(uint256 => address[]) hosters;

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
        address indexed seller
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


    // Function can only be called by buyer's shard hoster
    modifier onlyBuyerHoster(uint256 _orderID) {
        require(isUserHoster(_orderID, UpdateTo.Buyer));
        _;
    }


    // Function can only be called by buyer's shard hoster
    modifier onlySellerHoster(uint256 _orderID) {
        require(isUserHoster(_orderID, UpdateTo.Seller));
        _;
    }


    // Init with feeManager and vss
    function Trade(address _feeManager, address _vss) public {
        require(_feeManager != address(0));
        require(_vss != address(0));

        feeManager = FeeManager(_feeManager);
        vss = Vss(_vss);
    }


    // TODO workflow may be changed to this situation ?:
    // TODO 1. buyer create/upload a new order
    // TODO 2. seller confirm info on chain
    /**
     * @dev Create a new order
     * @param _orderID ID of a order
     * @param _buyer Buyer of a order
     * @param _seller Seller of a order
     */
    function createNewTradeOrder(
        uint256 _orderID,
        address _buyer,
        address _seller
    )
        public
        onlyOwner
        returns(bool)
    {
        // Ensure orderID haven't been used before
        require(order[_orderID].buyer == address(0));
        require(order[_orderID].seller == address(0));
        require(_buyer != address(0));
        require(_seller != address(0));

        order[_orderID].buyer = _buyer;
        order[_orderID].seller = _seller;
        CreateOrder(_orderID, _buyer, _seller);
        return true;
    }


    /**
     * @dev Seller upload buyer's encrypted shard, which treated as requesting arbitration
     * @param _orderID ID of order
     * @param _shard Encrypted shard of buyer's private key
     * @param _hosterID Hoster list used to encrypt buyer's private key
     */
    function uploadBuyerShardFromSeller(
        uint256 _orderID,
        string _shard,
        address[] _hosterID
    )
        public
        onlySeller(_orderID)
        returns(bool)
    {
        // Pay arbitraion fee
        feeManager.payFee(_orderID, feeManager.getArbitrationServiceType(), msg.sender, _hosterID);
        return uploadEncryptedShard(_orderID, UpdateTo.Buyer, _shard, _hosterID);
    }


    /**
     * @dev Seller upload buyer's encrypted shard, which treated as requesting arbitration
     * @param _orderID ID of order
     * @param _shard Encrypted shard of buyer's private key
     * @param _hosterID Hoster list used to encrypt buyer's private key
     */
     function uploadSellerShardFromBuyer(
        uint256 _orderID,
        string _shard,
        address[] _hosterID
    )
        public
        onlyBuyer(_orderID)
        returns(bool)
    {
        // Pay arbitraion fee
        feeManager.payFee(_orderID, feeManager.getArbitrationServiceType(), msg.sender, _hosterID);
        return uploadEncryptedShard(_orderID, UpdateTo.Seller, _shard, _hosterID);
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
     * @dev Get buyer's hosters
     * @param _orderID ID of order
     */
    function getBuyerShardHosters(uint256 _orderID) public view returns(address[]) {
        return getHosters(_orderID, UpdateTo.Buyer);
    }


    /**
     * @dev Get seller's hosters
     * @param _orderID ID of order
     */
    function getSellerShardHosters(uint256 _orderID) public view returns(address[]) {
        return getHosters(_orderID, UpdateTo.Seller);
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
     * @param _orderID ID of order
     * @param _decryptedShard Decrypted shard of buyer's private key
     */
    function uploadBuyerDecryptedShard(
        uint256 _orderID,
        string _decryptedShard
    )
        public
        onlyBuyerHoster(_orderID)
        returns(bool)
    {
        // Upload buyer's shard means seller has win the conflict
        require(order[_orderID].judgeInfo.state != WhoWin.Buyer);
        // Hoster haven't upload seller's shard
        require(order[_orderID].decryptedShard[uint256(UpdateTo.Seller)][msg.sender].shard.toSlice().empty());

        uploadDecryptedShard(_orderID, UpdateTo.Buyer, _decryptedShard);
        return true;
    }


    /**
     * @dev Hoster upload seller's decrypted shard
     * @param _orderID ID of order
     * @param _decryptedShard Decrypted shard of seller's private key
     */
    function uploadSellerDecryptedShard(
        uint256 _orderID,
        string _decryptedShard
    )
        public
        onlySellerHoster(_orderID)
        returns(bool)
    {
        // Upload seller's shard means buyer has win the conflict
        require(order[_orderID].judgeInfo.state != WhoWin.Seller);
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
        if (order[_orderID].decryptedShardLen[storageType] >= vss.GetN()) {
            // This mean who wins
            if (_userType == UpdateTo.Buyer) {
                order[_orderID].judgeInfo.state = WhoWin.Seller;
            }

            if (_userType == UpdateTo.Seller) {
                order[_orderID].judgeInfo.state = WhoWin.Buyer;
            }
        }

        UploadDecryptedShards(_orderID, msg.sender, _userType, _decryptedShard);
        return true;
    }


    /**
     * @dev Validate a hoster of a user
     * @param _orderID ID of order
     * @param _userType Type of user
     */
    function isUserHoster(
        uint256 _orderID, 
        UpdateTo _userType
    ) 
        public 
        view 
        returns(bool) 
    {
        bool isHoster = false;
        uint256 storageType = uint256(_userType);
        for (uint256 i = 0; i < order[_orderID].hosters[storageType].length; i++) {
            if (order[_orderID].hosters[storageType][i] == msg.sender) {
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
     * @param _hosters Hosters used to encrypt shard of private key
     */
    function uploadEncryptedShard(
        uint256 _orderID,
        UpdateTo _updateType,
        string _encryptedShard,
        address[] _hosters
    )
        internal
        returns(bool)
    {
        var delim = SEPARATE.toSlice();
        var s = _encryptedShard.toSlice();
        var shardArrayLength = s.count(delim) + 1;
        // _shard like "encryptedShard,encryptedShard,......"
        // it's length should be same with _hosterID
        require(shardArrayLength == _hosters.length);

        var storageType = uint256(_updateType);

        // Will cover value before if this is not the first time
        order[_orderID].hosters[storageType] = _hosters;
        for (uint256 i = 0; i < shardArrayLength; i++) {
            var tmpShard = s.split(delim).toString();

            order[_orderID].hosterShard[_hosters[i]][storageType].shard = tmpShard;
        }

        UploadEncryptedShard(_orderID, msg.sender, _updateType, _encryptedShard);
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
     * @dev Get buyer/seller's hosters whose public key used to encrypt shard
     * @param _orderID ID of order
     * @param _userType Type of user
     */
    function getHosters(uint256 _orderID, UpdateTo _userType) internal view returns(address[]) {
        return order[_orderID].hosters[uint256(_userType)];
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
}
