pragma solidity ^0.4.23;

import "./Ownable.sol";
import "./librarys/strings.sol";
import "./User.sol";

contract TradeBasic {
    // Separate to split a string
    string constant SEPARATE = ",";

    // Number of hoster will be selected
    uint256 public hosterNumber = 5;

    // Hoster contract
    Hoster public hosterContract;

    mapping(uint256 => TradeOrder) order;

    enum OrderStatus { Created, Canceled, Confirmed, SecretUploaded, Finished }

    struct TradeOrder {
        // Creator
        address creator;

        // Buyer of a order
        address buyer;

        // Seller of a order
        address seller;

        // Buyer/Seller upload secret or not
        mapping(address => bool) uploaded;

        // Hoster whose public key used to encrypt buyer/seller's shard
        address[] hosters;

        // Hosted buyer/seller's encrypted shard
        // Hoster's id(address) => user type => info
        mapping(address => mapping(uint256 => EncryptedShard)) hosterShard;

        OrderStatus status;
    }

    struct EncryptedShard {
        // Encrypted shard string
        string shard;
    }


    // Function can only be called by ThemisUser
    modifier onlyThemisUser() {
        require(hosterContract != address(0));
        require(hosterContract.isThemisUser(msg.sender));
        _;
    }

    // Buyer/Seller can call
    modifier onlyTrader(uint256 orderID) {
        require(order[orderID].buyer == msg.sender || order[orderID].seller == msg.sender);
        _;
    }
}


// Update contract info
contract InfoManageable is TradeBasic, Ownable {

    event UpdateDefaultHosterNumber(uint256 newNumber);

    event UpdateHosterContract(address indexed newAddress);

    /**
     * @dev Update default number of hoster used for service
     * @param _hosterNumber Number of default hosters
     */
    function updateDefaultHosterNumber(uint256 _hosterNumber) public onlyOwner returns(bool) {
        // hosterNumber should bigger than 3 and be a even number
        require(_hosterNumber >= 3);
        require(_hosterNumber % 2 != 0);

        hosterNumber = _hosterNumber;
        emit UpdateDefaultHosterNumber(_hosterNumber);
        return true;
    }


    /**
     * @dev Update address of hoster contract
     * @param _hoster Address of hoster contract
     */
    function updateHosterContract(address _hoster) public onlyOwner returns(bool) {
        require(_hoster != address(0));

        hosterContract = Hoster(_hoster);
    }
}


