pragma solidity ^0.4.23;

import "./Ownable.sol";
import "./librarys/strings.sol";
import "./Trustee.sol";
import "./librarys/SafeMath.sol";

contract TradeBasic {
    using SafeMath for uint256;

    // Separate to split a string
    string constant SEPARATE = ",";

    // Default number of trustee will be selected
    // Should less than 2^8(256)
    uint8 public trusteeNumber = 5;

    // Trustee contract
    Trustee public trusteeContract;

    // Order ID should less than 2^80(bigger than 10^21)
    mapping(uint80 => TradeOrder) order;

    // Currently, how many fee a trustee can get, which will increase automatically
    // when a order finished/judged;
    mapping(address => uint256) feeRemain;

    enum OrderStatus {
        Created,
        Canceled,
        Confirmed,
        SecretUploaded,
        VerifiedSuccess,
        InArbitration,
        Finished
    }

    struct TradeOrder {
        // Creator
        // For test-june version, creator can only be owner
        address creator;

        // Buyer uid
        // Should less than 2^32(4294967296)
        uint32 buyer;

        // Seller uid
        // Should less than 2^32(4294967296)
        uint32 seller;

        // buyer/seller's uid => amount of fee paid
        mapping(uint32 => uint256) feePaid;

        // Buyer/Seller upload secret or not
        mapping(uint32 => bool) uploaded;

        // Trustee whose public key used to encrypt buyer/seller's shard
        address[] trustees;

        // Indicate a address is this order's trustee or not
        mapping(address => bool) isTrustee;

        // buyer/seller's encrypted shard
        // Trustee's id(address) => user id => info
        mapping(address => mapping(uint32 => EncryptedShard)) trusteeShard;

        // The verify data of user's shard
        mapping(uint32 => string) verifyData;

        OrderStatus status;
    }

    struct EncryptedShard {
        // Encrypted shard string
        string shard;

        // Shard have been verified by trustee
        bool verified;

        // Shard is a right shard or not(result is sent by trustee)
        bool rightShard;
    }


    /**
      * @dev Get fee should sent to per trustee of a order
      */
    function getPerFeeOfOrder(uint80 orderID) public view returns(uint256) {
        uint32 buyer = order[orderID].buyer;
        uint32 seller = order[orderID].seller;
        uint256 buyerFeePaid = order[orderID].feePaid[buyer];
        uint256 sellerFeePaid = order[orderID].feePaid[seller];
        uint256 orderFeePaid = buyerFeePaid.add(sellerFeePaid);

        uint256 perFee = orderFeePaid.div(order[orderID].trustees.length);
        return perFee;
    }
}


// Update contract info
contract InfoManageable is TradeBasic, Ownable {

    event LogUpdateDefaultTrusteeNumber(uint256 newNumber);

    event LogUpdateTrusteeContract(address indexed newAddress);

    /**
     * @dev Update default number of trustee used for service
     * @param _trusteeNumber Number of default trustees
     */
    function updateDefaultTrusteeNumber(uint8 _trusteeNumber) public onlyOwner returns(bool) {
        // trusteeNumber should bigger than 3 and be a even number
        require(_trusteeNumber >= 3);
        require(_trusteeNumber % 2 != 0);

        trusteeNumber = _trusteeNumber;
        emit LogUpdateDefaultTrusteeNumber(_trusteeNumber);
        return true;
    }


    /**
     * @dev Update address of trustee contract
     * @param _trustee Address of trustee contract
     */
    function updateTrusteeContract(address _trustee) public onlyOwner returns(bool) {
        require(_trustee != address(0));

        trusteeContract = Trustee(_trustee);
        emit LogUpdateTrusteeContract(_trustee);
    }
}

contract Pausable is Ownable {
    event Pause();
    event Unpause();

    bool public paused = false;


    /**
     * @dev Modifier to make a function callable only when the contract is not paused.
     */
    modifier whenNotPaused() {
        require(!paused);
        _;
    }

    /**
     * @dev Modifier to make a function callable only when the contract is paused.
     */
    modifier whenPaused() {
        require(paused);
        _;
    }

    /**
     * @dev called by the owner to pause, triggers stopped state
     */
    function pause() onlyOwner whenNotPaused public {
        paused = true;
        emit Pause();
    }

    /**
     * @dev called by the owner to unpause, returns to normal state
     */
    function unpause() onlyOwner whenPaused public {
        paused = false;
        emit Unpause();
    }
}

