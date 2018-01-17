pragma solidity ^0.4.18;

import "./Ownable.sol";
import "./Utils.sol";
import "./StringUtils.sol";

contract Reputation is Ownable {

    // fame of userId
    mapping(bytes32 => int256) fame;

    /*
     * @dev no operation to do on init
     */
    function Reputation() public {

    }

    /*
     * @dev get reputation of user
     * @param userId : user id
     */
    function GetReputation(string userId) public view returns(int256) {
        bytes32 userBytes32Id = Utils.stringToBytes32(userId);
        return fame[userBytes32Id];
    }

    /*
     * @dev caculate the reputation by result of arbitration
     */
    function StoreArbitrate(string userId, int step) public onlyOwner {
        bytes32 userBytes32Id = Utils.stringToBytes32(userId);
        fame[userBytes32Id] = Utils.intAdd(fame[userBytes32Id], step);
    }

}
