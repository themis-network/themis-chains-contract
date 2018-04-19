pragma solidity ^0.4.18;

import "./librarys/strings.sol";

contract Vss {

    using strings for *;

    uint256 public N;
    function Vss() public {

    }

    // TODO
    function verifyShare(string decryptedShard) public returns(bool) {
        return true;
    }

    // TODO
    function recoverPrivateKey(string shards) public returns(string) {
        return "";
    }

    //
    function GetN() public view returns(uint256) {
        return N;
    }
}
