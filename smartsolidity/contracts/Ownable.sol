pragma solidity ^0.4.0;

/*
** @title Ownable
*/
contract Ownable {
    address owner;
    event ChangeOwner(address oriOwner, address newOwner);

    function Ownable() public {
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(owner == msg.sender);
        _;
    }

    function changeOwner(address newOwner) public onlyOwner {
        owner = newOwner;
        ChangeOwner(owner, newOwner);
    }
}