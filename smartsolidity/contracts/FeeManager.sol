pragma solidity ^0.4.23;

import "./Ownable.sol";
import "./librarys/SafeMath.sol";

/**
 * @dev ERC20Basic
 * @dev Simpler version of ERC20 interface
 * @dev see https://github.com/ethereum/EIPs/issues/179
 */
contract ERC20Basic {
    uint256 public totalSupply;
    function balanceOf(address who) public view returns (uint256);
    function transfer(address to, uint256 value) public returns (bool);
    event Transfer(address indexed from, address indexed to, uint256 value);
}


/**
 * @dev ERC20 interface
 * @dev see https://github.com/ethereum/EIPs/issues/20
 */
contract ERC20 is ERC20Basic {
    function allowance(address owner, address spender) public view returns (uint256);
    function transferFrom(address from, address to, uint256 value) public returns (bool);
    function approve(address spender, uint256 value) public returns (bool);
    event Approval(address indexed owner, address indexed spender, uint256 value);
}

/**
 * @dev Main manager for fee
 */
contract FeeManager is Ownable {

    ERC20 public GET;

    using SafeMath for uint256;

    // Service type of user request
    enum ServiceType { Host, Arbitration }

    // Fee should be payed when using a service node/user
    // Unit in wei
    uint256 public feeRate;

    // Deposit payed by a user when becoming a hoster
    mapping(address => uint256) depositPayed;

    // Hoster contract
    address hosterContract;

    // Trade contract
    address tradeContract;

    event FeePayed(
        uint256 orderID,
        ServiceType serviceType,
        uint256 payedFee,
        address[] serviceNodes
    );

    event PayDeposit(address indexed user, uint256 deposit);

    event WithDrawDeposit(address indexed user, uint256 deposit);

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
     * @dev Init GET Token address and fee rate
     * @param getAddress GET Token contract's address
     * @param _feeRate Fee rate used to caculate fee should payed
     */
    function FeeManager(address getAddress, uint256 _feeRate) public {
        require(getAddress != address(0));
        require(_feeRate > 0);

        GET = ERC20(getAddress);
        feeRate = _feeRate;
    }


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
        onlyAllowedServices(serviceType)
        onlyHosterOrTradeContract
        returns(bool)
    {
        // Simple check
        require(user != address(0), "user address should be valid");
        require(serviceNodes.length > 0, "number of service nodes/users should bigger than zero");

        uint256 fee = caculateFee(serviceType, serviceNodes.length);
        if (serviceType == ServiceType.Host) {
            payForHostService(orderID, user, serviceNodes, fee);
        }

        if (serviceType == ServiceType.Arbitration) {
            payForArbitrationService(orderID, user, serviceNodes, fee);
        }

        emit FeePayed(orderID, serviceType, fee, serviceNodes);

        return true;
    }


    /**
     * @dev User should pay deposit for being a hoster
     * @dev Deposit will be sent back when turn back to noraml user
     * @param user User who will be a hoster
     * @param deposit Deposit should be payed by user
     */
    function payDeposit(address user, uint256 deposit) public onlyHosterContract returns(bool) {
        // Simple check
        require(user != address(0));
        require(deposit > 0);
        // User should not be a hoster before
        require(depositPayed[user] == 0);

        // Will assert when user doesn't have/approve enough deposit for contract
        assert(GET.transferFrom(user, this, deposit));

        // Record deposit payed by user
        depositPayed[user] = deposit;

        emit PayDeposit(user, deposit);
        return true;
    }


    /**
     * @dev User will withdraw deposit when turn back to a normal user
     * @param user User who turn back to a normal user
     */
    function withDrawDeposit(address user) public onlyHosterContract returns(bool){
        // Simple check
        require(user != address(0));
        require(depositPayed[user] > 0);

        // Transfer deposit back to user
        assert(GET.transfer(user, depositPayed[user]));
        emit WithDrawDeposit(user, depositPayed[user]);

        depositPayed[user] = 0;
        return true;
    }


    /**
     * @dev User will withdraw/pay deposit when update deposit info
     * @param _newDeposit New deposit hoster want to claim
     */
    function updateToNewDepsoit(address user, uint256 _newDeposit) public onlyHosterContract returns(bool) {
        // Simple check
        require(user != address(0));
        require(_newDeposit > 0);

        uint256 oriDeposit = depositPayed[user];
        if (_newDeposit > oriDeposit){
            // Should pay deposit
            // Will assert when user doesn't have/approve enough deposit for contract
            assert(GET.transferFrom(user, this, _newDeposit.sub(oriDeposit)));

            // Record deposit payed by user
            depositPayed[user] = _newDeposit;

            emit PayDeposit(user, _newDeposit.sub(oriDeposit));
            return true;
        }

        if (_newDeposit < oriDeposit) {
            // Should with draw deposit
            // Transfer deposit back to user
            assert(GET.transfer(user, oriDeposit.sub(_newDeposit)));
            // Record deposit payed by user
            depositPayed[user] = _newDeposit;

            emit WithDrawDeposit(user, oriDeposit.sub(_newDeposit));
            return true;
        }

        return false;
    }


    /**
     * @dev User pay GET Tokens for host service: User should approve this contract enough get tokens
     * @dev This contract will transfer fee from user to service hosters/nodes
     * @param orderID Order id of trade
     * @param user User who request a host service
     * @param serviceNodes Service hoster/nodes list
     * @param shouldPay Fee user should pay for host service, which is caculated based on length of service hoster/nodes
     */
    function payForHostService(
        uint256   orderID,
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
            assert(GET.transferFrom(user, serviceNodes[i], fee));
        }

        return true;
    }


    /**
     * @dev User pay for arbitration service, contract transfer fee from user to hosters/users directly
     * @dev //TODO workflow of payment of arbitraion may be changed to?
     * @param orderID Order id of trade
     * @param user User who request arbitration service
     * @param serviceNodes List of service hosters/nodes user select
     * @param shouldPay Total fee should be payed to hosters/nodes
     * @return Indicate operation success or not
     */
    function payForArbitrationService(
        uint256   orderID,
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
            assert(GET.transferFrom(user, serviceNodes[i], fee));
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
        return feeRate.mul(nodesNum);
    }
}
