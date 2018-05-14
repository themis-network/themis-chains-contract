pragma solidity ^0.4.23;

import "./librarys/LinkedListLib.sol";
import "./Ownable.sol";
import "./FeeManager.sol";


contract ThemisUser is Ownable {
    // Themis user map
    mapping(address => UserInfo) public users;

    // Themis user type
    enum UserType { Normal, Hoster }

    event AddThemisUser(address indexed id);

    event UpdateThemisUser(address indexed id);

    event RemoveThemisUser(address indexed id);

    // Info of a normal user
    struct UserInfo {
        // Address of hoster which used as id
        address id;

        // Fame of user
        // TODO can it less than zero?
        uint256 fame;

        // Public key of user used for others to encrypt data
        string publicKey;

        // User type
        UserType userType;
    }


    /**
     * @dev Vaildate user type
     * @param _userType User type to be validated
     */
    modifier onlyValidatedType(UserType _userType) {
        require(_userType == UserType.Normal || _userType == UserType.Hoster);
        _;
    }


    /**
     * @dev Add new themis user
     * @param _id Id(address) of a themis user
     * @param _fame Fame of a new themis user
     * @param _publicKey Public key of a new themis user, which will be used to encrypted data
     * @param _userType User type of a themis user:Normal/Hoster
     */
    function addUser(
        address  _id,
        uint256  _fame,
        string   _publicKey,
        UserType _userType
    )
        public
        onlyOwner
        onlyValidatedType(_userType)
        returns(bool)
    {
        require(_id != address(0));
        require(users[_id].id == address(0));

        users[_id].id = _id;
        users[_id].fame = _fame;
        users[_id].publicKey = _publicKey;
        users[_id].userType = _userType;

        emit AddThemisUser(_id);
        return true;
    }


    /**
     * @dev Update user's info
     * @param _id ID of themis user
     * @param _newFame New fame of a user
     * @param _newPublicKey New public key of a user
     * @param _userType New type of a new user
     */
    function updateUser(
        address  _id,
        uint256  _newFame,
        string   _newPublicKey,
        UserType _userType
    )
        public
        onlyOwner
        onlyValidatedType(_userType)
        returns(bool)
    {
        // Ensure user is exist
        require(users[_id].id == _id);

        users[_id].id = _id;
        users[_id].fame = _newFame;
        users[_id].publicKey = _newPublicKey;
        users[_id].userType = _userType;

        emit UpdateThemisUser(_id);
        return true;
    }


    /**
     * @dev Remove user
     * @param _id ID of a themis user
     */
    function removeUser(address _id) public onlyOwner returns(bool) {
        // Delete user
        delete(users[_id]);

        emit RemoveThemisUser(_id);
        return true;
    }


    /**
     * @dev Check a address is themis user or not
     * @param _user ID of a themis user
     */
    function isThemisUser(address _user) public view returns(bool) {
        require(_user != address(0));
        return users[_user].id == _user;
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

    event GetThemisHosters(address[] hosters);

    /**
     * @dev Only hoster can call this method
     */
    modifier onlyHoster() {
        require(msg.sender == hoster[idIndex[msg.sender]].id);
        _;
    }


    /**
     * @dev Can be called only by themis user
     */
    modifier onlyThemisUser() {
        require(super.isThemisUser(msg.sender));
        _;
    }


    /**
     * @dev Init hoster contract
     * @param _feeManagerAddress Address of fee manage contract which controls workflow of fee payed
     */
    function Hoster(address _feeManagerAddress) public {
        require(_feeManagerAddress != address(0));

        feeManager = FeeManager(_feeManagerAddress);
        // Ensure zero will not be used for index
        hoster.push(HosterInfo(address(0), 0));
    }


    /**
     * @dev Validate hoster
     * @param _who ID of a hoster
     */
    function isHoster(address _who) public view returns(bool) {
        bool exist;
        uint256 i;
        uint256 j;
        (exist, i, j) = sortedHosterIndex.getNode(idIndex[_who]);

        if (!exist) {
            return false;
        }

        uint256 index = idIndex[_who];
        if (index == 0) {
            return false;
        }

        if (hoster[index].id != _who) {
            return false;
        }

        return true;
    }


    /**
     * @dev Add a new hoster who is not a themis user before
     * @param _id ID of a hoster
     * @param _fame Fame of a hoster
     * @param _deposit Deposit of a hoster
     * @param _publicKey Public Key of hoster, which used to encrypt data
     */
    function addHoster(
        address _id,
        uint256 _fame,
        uint256 _deposit,
        string  _publicKey
    )
        public
        onlyOwner
        returns(bool)
    {

        require(_id != address(0));
        // Ensure user haven't been added before
        require(idIndex[_id] == 0);
        // Should call UpdateNormalUserToHoster when a address is themis user before
        require(!super.isThemisUser(_id));

        // Pay deposit
        assert(feeManager.payDeposit(_id, _deposit));

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
        super.addUser(_id, _fame, _publicKey, UserType.Hoster);

        emit AddThemisHoster(_id);
        return true;
    }


    /**
     * @dev Update a normal user to hoster, which should apply deposit
     * @param _id ID of a hoster
     * @param _deposit Deposit of a hoster
     */
    function updateNormalUserToHoster(
        address _id,
        uint256 _deposit
    )
        public
        onlyOwner
        returns(bool)
    {
        // Only themis user can be updated to hoster
        require(super.isThemisUser(_id));
        // Pay deposit
        assert(feeManager.payDeposit(_id, _deposit));

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

        super.updateUser(_id, users[_id].fame, users[_id].publicKey, UserType.Hoster);
        emit ChangeToThemisHoster(_id);
        return true;
    }


    /**
     * @dev Remove a hoster, but retains themis user
     * @param _id ID of a hoster
     */
    function removeHoster(address _id) public onlyOwner returns(bool) {
        // Simple check
        require(_id != address(0));
        require(idIndex[_id] != 0);

        // With draw deposit
        assert(feeManager.withDrawDeposit(_id));

        // Set all info to zero/init
        sortedHosterIndex.remove(idIndex[_id]);
        hoster[idIndex[_id]] = HosterInfo(address(0), 0);
        idIndex[_id] = 0;

        super.updateUser(_id, users[_id].fame, users[_id].publicKey, UserType.Normal);

        emit RemoveThemisHoster(_id);
        return true;
    }


    /**
     * @dev Update host's fame which will cause reorder
     * @param _id ID of a hoster
     * @param _newFame New fame of a hoster
     */
    function updateUserFame(address _id, uint256 _newFame) public onlyOwner returns(bool) {
        // User should have been added before
        // The index of first node is 1, so no need to do more check
        require(idIndex[_id] != 0);

        uint256 oldDeposit = hoster[idIndex[_id]].deposit;
        require(updateUserFameOrDeposit(_id, _newFame, oldDeposit) == true);

        super.updateUser(_id, _newFame, users[_id].publicKey, users[_id].userType);

        return true;
    }


    /**
     * @dev User update his/her deposit
     * @param _newDeposit New deposit of a hoster
     */
    function updateUserDeposit(uint256 _newDeposit) public onlyHoster returns(bool) {
        // User should have been added before
        // The index of first node is 1, so no need to do more check
        address _id = msg.sender;
        require(idIndex[_id] != 0);

        // Update deposit payed by hoster
        // Will throw when _newDeposit is same with original deposit
        assert(feeManager.updateToNewDepsoit(msg.sender, _newDeposit));

        require(updateUserFameOrDeposit(_id, users[_id].fame, _newDeposit) == true);

        super.updateUser(_id, users[_id].fame, users[_id].publicKey, users[_id].userType);

        return true;
    }


    /**
     * @dev Return hoster array ordered by fame, deposit
     * @dev Log result
     * @param _num Number of hoster want to get
     */
    function getHosters(uint256 _num) public returns(address[]) {
        require(_num > 0);

        // Get node from list sequentially
        bool exist;
        uint256 i;

        // Use storage may cause "out of gas"
        address[] memory hostersList = new address[](_num);

        // List not exist
        (exist, i) = sortedHosterIndex.getAdjacent(HEAD, NEXT);
        if (!exist) {
            return hostersList;
        }

        uint256 numElements;
        while (i != HEAD) {
            hostersList[numElements] = hoster[i].id;
            numElements++;
            if (numElements >= _num) {
                break;
            }
            (exist,i) = sortedHosterIndex.getAdjacent(i, NEXT);
        }

        // Remove empty address
        if (numElements < _num) {
            address[] memory resultList = new address[](numElements);
            for (uint256 j = 0; j < numElements; j++) {
                resultList[j] = hostersList[j];
            }

            emit GetThemisHosters(resultList);

            return resultList;
        }

        emit GetThemisHosters(hostersList);

        // If num is bigger than size of hoster, just return all hosters
        return hostersList;
    }


    /**
     * @dev Update hoster' fame/deposit and reorder list of hoster
     * @param _id ID of a hoster
     * @param _newFame New fame of a hoster
     * @param _newDeposit New deposit of a hoster
     */
    function updateUserFameOrDeposit(
        address _id,
        uint256 _newFame,
        uint256 _newDeposit
    )
        internal
        returns(bool)
    {
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


    /**
     * @dev Get position of a new node to insert into with given fame and deposit
     * @param _fame Fame of a hoster
     * @param _deposit Fame of a deposit
     */
    function getInsertPosition(
        uint256 _fame,
        uint256 _deposit
    )
        internal
        view
        returns(bool, uint256, bool)
    {
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

