pragma solidity ^0.4.23;

import "./librarys/LinkedListLib.sol";
import "./Ownable.sol";
import "./librarys/SafeMath.sol";


contract Trustee is Ownable {
    using LinkedListLib for LinkedListLib.LinkedList;

    using SafeMath for uint256;

    // Index of all trustee sort by fame, deposit;
    LinkedListLib.LinkedList trusteeList;

    uint256 constant HEAD = 0;
    bool constant PREV = false;
    bool constant NEXT = true;

    struct TrusteeInfo {
        // Address of trustee which used as ID
        address ID;

        // Deposit should be payed
        uint256 deposit;

        // Fame of trustee
        // Should less than 2^32(4294967296)
        uint32 fame;

        // Encrypt key of trustee used for others to encrypt data
        string encryptKey;
    }

    // Array of trustees
    TrusteeInfo[] public trustees;

    mapping(address => uint256) public IDIndex;

    event LogAddTrustee(address indexed ID);

    event LogRemoveTrustee(address indexed ID);

    event LogGetTrustees(address[] trustees);

    event LogIncreaseDeposit(address indexed trustee, uint256 amount);

    event LogDecreaseDeposit(address indexed trustee, uint256 amount);

    event LogUpdateTrusteeFame(address indexed trustee, uint32 newFame);

    event LogUpdateTrusteeEncryptKey(address indexed trustee, string encryptKey);


    /**
     * @dev Can be called only by trustee
     */
    modifier onlyTrustee() {
        require(isTrustee(msg.sender));
        _;
    }


    /**
     * @dev Init trustee contract
     */
    constructor() public {
        // Ensure zero will not be used for index
        trustees.push(TrusteeInfo(address(0), 0, 0, ""));
    }


    /**
     * @dev ValIDate trustee
     * @param who ID of a trustee
     */
    function isTrustee(address who) public view returns(bool) {
        uint256 index = IDIndex[who];

        if (index == 0) {
            return false;
        }

        // Check node info
        bool exist;
        uint256 i;
        uint256 j;
        (exist, i, j) = trusteeList.getNode(index);

        if (!exist) {
            return false;
        }

        if (trustees[index].ID != who) {
            return false;
        }

        return true;
    }


    /**
     * @dev Add a new trustee
     * @dev Init deposit of a trustee is 0, trustee should use increaseDeposit() to pay some deposit 
     * @param ID ID of a trustee
     * @param fame Fame of a trustee
     * @param encryptKey Encrypt Key of trustee, which used to encrypt data
     */
    function addTrustee(
        address ID,
        uint32 fame,
        string  encryptKey
    )
        public
        onlyOwner
        returns(bool)
    {
        // User should haven't been trustee
        require(!isTrustee(ID));

        bool success;
        uint256 position;
        bool direction;

        // Get position where to insert
        (success, position, direction) = getInsertPosition(fame, 0);
        require(success);

        // Deposit will be set 0 when added by owner
        trustees.push(TrusteeInfo(ID, 0, fame, encryptKey));

        // Update list and IDIndex
        trusteeList.insert(position, trustees.length - 1, direction);
        IDIndex[ID] = trustees.length - 1;

        emit LogAddTrustee(ID);
        return true;
    }


    /**
     * @dev Remove a trustee and send deposit back to trustee
     * @param ID ID of a trustee
     */
    function removeTrustee(address ID) public onlyOwner returns(bool) {
        // Simple check
        require(isTrustee(ID));

        uint256 deposit = trustees[IDIndex[ID]].deposit;

        // Set all info to zero/init
        trusteeList.remove(IDIndex[ID]);
        trustees[IDIndex[ID]] = TrusteeInfo(address(0), 0, 0, "");
        IDIndex[ID] = 0;

        // Withdraw deposit
        // Do nothing when deposit == 0
        if (deposit > 0) {
            ID.transfer(deposit);
        }

        emit LogRemoveTrustee(ID);
        return true;
    }


    /**
     * @dev Update Trustee's fame which will cause reorder
     * @param ID ID of a trustee
     * @param newFame New fame of a trustee
     */
    function updateTrusteeFame(address ID, uint32 newFame) public onlyOwner returns(bool) {
        require(isTrustee(ID));

        uint256 oldDeposit = trustees[IDIndex[ID]].deposit;
        require(updateTrusteeFameOrDeposit(ID, newFame, oldDeposit) == true);
        emit LogUpdateTrusteeFame(ID, newFame);

        return true;
    }


    /**
     * @dev Trustee increase his/her deposit
     */
    function increaseDeposit() external onlyTrustee payable returns(bool) {
        require(msg.value > 0);

        address ID = msg.sender;
        uint32 oldFame = trustees[IDIndex[ID]].fame;
        uint256 newDeposit = trustees[IDIndex[ID]].deposit.add(msg.value);

        // Update deposit payed by trustee
        require(updateTrusteeFameOrDeposit(ID, oldFame, newDeposit) == true);
        emit LogIncreaseDeposit(msg.sender, msg.value);

        return true;
    }


    /**
     * @dev Trustee decrease deposit and withdraw originalDeposit - newDeposit
     * @param amount Amount of deposit want to decrease
     */
    function decreaseDeposit(uint256 amount) public onlyTrustee returns(bool) {
        require(amount > 0);

        address ID = msg.sender;
        uint32 oldFame = trustees[IDIndex[ID]].fame;
        uint256 oldDeposit = trustees[IDIndex[ID]].deposit;
        require(amount <= oldDeposit);
        uint256 newDeposit = oldDeposit.sub(amount);

        require(updateTrusteeFameOrDeposit(ID, oldFame, newDeposit) == true);
        // Withdraw deposit
        msg.sender.transfer(amount);
        emit LogDecreaseDeposit(msg.sender, amount);

        return true;
    }


    /**
     * @dev Update encrypt key
     * @param newEncryptKey New encrypt key
     */
    function updateEncryptKey(string newEncryptKey) public onlyTrustee returns(bool) {
        trustees[IDIndex[msg.sender]].encryptKey = newEncryptKey;
        emit LogUpdateTrusteeEncryptKey(msg.sender, newEncryptKey);
        return true;
    }


    /**
     * @dev Get trustee info
     * @param who ID of trustee
     */
    function getTrusteeInfo(address who) public view returns(uint256, uint256, string) {
        return (trustees[IDIndex[who]].fame, trustees[IDIndex[who]].deposit, trustees[IDIndex[who]].encryptKey);
    }


    /**
     * @dev Return trustee array ordered by fame, deposit
     * @dev Log result
     * @param number Number of trustee want to get
     */
    function getTrustees(uint256 number) public returns(address[]) {
        require(number > 0);

        // Get node from list sequentially
        bool exist;
        uint256 i;

        address[] memory trusteesList = new address[](number);

        // List not exist
        (exist, i) = trusteeList.getAdjacent(HEAD, NEXT);
        if (!exist) {
            // TODO change return
            return new address[](0);
        }

        uint256 numElements;
        while (i != HEAD) {
            trusteesList[numElements] = trustees[i].ID;
            numElements++;
            if (numElements >= number) {
                break;
            }
            (exist,i) = trusteeList.getAdjacent(i, NEXT);
        }

        // Remove empty address
        if (numElements < number) {
            address[] memory resultList = new address[](numElements);
            for (uint256 j = 0; j < numElements; j++) {
                resultList[j] = trusteesList[j];
            }

            emit LogGetTrustees(resultList);

            return resultList;
        }

        emit LogGetTrustees(trusteesList);

        // If num is bigger than size of trustee, just return all trustees
        return trusteesList;
    }


    /**
     * @dev Update trustee' fame/deposit and reorder list of trustee
     * @param ID ID of a trustee
     * @param newFame New fame of a trustee
     * @param newDeposit New deposit of a trustee
     */
    function updateTrusteeFameOrDeposit(
        address ID,
        uint32 newFame,
        uint256 newDeposit
    )
        internal
        returns(bool)
    {
        // No need to check ID, which have been checked by calling function
        // Remove and reinsert it to list
        bool success;
        uint256 position;
        bool direction;
        trusteeList.remove(IDIndex[ID]);
        (success, position, direction) = getInsertPosition(newFame, newDeposit);
        require(success);

        // Update trustee fame and deposit
        trustees[IDIndex[ID]] = TrusteeInfo(ID, newDeposit, newFame, trustees[IDIndex[ID]].encryptKey);

        // Reinsert node to list
        trusteeList.insert(position, IDIndex[ID], direction);

        return true;
    }


    /**
     * @dev Get position of a new node to insert into with given fame and deposit
     * @param fame Fame of a trustee
     * @param deposit Fame of a deposit
     */
    function getInsertPosition(
        uint32 fame,
        uint256 deposit
    )
        internal
        view
        returns(bool, uint256, bool)
    {
        // Get first node
        bool exist;
        uint256 i;
        (exist, i) = trusteeList.getAdjacent(HEAD, NEXT);

        // Insert node to right of head when list is empty
        if (!exist || i == HEAD) {
            return (true, HEAD, NEXT);
        }
        // Should be insert to first whose fame is biggest
        if (trustees[i].fame < fame) {
            return (true, HEAD, NEXT);
        }
        if (trustees[i].fame == fame && trustees[i].deposit <= deposit) {
            return (true, HEAD, NEXT);
        }

        uint256 before = i;
        // Should be insert after first one
        // fame is <= before's fame
        while (i != HEAD) {
            (exist, i) = trusteeList.getAdjacent(i, NEXT);
            // Reach end of the list
            if (!exist) {
                return (true, before, NEXT);
            }

            // Check the position is right between before and after
            // Situation 1: before'fame > insert one's fame > i's fame
            // Examples A: Before one is {fame:7, deposit:5}, insert one is {fame:6, deposit:4}, after one is {fame:5, deposit:5}
            if (trustees[before].fame > fame) {
                if (trustees[i].fame < fame) {
                    return (true, before, NEXT);
                }

                if (trustees[i].fame == fame && trustees[i].deposit <= deposit) {
                    return (true, before, NEXT);
                }
            }

            // Situation 2:
            // Before one's fame == insert one's fame
            // Examples A: Before one is {fame:7, deposit:5}, insert one is {fame:7, deposit:4}, after one is {fame:6, deposit:5}
            // Examples B: Before one is {fame:7, deposit:5}, insert one is {fame:7, deposit:4}, after one is {fame:7, deposit:3}
            if (trustees[before].fame == fame) {
                if (trustees[i].fame < fame && trustees[before].deposit >= deposit) {
                    return (true, before, NEXT);
                }

                if (trustees[i].fame == fame && trustees[before].deposit >= deposit && trustees[i].deposit <= deposit) {
                    return (true, before, NEXT);
                }
            }

            before = i;
        }

        return (false, 0, false);
    }
}

