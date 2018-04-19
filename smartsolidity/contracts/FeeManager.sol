pragma solidity ^0.4.18;

import "./Ownable.sol";
import "./librarys/SafeMath.sol";

/**
 * @title ERC20Basic
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
 * @title ERC20 interface
 * @dev see https://github.com/ethereum/EIPs/issues/20
 */
contract ERC20 is ERC20Basic {
    function allowance(address owner, address spender) public view returns (uint256);
    function transferFrom(address from, address to, uint256 value) public returns (bool);
    function approve(address spender, uint256 value) public returns (bool);
    event Approval(address indexed owner, address indexed spender, uint256 value);
}

contract FeeManager is Ownable {

    ERC20 public GET;

    using SafeMath for uint256;

    uint256 public constant HOST_SERVICE = 1;
    uint256 public constant JUDGE_SERVICE = 2;

    // Fee should be payed when using a service node/user
    // Unit in wei
    uint256 public feeRate;

    event FeePayed(uint256 orderID, uint256 serviceType, uint256 payedFee);

    event TestSs(uint256 aa);

    // Can be called only by request allowed services
    modifier OnlyAllowedServices(uint256 serviceType) {
        require(serviceType == HOST_SERVICE || serviceType == JUDGE_SERVICE);
        _;
    }

    // Init GET Token address and fee rate
    function FeeManager(address getAddress, uint256 _feeRate) public {
        require(getAddress != address(0));
        require(_feeRate > 0);

        GET = ERC20(getAddress);
        feeRate = _feeRate;
    }


    // Get host service type
    function GetHostServiceType() public view returns(uint256) {
        return HOST_SERVICE;
    }

    // Get judge service
    function GetJudgeService() public view returns(uint256) {
        return JUDGE_SERVICE;
    }


    // Pay host/judge fee
    function PayFee(uint256 orderID, uint256 serviceType, address user, address[] serviceNodes) OnlyAllowedServices(serviceType) public returns(bool) {
        // Simple check
        require(user != address(0));
        require(serviceNodes.length > 0);

        var fee = caculateFee(serviceType, serviceNodes.length);
        if (serviceType == HOST_SERVICE) {
            PayForHostService(orderID, user, serviceNodes, fee);
        }

        if (serviceType == JUDGE_SERVICE) {
            PayForJudgementService(orderID, user, serviceNodes, fee);
        }

        FeePayed(orderID, serviceType, fee);

        return true;
    }


    // User pay GET Tokens for host service
    // First, fee will be transfer to this contract
    // Then, this contract transfer fee to service nodes/users
    function PayForHostService(uint256 orderID, address user, address[] serviceNodes, uint256 shouldPay) internal returns(bool) {
        // Number of GET Tokens holded and approved to this(FeeManager address) by user should bigger than shouldPay
        // User should approve this contract enough GET Tokens

        var fee = shouldPay.div(serviceNodes.length);
        // Transfer fee to every service user/node
        for (uint i = 0; i < serviceNodes.length; i++) {
            GET.transferFrom(user, serviceNodes[i], fee);
        }

        return true;
    }


    // User pay fee for judgement service
    // ?
    // First, transfer fee to this contract
    // Then, wait for judge upload result, then pay fee to judge
    function PayForJudgementService(uint256 orderID, address user, address[] serviceNodes, uint256 shouldPay) internal returns(bool) {
        // TODO ensure/change fee payed module
        var fee = shouldPay.div(serviceNodes.length);
        // Transfer fee to every service user/node
        for (uint i = 0; i < serviceNodes.length; i++) {
            GET.transferFrom(user, serviceNodes[i], fee);
        }

        return true;
    }


    // Caculate fee based on serviceType and nodesNum
    // Up to now, service type doesn't make sense
    function caculateFee(uint256 serviceType, uint256 nodesNum) internal view returns(uint256) {
        return feeRate.mul(nodesNum);
    }
}