contract OrderManageable is TradeBasic, Ownable {

    using strings for *;

    // Type of user of a contract
    enum UserType { Buyer, Seller }

    event CreateOrder(uint256 orderID, address indexed user, UserType userType);

    event CancelTrade(uint256 orderID, address indexed creator);

    event ConfirmTradeOrder(uint256 orderID, address indexed user, address[] hosters);

    event UploadSecret(uint256 orderID, address indexed uploader, string secrets);

    event FinishOrder(uint256 orderID);

    // User type should only be Buyer or Seller
    modifier onlyValidateType(UserType userType) {
        require(userType == UserType.Buyer || userType == UserType.Seller);
        _;
    }


    // Can only be called by creator
    modifier onlyCreator(uint256 orderID) {
        require(order[orderID].creator == msg.sender);
        _;
    }


    /**
     * @dev Buyer/Seller create a new trade order
     * @param orderID ID of a order
     * @param userType User type of msg.sender
     */
    function createNewTradeOrder(uint256 orderID, UserType userType) public payable onlyThemisUser onlyValidateType(userType) returns(bool) {
        // Ensure orderID haven't been used before
        require(order[orderID].buyer == address(0));
        require(order[orderID].seller == address(0));

        if (UserType.Buyer == userType) {
            order[orderID].buyer = msg.sender;
        }
        if (UserType.Seller == userType) {
            order[orderID].seller = msg.sender;
        }
        order[orderID].creator = msg.sender;
        order[orderID].status = OrderStatus.Created;

        // TODO Pay for hoster's

        emit CreateOrder(orderID, msg.sender, userType);
        return true;
    }


    /**
     * @dev Creator cancel the order
     * @param orderID ID of order
     */
    function cancelTrade(uint256 orderID) public onlyCreator(orderID) returns(bool) {
        // Ensure order is created
        require(order[orderID].status == OrderStatus.Created);

        order[orderID].status = OrderStatus.Canceled;
        // TODO get the fee back
        emit CancelTrade(orderID, msg.sender);
        return true;
    }


    /**
     * @dev Seller/Buyer confirm order
     */
    function confirmTradeOrder(uint256 orderID) public onlyThemisUser payable returns(bool) {
        // Ensure order is created
        require(order[orderID].status == OrderStatus.Created);

        if (order[orderID].buyer != address(0)) {
            // User can not confirm a order created by himself
            require(order[orderID].buyer != msg.sender);
            order[orderID].seller = msg.sender;
        } else if (order[orderID].seller != address(0)) {
            // User can not confirm a order created by himself
            require(order[orderID].seller != msg.sender);
            order[orderID].buyer = msg.sender;
        }

        order[orderID].status = OrderStatus.Confirmed;

        // Get hosters
        address[] memory hosters = hosterContract.getHosters(hosterNumber);

        // hosters.length should be same with hosterNumber
        require(hosters.length == hosterNumber);

        order[orderID].hosters = hosters;
        // TODO pay for service

        emit ConfirmTradeOrder(orderID, msg.sender, hosters);
    }


    /**
     * @dev User upload encrypted shard's
     * @dev Shard's order should be same as hoster's order
     * @param orderID ID of order
     * @param secrets Secrets of trader's private key
     */
    function uploadSecret(
        uint256 orderID,
        string secrets
    )
        public
        onlyTrader(orderID)
        returns(bool)
    {
        // Ensure the order is confirmed
        require(order[orderID].status == OrderStatus.Confirmed);

        var delim = SEPARATE.toSlice();
        var s = secrets.toSlice();
        var shardArrayLength = s.count(delim) + 1;

        // Shard length should be same with hosters length
        address[] memory hosters = order[orderID].hosters;
        require(shardArrayLength == hosters.length);

        uint256 storageType;

        //
        if (order[orderID].buyer == msg.sender) {
            storageType = uint256(UserType.Seller);
        }
        if (order[orderID].seller == msg.sender) {
            storageType = uint256(UserType.Buyer);
        }

        // Will cover value before if this is not the first time
        for (uint256 i = 0; i < shardArrayLength; i++) {
            var tmpShard = s.split(delim).toString();

            order[orderID].hosterShard[hosters[i]][storageType].shard = tmpShard;
        }

        order[orderID].uploaded[msg.sender] = true;
        updateStatusWhenSecretUploaded(orderID);

        emit UploadSecret(orderID, msg.sender, secrets);
        return true;
    }


    /**
     * @dev Finish the order // TODO change / send fee to hosters
     */
    function finishOrder(uint256 orderID) public onlyOwner returns(bool) {
        // Ensure secret have been uploaded
        require(order[orderID].status == OrderStatus.SecretUploaded);

        order[orderID].status = OrderStatus.Finished;
        emit FinishOrder(orderID);
        return true;
    }


    /**
     * @dev Get encrypted shard of seller
     * @param orderID ID of order
     * @param hosterID ID of hoster
     * @param userType Whose secret you want to get
     */
    function getSecret(
        uint256 orderID,
        address hosterID,
        UserType userType
    )
        public
        view
        returns(string)
    {
        return order[orderID].hosterShard[hosterID][uint256(userType)].shard;
    }


    /**
     * @dev Get buyer of order
     * @param orderID ID of order
     */
    function getOrderBuyer(uint256 orderID) public view returns(address) {
        return order[orderID].buyer;
    }


    /**
     * @dev Get seller of order
     * @param orderID ID of order
     */
    function getOrderSeller(uint256 orderID) public view returns(address) {
        return order[orderID].seller;
    }


    /**
     * @dev Get hosters
     * @param orderID ID of order
     */
    function getOrderHosters(uint256 orderID) public view returns(address[]) {
        return order[orderID].hosters;
    }

    /**
     * @dev Check user ia a order's hoster or not
     * @param orderID ID of order
     */
    function isOrderHoster(
        uint256 orderID,
        address user
    )
        public
        view
        returns(bool)
    {
        bool isHoster = false;
        for (uint256 i = 0; i < order[orderID].hosters.length; i++) {
            if (order[orderID].hosters[i] == user) {
                isHoster = true;
                break;
            }
        }

        return isHoster;
    }


    /**
     * @dev Update order status if buyer and seller all uploaded secret
     */
    function updateStatusWhenSecretUploaded(uint256 orderID) internal returns(bool){
        address buyer = order[orderID].buyer;
        address seller = order[orderID].seller;

        if (order[orderID].uploaded[buyer] == true && order[orderID].uploaded[seller] == true) {
            order[orderID].status = OrderStatus.SecretUploaded;
        }

        return true;
    }
}

