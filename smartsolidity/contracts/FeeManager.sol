pragma solidity ^0.4.23;

import "./Ownable.sol";
import "./librarys/SafeMath.sol";



/**
 * @dev Main manager for fee
 */
contract FeeManager is Ownable {

    using SafeMath for uint256;

    // Service type of user request
    enum ServiceType { Host, Arbitration }

    // Fee rate of host service
    // Unit in wei
    uint256 public hostFeeRate = 1 ether;

    // Fee rate of arbitration service
    uint256 public arbitrationFeeRate = 1 ether;

    // Deposit payed by a user when becoming a hoster
    mapping(address => uint256) depositPayed;

    // Hoster contract
    address public hosterContract;

    // Trade contract
    address public tradeContract;

    event FeePayed(
        uint256 orderID,
        ServiceType serviceType,
        uint256 payedFee,
        address[] serviceNodes
    );

    event PayDeposit(address indexed user, uint256 deposit);

    event WithdrawDeposit(address indexed user, uint256 deposit);

    event UpdateHosterContract(address indexed hoster);

    event UpdateTradeContract(address indexed trade);

    /**
     * @dev Can be called only by request allowed services
     * @param serviceType Service type user requests
     */
    modifier onlyAllowedServices(ServiceType serviceType) {
        require(serviceType == ServiceType.Host || serviceType == ServiceType.Arbitration);
        _;
    }


    /**
     * @dev Can only be called by Hoster contract
     */
    modifier onlyHosterContract() {
        require(hosterContract != address(0));
        require(msg.sender == hosterContract);
        _;
    }


    /**
     * @dev Can only be called by Trade contract
     */
    modifier onlyTradeContract() {
        require(tradeContract != address(0));
        require(msg.sender == tradeContract);
        _;
    }


    /**
     * @dev Can only be called by hoster/trade contract
     */
    modifier onlyHosterOrTradeContract() {
        require(hosterContract != address(0));
        require(tradeContract != address(0));
        require(msg.sender == hosterContract || msg.sender == tradeContract);
        _;
    }


    /**
     * @dev Init
     */
    function FeeManager() public {}


    /**
     * @dev Update Hoster contract address
     * @param _newHoster New hoster contract address
     */
    function updateHosterContract(address _newHoster) public onlyOwner returns(bool) {
        require(_newHoster != address(0));

        hosterContract = _newHoster;
        emit UpdateHosterContract(_newHoster);
        return true;
    }


    /**
     * @dev Update trade contract address
     * @param _newTrade New trade contract address
     */
    function updateTradeContract(address _newTrade) public onlyOwner returns(bool) {
        require(_newTrade != address(0));

        tradeContract = _newTrade;

        emit UpdateTradeContract(_newTrade);
        return true;
    }

    /**
     * @dev Update fee rate
     * @param _newHostFeeRate New host fee rate
     * @param _newArbitrationFeeRate New arbitration fee rate
     */
    function updateFeeRate(uint256 _newHostFeeRate, uint256 _newArbitrationFeeRate) public onlyOwner returns(bool) {
        require(_newHostFeeRate > 0);
        require(_newArbitrationFeeRate > 0);

        hostFeeRate = _newHostFeeRate;
        arbitrationFeeRate = _newArbitrationFeeRate;
    }


    /**
     * @dev Get host service type
     * @return host service type
     */
    function getHostServiceType() public view returns(ServiceType) {
        return ServiceType.Host;
    }


    /**
     * @dev Get arbitration service
     * @return arbitration service type
     */
    function getArbitrationServiceType() public view returns(ServiceType) {
        return ServiceType.Arbitration;
    }


    /**
     * @dev Pay fee for host/arbitration service
     * @param orderID Order id of trade
     * @param serviceType Service type user requests
     * @param user User who want to use host/arbitration service
     * @param serviceNodes Service hosters/nodes user selected
     * @return indicate operation success or not
     */
    function payFee(
        uint256 orderID,
        ServiceType serviceType,
        address user,
        address[] serviceNodes
    )
        public
        payable
        onlyAllowedServices(serviceType)
        onlyHosterOrTradeContract
        returns(bool)
    {
        // Simple check
        require(user != address(0), "user address should be valid");
        require(serviceNodes.length > 0, "number of service nodes/users should bigger than zero");

        uint256 fee = caculateFee(serviceType, serviceNodes.length);
        require(msg.value >= fee);

        if (serviceType == ServiceType.Host) {
            payForHostService(user, serviceNodes, fee);
        }

        if (serviceType == ServiceType.Arbitration) {
            payForArbitrationService(user, serviceNodes, fee);
        }

        // Pay back remain get coin
        if (msg.value > fee) {
            user.transfer(msg.value.sub(fee));
        }

        emit FeePayed(orderID, serviceType, fee, serviceNodes);

        return true;
    }


    /**
     * @dev User should pay deposit for being a hoster
     * @dev Deposit will be sent back when turn back to noraml user
     * @param user User who will be a hoster
     */
    function payDeposit(address user) public onlyHosterContract payable returns(bool) {
        // Simple check
        require(user != address(0));
        require(msg.value > 0);

        // User should not be a hoster before
        require(depositPayed[user] == 0);

        uint256 deposit = msg.value;
        // Record deposit payed by user
        depositPayed[user] = deposit;

        emit PayDeposit(user, deposit);
        return true;
    }


    /**
     * @dev User will withdraw deposit when turn back to a normal user
     * @param user User who turn back to a normal user
     */
    function withdrawDeposit(address user) public onlyHosterContract returns(bool){
        // Simple check
        require(user != address(0));
        require(depositPayed[user] > 0);

        // Transfer deposit back to user
        user.transfer(depositPayed[user]);
        emit WithdrawDeposit(user, depositPayed[user]);

        depositPayed[user] = 0;
        return true;
    }


    /**
     * @dev User will withdraw/pay deposit when update deposit info
     * @param user User who will increase deposit
     */
    function increaseDeposit(address user) public payable onlyHosterContract returns(bool) {
        // Simple check
        require(user != address(0));
        require(msg.value > 0);

        depositPayed[user] = depositPayed[user].add(msg.value);
        emit PayDeposit(user, msg.value);
        return true;
    }


    /**
     * @dev User withdraw deposit
     * @param user User who will decrease deposit
     * @param amount Amount of GET coin user want to increase for deposit
     */
    function decreaseDeposit(address user, uint256 amount) public onlyHosterContract returns(bool) {
        // Simple check
        require(user != address(0));
        require(amount > 0);

        require(amount <= depositPayed[user]);

        user.transfer(amount);
        depositPayed[user] = depositPayed[user].sub(amount);

        emit WithdrawDeposit(user, amount);
        return true;
    }


    /**
     * @dev User pay GET Tokens for host service: User should approve this contract enough get tokens
     * @dev This contract will transfer fee from user to service hosters/nodes
     * @param user User who request a host service
     * @param serviceNodes Service hoster/nodes list
     * @param shouldPay Fee user should pay for host service, which is caculated based on length of service hoster/nodes
     */
    function payForHostService(
        address   user,
        address[] serviceNodes,
        uint256   shouldPay
    )
        internal
        returns(bool)
    {
        // Number of GET Tokens holded and approved to this(FeeManager address) by user should bigger than shouldPay
        // User should approve this contract enough GET Tokens

        uint256 fee = shouldPay.div(serviceNodes.length);
        // Transfer fee to every service user/node
        for (uint i = 0; i < serviceNodes.length; i++) {
            serviceNodes[i].transfer(fee);
        }

        return true;
    }


    /**
     * @dev User pay for arbitration service, contract transfer fee from user to hosters/users directly
     * @dev //TODO workflow of payment of arbitraion may be changed to?
     * @param user User who request arbitration service
     * @param serviceNodes List of service hosters/nodes user select
     * @param shouldPay Total fee should be payed to hosters/nodes
     * @return Indicate operation success or not
     */
    function payForArbitrationService(
        address   user,
        address[] serviceNodes,
        uint256   shouldPay
    )
        internal
        returns(bool)
    {
        uint256 fee = shouldPay.div(serviceNodes.length);
        // Transfer fee to every service user/node
        for (uint i = 0; i < serviceNodes.length; i++) {
            serviceNodes[i].transfer(fee);
        }

        return true;
    }


    /**
     * @dev Caculate fee based on serviceType and nodesNum, Up to now, service type doesn't make sense
     * @param serviceType Service type user request
     * @param nodesNum Number of service hosters/nodes
     * @return Fee should be payed for a special service
     */
    function caculateFee(ServiceType serviceType, uint256 nodesNum) internal view returns(uint256) {
        if (serviceType == ServiceType.Host) {
            return hostFeeRate.mul(nodesNum);
        }

        if (serviceType == ServiceType.Arbitration) {
            return arbitrationFeeRate.mul(nodesNum);
        }

        // Never reach
        return 0;
    }
}
