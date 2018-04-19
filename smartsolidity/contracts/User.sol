pragma solidity ^0.4.18;

import "./librarys/LinkedListLib.sol";
import "./Ownable.sol";
import "./FeeManager.sol";


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

    FeeManager feeManager;

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

    event RemoveThemisHoster(address indexed id);

    event ChangeToThemisHoster(address indexed id);

    event GetThemisHosters(uint256 orderID, address indexed who, address[] hosters);

    // Only hoster can call this method
    modifier OnlyHoster() {
        require(msg.sender == hoster[idIndex[msg.sender]].id);
        _;
    }

    // Can be called only by themis user
    modifier onlyThemisUser() {
        require(super.IsThemisUser(msg.sender));
        _;
    }

    // Init contract with fee manager address
    function Hoster(address feeManagerAddress) public {
        require(feeManagerAddress != address(0));

        feeManager = FeeManager(feeManagerAddress);
        // Ensure zero will not be used for index
        hoster.push(HosterInfo(address(0), 0));
    }


    // Check is a hoster or not
    function IsHoster(address who) public view returns(bool) {
        if (idIndex[who] != 0 && users[who].userType == HOSTER && hoster[idIndex[who]].id == who) {
            bool exist;
            uint256 i;
            uint256 j;
            (exist, i, j) = sortedHosterIndex.getNode(idIndex[who]);
            if (exist) {
                return true;
            }
        }

        return false;
    }


    // Find position for hoster to insert in
    function AddHoster(address _id, uint256 _fame, uint256 _deposit, string _publicKey) public onlyOwner returns(bool){

        require(_id != address(0));
        // Ensure user haven't been added before
        require(idIndex[_id] == 0);
        // Should call UpdateNormalUserToHoster when a address is themis user before
        require(!super.IsThemisUser(_id));

        bool success;
        uint256 position;
        bool direction;

        // Get position where to insert
        (success, position, direction) = getInsertPosition(_fame, _deposit);
        require(success);

        hoster.push(HosterInfo(_id, _deposit));

        // Update list and idIndex
        sortedHosterIndex.insert(position, hoster.length - 1, direction);
        idIndex[_id] = hoster.length - 1;

        // Add/Update user to themis user contract
        super.AddUser(_id, _fame, _publicKey, HOSTER);

        AddThemisHoster(_id);
        return true;
    }



    // Update from user to hoster
    // Should pass deposit, because normal user didn't have this field
    function UpdateNormalUserToHoster(address _id, uint256 _deposit) onlyOwner public returns(bool) {
        // Only themis user can be updated to hoster
        require(super.IsThemisUser(_id));

        bool success;
        uint256 position;
        bool direction;

        // Get position where to insert
        (success, position, direction) = getInsertPosition(users[_id].fame, _deposit);
        require(success);

        hoster.push(HosterInfo(_id, _deposit));

        // Update list and idIndex
        sortedHosterIndex.insert(position, hoster.length - 1, direction);
        idIndex[_id] = hoster.length - 1;

        super.UpdateUser(_id, users[_id].fame, users[_id].publicKey, HOSTER);
        ChangeToThemisHoster(_id);
        return true;
    }


    // Remove hoster but retains themis user
    function RemoveHoster(address _id) onlyOwner public returns(bool) {
        // Simple check
        require(_id != address(0));
        require(idIndex[_id] != 0);

        // Set all info to zero/init
        sortedHosterIndex.remove(idIndex[_id]);
        hoster[idIndex[_id]] = HosterInfo(address(0), 0);
        idIndex[_id] = 0;

        super.UpdateUser(_id, users[_id].fame, users[_id].publicKey, NORMAL);
        RemoveThemisHoster(_id);
        return true;
    }


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
    // May OnlyOwner do better?
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
    // Should pay GET Tokens to get this service
    function GetHosters(uint256 orderID, uint256 num) onlyThemisUser  public returns(address[]) {
        require(num > 0);

        // Get node from list sequentially
        bool exist;
        uint256 i;

        // Use storage may cause "out of gas"
        address[] memory hostersList = new address[](num);

        // List not exist
        (exist, i) = sortedHosterIndex.getAdjacent(HEAD, NEXT);
        if (!exist) {
            return hostersList;
        }

        uint256 numElements;
        while (i != HEAD) {
            hostersList[numElements] = hoster[i].id;
            numElements++;
            if (numElements >= num) {
                break;
            }
            (exist,i) = sortedHosterIndex.getAdjacent(i, NEXT);
        }

        // Remove empty address
        if (numElements < num) {
            address[] memory resultList = new address[](numElements);
            for (uint256 j = 0; j < numElements; j++) {
                resultList[j] = hostersList[j];
            }

            GetThemisHosters(orderID, msg.sender, resultList);

            // Pay Fee
            assert(feeManager.PayFee(orderID, feeManager.GetHostServiceType(), msg.sender, resultList));
        } else {
            GetThemisHosters(orderID, msg.sender, hostersList);

            // Pay Fee
            assert(feeManager.PayFee(orderID, feeManager.GetHostServiceType(), msg.sender, hostersList));
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
        bool success;
        uint256 position;
        bool direction;
        sortedHosterIndex.remove(idIndex[_id]);
        (success, position, direction) = getInsertPosition(_newFame, _newDeposit);
        require(success);

        // Update hoster fame and deposit
        hoster[idIndex[_id]] = HosterInfo(_id, _newDeposit);

        // Reinsert node to list
        sortedHosterIndex.insert(position, idIndex[_id], direction);

        return true;
    }


    // Find the right position a node should be insert in with given fame and deposit
    function getInsertPosition(uint256 _fame, uint256 _deposit) internal view returns(bool, uint256, bool){
        // Get first node
        bool exist;
        uint256 i;
        (exist, i) = sortedHosterIndex.getAdjacent(HEAD, NEXT);

        // Insert node to right of head when list is empty
        if (!exist || i == HEAD) {
            return (true, HEAD, NEXT);
        }
        // Should be insert to first whose fame is biggest
        if (users[hoster[i].id].fame < _fame) {
            return (true, HEAD, NEXT);
        }
        if (users[hoster[i].id].fame == _fame && hoster[i].deposit < _deposit) {
            return (true, HEAD, NEXT);
        }

        uint256 before = i;
        // Should be insert after first one
        while (i != HEAD) {
            (exist, i) = sortedHosterIndex.getAdjacent(i, NEXT);
            // Reach end of the list
            if (!exist) {
                return (true, before, NEXT);
            }

            // Check the position is right between before and after
            // Situation 1: before'fame > insert one's fame
            // Examples A: Before one is {fame:7, deposit:5}, insert one is {fame:6, deposit:4}, after one is {fame:5, deposit:5}
            // Examples B: Before one is {fame:7, deposit:1}, insert one is {fame:6, deposit:4}, after one is {fame:6, deposit:3}
            if (users[hoster[before].id].fame > _fame) {
                if (users[hoster[i].id].fame < _fame) {
                    return (true, before, NEXT);
                }

                if (users[hoster[i].id].fame == _fame && hoster[i].deposit <= _deposit) {
                    return (true, before, NEXT);
                }

            }

            // Situation 2:
            // Before one's fame == insert one's fame
            // Examples A: Before one is {fame:7, deposit:5}, insert one is {fame:7, deposit:4}, after one is {fame:6, deposit:5}
            // Examples B: Before one is {fame:7, deposit:5}, insert one is {fame:7, deposit:4}, after one is {fame:7, deposit:3}
            if (users[hoster[before].id].fame == _fame) {
                //
                if (users[hoster[i].id].fame < _fame && hoster[before].deposit >= _deposit) {
                    return (true, before, NEXT);
                }

                if (users[hoster[i].id].fame == _fame && hoster[before].deposit >= _deposit && hoster[i].deposit <= _deposit) {
                    return (true, before, NEXT);
                }
            }

            before = i;
        }

        return (false, 0, false);
    }
}

