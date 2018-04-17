pragma solidity ^0.4.18;

import "./librarys/LinkedListLib.sol";
import "./Ownable.sol";


contract ThemisUser is Ownable {
    // Themis user map
    mapping(address => UserInfo) public users;

    // Normal user
    uint256 constant NORMAL = 0;
    // Host user
    uint256 constant HOSTER = 1;

    event AddThemisUser(address indexed id);

    event UpdateThemisUser(address indexed id);

    event RemoveThemisUser(address indexed id);

    struct UserInfo {
        // Address of hoster which used as id
        address id;

        // Fame of user
        // TODO can it less than zero?
        uint256 fame;

        // Public key of user used for others to encrypt data
        string publicKey;

        // User type
        uint256 userType;
    }

    // Add new themis user
    function AddUser(address _id, uint256 _fame, string _publicKey, uint256 _userType) public onlyOwner returns(bool) {
        require(_id != address(0));
        require(users[_id].id == address(0));

        users[_id].id = _id;
        users[_id].fame = _fame;
        users[_id].publicKey = _publicKey;
        users[_id].userType = _userType;

        AddThemisUser(_id);
        return true;
    }

    // Update user info
    function UpdateUser(address _id, uint256 _newFame, string _newPublicKey, uint256 _userType) public onlyOwner returns(bool) {
        // Ensure user is exist
        require(users[_id].id == _id);

        users[_id].id = _id;
        users[_id].fame = _newFame;
        users[_id].publicKey = _newPublicKey;
        users[_id].userType = _userType;

        UpdateThemisUser(_id);
        return true;
    }

    // Remove user
    function RemoveUser(address _id) public onlyOwner returns(bool) {
        // Set info to null
        users[_id].id = address(0);
        users[_id].fame = 0;
        users[_id].publicKey = "";
        users[_id].userType = NORMAL;

        RemoveThemisUser(_id);
        return true;
    }

    // Check a address is themis user or not
    function IsThemisUser(address user) public view returns(bool) {
        require(user != address(0));
        return users[user].id == user;
    }
}


contract Hoster is ThemisUser {
    using LinkedListLib for LinkedListLib.LinkedList;

    // Index of all users sort by fame, deposit;
    LinkedListLib.LinkedList sortedHosterIndex;

    uint256 constant HEAD = 0;
    bool constant PREV = false;
    bool constant NEXT = true;

    struct HosterInfo {
        // Address of hoster which used as id
        address id;

        // Deposit fund to be a hoster/judge in GET uint
        uint256 deposit;
    }

    // Host info list of user
    HosterInfo[] public hoster;

    mapping(address => uint256) public idIndex;

    event AddThemisHoster(address indexed id);

    // Only hoster can call this method
    modifier OnlyHoster() {
        require(msg.sender == hoster[idIndex[msg.sender]].id);
        _;
    }

    function Hoster() public {
        // Ensure zero will not be used for index
        hoster.push(HosterInfo(address(0), 0));
    }


    // Find position for hoster to insert in
    function AddHoster(address _id, uint256 _fame, uint256 _deposit, string _publicKey) public onlyOwner returns(bool){

        require(_id != address(0));
        // Ensure user haven't been added before
        require(idIndex[_id] == 0);

        uint256 position;
        bool direction;

        // Get position where to insert
        (position, direction) = getInsertPosition(_fame, _deposit);

        hoster.push(HosterInfo(_id, _deposit));

        // Update list and idIndex
        sortedHosterIndex.insert(position, hoster.length - 1, direction);
        idIndex[_id] = hoster.length - 1;

        // Add/Update user to themis user contract
        if (super.IsThemisUser(_id)) {
            super.UpdateUser(_id, _fame, _publicKey, HOSTER);
        } else {
            super.AddUser(_id, _fame, _publicKey, HOSTER);
        }

        AddThemisHoster(_id);
        return true;
    }


    // TODO remove hoster when


    // Owner update user's fame
    function UpdateUserFame(address _id, uint256 _newFame) public onlyOwner returns(bool) {
        // User should have been added before
        // The index of first node is 1, so no need to do more check
        require(idIndex[_id] != 0);

        uint256 oldDeposit = hoster[idIndex[_id]].deposit;
        require(updateUserFameOrDeposit(_id, _newFame, oldDeposit) == true);

        super.UpdateUser(_id, _newFame, users[_id].publicKey, users[_id].userType);

        return true;
    }


    // User update his/her deposit
    function UpdateUserDeposit(uint256 _newDeposit) public OnlyHoster returns(bool) {
        // User should have been added before
        // The index of first node is 1, so no need to do more check
        address _id = msg.sender;
        require(idIndex[_id] != 0);

        require(updateUserFameOrDeposit(_id, users[_id].fame, _newDeposit) == true);

        super.UpdateUser(_id, users[_id].fame, users[_id].publicKey, users[_id].userType);

        return true;
    }


    // Get hoster id(address) sort by fame, deposit, and contact them to a string
    function GetHosters(uint256 num) public view returns(address[]) {
        require(num > 0);

        // Get node from list sequentially
        bool exist;
        uint256 i;
        address[] memory hostersList = new address[](num);

        (exist, i) = sortedHosterIndex.getAdjacent(HEAD, NEXT);
        if (!exist) {
            return hostersList;
        }

        uint256 numElements;
        while (i != HEAD) {
            hostersList[numElements] = (hoster[i].id);
            numElements++;
            if (numElements >= num) {
                // Contact address to string
                return hostersList;
            }
            (exist,i) = sortedHosterIndex.getAdjacent(i, NEXT);
        }

        // If num is bigger than size of hoster, just return all hosters
        return hostersList;
    }


    // Update node list and related info when user's fame/deposit is updated
    function updateUserFameOrDeposit(address _id, uint256 _newFame, uint256 _newDeposit) internal returns(bool){
        // User should have been added before
        // The index of first node is 1, so no need to do more check
        require(idIndex[_id] != 0);

        // Remove and reinsert it to list
        uint256 position;
        bool direction;
        sortedHosterIndex.remove(idIndex[_id]);
        (position, direction) = getInsertPosition(_newFame, _newDeposit);

        // Update hoster fame and deposit
        hoster[idIndex[_id]] = HosterInfo(_id, _newDeposit);

        // Reinsert node to list
        sortedHosterIndex.insert(position, idIndex[_id], direction);

        return true;
    }


    // Find the right position a node should be insert in with given fame and deposit
    function getInsertPosition(uint256 _fame, uint256 _deposit) internal view returns(uint256, bool){
        // Get first node
        bool exist;
        uint256 i;
        (exist, i) = sortedHosterIndex.getAdjacent(HEAD, NEXT);

        // Insert node to right of head when list is empty
        if (!exist || i == HEAD) {
            return (HEAD, NEXT);
        }
        // Should be insert to first whose fame is biggest
        if (users[hoster[i].id].fame < _fame) {
            return (HEAD, NEXT);
        }

        uint256 before = i;
        // Should be insert after first one
        while (i != HEAD) {
            (exist, i) = sortedHosterIndex.getAdjacent(i, NEXT);
            // Reach end of the list
            if (!exist) {
                return (before, NEXT);
            }

            // Check the position is right between before and after
            if (
                users[hoster[before].id].fame >= _fame
                && users[hoster[i].id].fame <= _fame
                && hoster[before].deposit >= _deposit
                && hoster[i].deposit <= _deposit
            ) {
                return (before, NEXT);
            }

            before = i;
        }
    }
}