contract OrderManageable is TradeBasic, Ownable, Pausable  {

    using strings for *;

    // Type of user of a contract
    enum UserType { Buyer, Seller }

    event LogCreateOrder(uint80 indexed orderID, uint32 indexed user, UserType userType, uint256 feePayed);

    event LogCancelTrade(uint80 indexed orderID, address indexed creator);

    event LogConfirmTradeOrder(uint80 indexed orderID, uint32 indexed user, address[] trustees, uint256 feePayed);

    event LogUploadSecret(uint80 indexed orderID, uint32 indexed user, string secrets);

    event LogVerifyResult(address indexed trustee, bool buyerResult, bool sellerResult);

    event LogFinishOrder(uint80 indexed orderID);

    event LogWithdrawFee(address indexed trustee, uint256 amount);

    // User type should only be Buyer or Seller
    modifier onlyValidateType(UserType userType) {
        require(userType == UserType.Buyer || userType == UserType.Seller);
        _;
    }


    // Can only be called by creator
    modifier onlyCreator(uint80 orderID) {
        require(order[orderID].creator == msg.sender);
        _;
    }


    // Can only be called by trustee of a order
    modifier onlyTrustee(uint80 orderID) {
        require(order[orderID].isTrustee[msg.sender] == true);
        _;
    }


    /**
     * @dev Buyer/Seller create a new trade order
     * @param orderID ID of a order
     * @param userType User type of msg.sender
     */
    function createNewTradeOrder(
        uint80 orderID,
        uint32 userID,
        UserType userType
    )
        external
        payable
        onlyOwner
        whenNotPaused
        onlyValidateType(userType)
        returns(bool)
    {
        // Ensure orderID haven't been used before
        require(order[orderID].buyer == 0);
        require(order[orderID].seller == 0);
        require(msg.value > 0);

        if (UserType.Buyer == userType) {
            order[orderID].buyer = userID;
        }
        if (UserType.Seller == userType) {
            order[orderID].seller = userID;
        }
        order[orderID].creator = msg.sender;
        order[orderID].status = OrderStatus.Created;

        // Record fee paid
        order[orderID].feePaid[userID] = msg.value;

        emit LogCreateOrder(orderID, userID, userType, msg.value);
        return true;
    }


    /**
     * @dev Creator cancel the order
     * @param orderID ID of order
     * @param createUserID User ID when create order
     */
    function cancelTrade(uint80 orderID, uint32 createUserID) external whenNotPaused onlyCreator(orderID) returns(bool) {
        // Ensure order is created
        require(order[orderID].status == OrderStatus.Created);
        require(order[orderID].feePaid[createUserID] > 0);

        order[orderID].status = OrderStatus.Canceled;
        // Send fee back to creator
        msg.sender.transfer(order[orderID].feePaid[createUserID]);
        emit LogCancelTrade(orderID, msg.sender);
        return true;
    }


    /**
     * @dev Seller/Buyer confirm order
     */
    function confirmTradeOrder(uint80 orderID, uint32 userID) external whenNotPaused onlyCreator(orderID) payable returns(bool) {
        // Ensure order is created
        require(order[orderID].status == OrderStatus.Created);
        require(msg.value > 0);

        if (order[orderID].buyer != 0) {
            // Buyer should not same with seller
            require(order[orderID].buyer != userID);
            order[orderID].seller = userID;
        } else if (order[orderID].seller != 0) {
            // Buyer should not same with seller
            require(order[orderID].seller != userID);
            order[orderID].buyer = userID;
        } else {
            revert();
        }

        order[orderID].status = OrderStatus.Confirmed;

        // Get trustees
        address[] memory trustees = trusteeContract.getTrustees(trusteeNumber);

        // trustees.length should be same with trusteeNumber
        require(trustees.length == trusteeNumber);
        for (uint8 i = 0; i < trustees.length; i++) {
            order[orderID].isTrustee[trustees[i]] = true;
        }

        order[orderID].trustees = trustees;
        // Record fee paid
        order[orderID].feePaid[userID] = msg.value;

        emit LogConfirmTradeOrder(orderID, userID, trustees, msg.value);
        return true;
    }


    /**
     * @dev User upload encrypted shard's
     * @dev Shard's order should be same as trustee's order
     * @param orderID ID of order
     * @param secrets Secrets of trader's private key
     * @param userID ID of user
     * @param verifyData Trustee will decode shard, and verify shard with this verifyData by shamir
     */
    function uploadSecret(
        uint80 orderID,
        string secrets,
        uint32 userID,
        string verifyData
    )
        external
        whenNotPaused
        onlyCreator(orderID)
        returns(bool)
    {
        // Ensure the order is confirmed
        require(order[orderID].status == OrderStatus.Confirmed);
        require(order[orderID].buyer == userID || order[orderID].seller == userID);

        strings.slice memory sep = SEPARATE.toSlice();
        strings.slice memory s = secrets.toSlice();

        // Shard length should be same with trustees length
        address[] memory trustees = order[orderID].trustees;
        require((s.count(sep) + 1) == trustees.length);

        // Will cover value before if this is not the first time
        for (uint8 i = 0; i < trustees.length; i++) {
            order[orderID].trusteeShard[trustees[i]][userID].shard = s.split(sep).toString();
            // In case of situation that user want to update some bad shard after
            // trustee verified right shard
            order[orderID].trusteeShard[trustees[i]][userID].rightShard = false;
            order[orderID].trusteeShard[trustees[i]][userID].verified = false;
        }

        order[orderID].uploaded[userID] = true;
        updateStatusWhenSecretUploaded(orderID);

        order[orderID].verifyData[userID] = verifyData;

        emit LogUploadSecret(orderID, userID, secrets);
        return true;
    }


    /**
     * @dev Trustee send back verify result
     * @param orderID ID of order
     * @param buyerResult Result verified by trustee, which is uploaded by buyer
     * @param sellerResult Result verified by trustee, which is uploaded by seller
     */
    function sendVerifyResult(
        uint80 orderID,
        bool buyerResult,
        bool sellerResult
    )
        external
        onlyTrustee(orderID)
        returns(bool)
    {
        require(order[orderID].status == OrderStatus.SecretUploaded);
        uint32 buyer = order[orderID].buyer;
        uint32 seller = order[orderID].seller;
        order[orderID].trusteeShard[msg.sender][buyer].rightShard = buyerResult;
        order[orderID].trusteeShard[msg.sender][buyer].verified = true;
        order[orderID].trusteeShard[msg.sender][seller].rightShard = sellerResult;
        order[orderID].trusteeShard[msg.sender][seller].verified = true;

        // Change order status to VerifiedSuccess if result of all shard verified by trustees is right
        updateStatusWhenShardVerified(orderID);

        emit LogVerifyResult(msg.sender, buyerResult, sellerResult);
    }


    /**
     * @dev Finish the order normally
     * @param orderID ID of order
     */
    function finishOrder(uint80 orderID) external whenNotPaused onlyCreator(orderID) returns(bool) {
        // Ensure shard have been right verified
        require(order[orderID].status == OrderStatus.VerifiedSuccess);

        order[orderID].status = OrderStatus.Finished;

        uint256 perFee = getPerFeeOfOrder(orderID);
        for (uint8 i = 0; i < order[orderID].trustees.length; i++) {
            address trustee = order[orderID].trustees[i];
            feeRemain[trustee] = feeRemain[trustee].add(perFee);
        }
        emit LogFinishOrder(orderID);
        return true;
    }


    /**
     * @dev Trustee withdraw fee back
     */
    function withdrawFee() external whenNotPaused returns(bool) {
        uint256 amount;
        amount = feeRemain[msg.sender];
        require(amount > 0);

        feeRemain[msg.sender] = 0;
        emit LogWithdrawFee(msg.sender, amount);

        // Send fee back to trustee
        msg.sender.transfer(amount);
        return true;
    }


    /**
     * @dev Get encrypted shard of seller
     * @param orderID ID of order
     * @param trusteeID ID of trustee
     * @param user ID of user Whose secret you want to get
     */
    function getSecret(
        uint80 orderID,
        address trusteeID,
        uint32 user
    )
        public
        view
        returns(string)
    {
        return order[orderID].trusteeShard[trusteeID][user].shard;
    }

    /**
     * @dev Get verify data of user's shard
     * @param orderID ID of order
     * @param user ID of user
     */
    function getVerifyData(
        uint80 orderID,
        uint32 user
    )
        public
        view
        returns(string)
    {
        return order[orderID].verifyData[user];
    }


    /**
     * @dev Get order status
     * @param orderID ID of order
     */
    function getOrderStatus(uint80 orderID) public view returns(OrderStatus) {
        return order[orderID].status;
    }


    /**
     * @dev Get buyer of order
     * @param orderID ID of order
     */
    function getOrderBuyer(uint80 orderID) public view returns(uint256) {
        return order[orderID].buyer;
    }


    /**
     * @dev Get seller of order
     * @param orderID ID of order
     */
    function getOrderSeller(uint80 orderID) public view returns(uint256) {
        return order[orderID].seller;
    }


    /**
     * @dev Get trustees
     * @param orderID ID of order
     */
    function getOrderTrustees(uint80 orderID) public view returns(address[]) {
        return order[orderID].trustees;
    }


    /**
     * @dev Check user ia a order's trustee or not
     * @param orderID ID of order
     */
    function isOrderTrustee(
        uint80 orderID,
        address user
    )
        public
        view
        returns(bool)
    {
        return order[orderID].isTrustee[user];
    }


    /**
     * @dev Update order status if buyer and seller all uploaded secret
     */
    function updateStatusWhenSecretUploaded(uint80 orderID) internal returns(bool){
        uint32 buyer = order[orderID].buyer;
        uint32 seller = order[orderID].seller;

        if (order[orderID].uploaded[buyer] == true && order[orderID].uploaded[seller] == true) {
            order[orderID].status = OrderStatus.SecretUploaded;
        }

        return true;
    }


    /**
     * @dev Update order status if shard uploaded by seller and buyer is right
     * @param orderID ID of order
     */
    function updateStatusWhenShardVerified(uint80 orderID) internal returns(bool) {
        uint32 buyer = order[orderID].buyer;
        uint32 seller = order[orderID].seller;

        for (uint8 i = 0; i < order[orderID].trustees.length; i++) {
            address trustee = order[orderID].trustees[i];
            if (order[orderID].trusteeShard[trustee][buyer].verified == false) {
                return false;
            }

            if (order[orderID].trusteeShard[trustee][buyer].rightShard == false) {
                return false;
            }

            if (order[orderID].trusteeShard[trustee][seller].verified == false) {
                return false;
            }

            if (order[orderID].trusteeShard[trustee][seller].rightShard == false) {
                return false;
            }
        }

        order[orderID].status = OrderStatus.VerifiedSuccess;
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


contract Arbitrable is TradeBasic, ArbitratorManageable {

    // Arbitration of order;
    mapping(uint256 => ArbitrateInfo) arbitration;

    struct ArbitrateInfo {
        // Who arbitrate
        uint32 requester;

        // Who wins
        uint32 winner;
    }

    event Arbitrate(uint80 orderID, uint32 indexed user);

    event Judge(uint80 orderID, uint32 indexed winner, address indexed judge);


    /**
     * @dev User arbitrate
     * @param orderID ID of order want to arbitrate for
     */
    function arbitrate(uint80 orderID, uint32 user) public onlyOwner returns(bool) {
        // Not necessary check order(check by onlyTrader)
        // Have not arbitrate before
        require(arbitration[orderID].requester == 0);
        require(order[orderID].buyer == user || order[orderID].seller == user);
        // Ensure secret have been uploaded
        require(order[orderID].status == OrderStatus.VerifiedSuccess);

        order[orderID].status = OrderStatus.InArbitration;

        arbitration[orderID].requester = user;
        emit Arbitrate(orderID, user);
        return true;
    }


    /**
     * @dev Arbitrator judge who wins
     * @param orderID ID of order
     * @param winner User who wins
     */
    function judge(uint80 orderID, uint32 winner) public onlyArbitrator returns(bool) {
        // Ensure order is in arbitration
        require(arbitration[orderID].requester != 0);
        // Ensure secret have been uploaded
        require(order[orderID].status == OrderStatus.InArbitration);

        // Winner must be one of buyer/seller
        require(winner == order[orderID].buyer || winner == order[orderID].seller);

        arbitration[orderID].winner = winner;
        order[orderID].status = OrderStatus.Finished;

        // Compute fee for trustee
        uint256 perFee = getPerFeeOfOrder(orderID);
        for (uint8 i = 0; i < order[orderID].trustees.length; i++) {
            address trustee = order[orderID].trustees[i];
            feeRemain[trustee] = feeRemain[trustee].add(perFee);
        }
        emit Judge(orderID, winner, msg.sender);
        return true;
    }


    /**
     * @dev Get arbitration requester
     * @param orderID ID of order
     */
    function getRequester(uint80 orderID) public view returns(uint32) {
        return arbitration[orderID].requester;
    }


    /**
     * @dev Get arbitration winner
     * @param orderID ID of order
     */
    function getWinner(uint80 orderID) public view returns(uint32) {
        return arbitration[orderID].winner;
    }
}


// Main contract
contract Trade is InfoManageable, Arbitrable, OrderManageable  {


}
