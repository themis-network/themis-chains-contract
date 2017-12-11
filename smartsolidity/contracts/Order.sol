pragma solidity ^0.4.14;

/**
 *
 */
library Utils {

    /**
     * @dev copy from https://ethereum.stackexchange.com/questions/9142/how-to-convert-a-string-to-bytes32
     */
    function stringToBytes32(string memory source) internal pure returns (bytes32 result) {
        bytes memory tempEmptyStringTest = bytes(source);
        if (tempEmptyStringTest.length == 0) {
            return 0x0;
        }

        assembly {
            result := mload(add(source, 32))
        }
    }

    /**
     * @dev copy from https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
     */
    function bytes32ToString(bytes32 x) internal pure returns (string) {
        bytes memory bytesString = new bytes(32);
        uint charCount = 0;
        for (uint j = 0; j < 32; j++) {
            byte char = byte(bytes32(uint(x) * 2 ** (8 * j)));
            if (char != 0) {
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for (j = 0; j < charCount; j++) {
            bytesStringTrimmed[j] = bytesString[j];
        }
        return string(bytesStringTrimmed);
    }
}

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

/*
** @title HostingService for order
*/
contract Order is Ownable {

    struct Trustee {
        string trusteeId;
        string hostingEncryBuyerPrivKey;
        string hostingEncrySellerPrivKey;
    }

    struct OrderStruct {
        string buyerId;
        string buyerEncryPrivKey;
        string sellerId;
        string sellerEncryPrivKey;
        mapping(bytes32 => Trustee) custodian;
    }

    mapping(bytes32 => OrderStruct) public orders;

    /**
     * @dev no need to initialize
     */
    function Order() public {

    }

    /**
     * @dev This should be request first when getting a new hosting request by user to set
     * @dev up the order data
     */
    function RequestHostingService (
        string orderId,
        string buyerId,
        string buyerEncryPrivKey,
        string sellerId,
        string sellerEncryPrivKey
    ) public onlyOwner {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        orders[orderBytes32Id].buyerId = buyerId;
        orders[orderBytes32Id].buyerEncryPrivKey = buyerEncryPrivKey;
        orders[orderBytes32Id].sellerId = sellerId;
        orders[orderBytes32Id].sellerEncryPrivKey = sellerEncryPrivKey;
    }

    /**
     * @dev This should be request right after RequestHostingService()
     */
    function RequestHostingServiceTrustee(
        string orderId,
        string trusteeId,
        string hostingEncryBuyerPrivKey,
        string hostingEncrySellerPrivKey
    ) public onlyOwner {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        bytes32 trusteeBytes32Id = Utils.stringToBytes32(trusteeId);
        Trustee memory tmpTrustee = Trustee(trusteeId, hostingEncryBuyerPrivKey, hostingEncrySellerPrivKey);
        orders[orderBytes32Id].custodian[trusteeBytes32Id] = tmpTrustee;
    }

    //TODO maybe it's better aparting from null
    function GetEncryBuyerPrivKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].buyerEncryPrivKey;
    }

    function GetEncrySellerPrivKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].sellerEncryPrivKey;
    }

    function GetTrusteeStoreBuyerOrSellerEncryPrivKey(string orderId, string trusteeId, uint buyerOrSeller) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        bytes32 trusteeBytes32Id = Utils.stringToBytes32(trusteeId);

        if (buyerOrSeller == 1) {
            return orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncryBuyerPrivKey;
        }

        if (buyerOrSeller == 2) {
            return orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncrySellerPrivKey;
        }
    }
}