contract ArbitratorManageable is Ownable {
    // Arbitrator
    mapping(address => bool) arbitrator;

    event AddArbitrator(address indexed who);

    event RemoveArbitrator(address indexed who);

    modifier onlyArbitrator() {
        require(arbitrator[msg.sender] == true);
        _;
    }


    /**
     * @dev Owner add arbitrator
     * @param who Address will be added
     */
    function addArbitrator(address who) public onlyOwner returns(bool) {
        arbitrator[who] = true;
        emit AddArbitrator(who);
        return true;
    }


    /**
     * @dev Owner remove arbitrator
     * @param who Address will be removed
     */
    function removeArbitrator(address who) public onlyOwner returns(bool) {
        delete(arbitrator[who]);
        emit RemoveArbitrator(who);
        return true;
    }


    /**
     * @dev Check a address is judge or not
     */
    function isArbitrator(address who) public view returns(bool) {
        return arbitrator[who] == true;
    }
}


//
contract Arbitrable is TradeBasic, ArbitratorManageable {

    // Arbitration of order;
    mapping(uint256 => ArbitrateInfo) arbitration;

    struct ArbitrateInfo {
        // Who arbitrate
        address requester;

        // Who wins
        address winner;
    }

    event Arbitrate(uint256 orderID, address indexed user);

    event Judge(uint256 orderID, address indexed winner, address indexed judge);


    /**
     * @dev User arbitrate
     * @param orderID ID of order want to arbitrate for
     */
    function arbitrate(uint256 orderID) public onlyTrader(orderID) returns(bool) {
        // Not necessary check order(check by onlyTrader)
        // Have not arbitrate before
        require(arbitration[orderID].requester == address(0));

        // TODO pay for service

        arbitration[orderID].requester = msg.sender;
        emit Arbitrate(orderID, msg.sender);
        return true;
    }


    /**
     * @dev Arbitrator judge who wins
     * @param orderID ID of order
     * @param winner User who wins
     */
    function judge(uint256 orderID, address winner) public onlyArbitrator returns(bool) {
        // Ensure order is in arbitration
        require(arbitration[orderID].requester != address(0));

        // Winner must be one of buyer/seller
        require(winner == order[orderID].buyer || winner == order[orderID].seller);

        arbitration[orderID].winner = winner;
        // TODO send fee to judge
        emit Judge(orderID, winner, msg.sender);
        return true;
    }


    /**
     * @dev Get arbitration requester
     * @param orderID ID of order
     */
    function getRequester(uint256 orderID) public view returns(address) {
        return arbitration[orderID].requester;
    }


    /**
     * @dev Get arbitration winner
     * @param orderID ID of order
     */
    function getWinner(uint256 orderID) public view returns(address) {
        return arbitration[orderID].winner;
    }
}


// Main contract
contract Trade is Arbitrable, OrderManageable, InfoManageable {


}
