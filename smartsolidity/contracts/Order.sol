pragma solidity ^0.4.14;

import "./Ownable.sol";
import "./Utils.sol";

/*
** @title HostingService for order
*/
contract Order is Ownable {

    uint constant buyer = 1;
    uint constant seller = 2;

    struct Trustee {
        string hostingEncryBuyerPrivKey;
        string hostingEncrySellerPrivKey;
    }

    struct OrderStruct {
        // string[0]: buyerId; string[1] sellerId;
        string[2] userId;
        // string[0]: public key of buyer; string[1]: public key of seller;
        string[2] userPublicKey;
        // string[0]: priv key of buyer; string[1]: priv key of seller;
        string[2] userPrivKey;
        // shard key holded by buyer;
        string[] buyerKeyShard;
        // shard key holded by seller;
        string[] sellerKeyShard;
        // the public key of virTurstee
        string virTrusteePublicKey;
        // the privkey of virTurstee
        string virTrusteePrivKey;
        // the amount holding shard key of who will win when a debate accuring
        uint K;
        // the amount of trustees
        uint N;
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
        string buyerPublicKey,
        string buyerEncryPrivKey,
        string virTrusteePublicKey,
        string virTrusteePrivKey,
        string sellerId,
        uint K,
        uint N
    ) public onlyOwner {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);

        orders[orderBytes32Id].userId[0] = buyerId;
        orders[orderBytes32Id].userId[1] = sellerId;

        orders[orderBytes32Id].userPublicKey[0] = buyerPublicKey;
        orders[orderBytes32Id].userPrivKey[0] = buyerEncryPrivKey;

        orders[orderBytes32Id].virTrusteePublicKey = virTrusteePublicKey;
        orders[orderBytes32Id].virTrusteePrivKey = virTrusteePrivKey;

        orders[orderBytes32Id].K = K;
        orders[orderBytes32Id].N = N;
    }

    /**
     * @dev seller upload pub/priv key
     */
    function UploadSellerKey(
        string orderId,
        string sellerPublicKey,
        string sellerEncryPrivKey
    ) public onlyOwner {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);

        orders[orderBytes32Id].userPublicKey[1] = sellerPublicKey;
        orders[orderBytes32Id].userPrivKey[1] = sellerEncryPrivKey;
    }

    /**
     * @dev This should be request right after RequestHostingService()
     */
    function UploadShardKeyToTrustee(
        string orderId,
        string trusteeId,
        string hostingEncryPrivKey,
        uint   userType
    ) public onlyOwner {
        // check the validity of userType
        require(userType == buyer || userType == seller);

        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        bytes32 trusteeBytes32Id = Utils.stringToBytes32(trusteeId);

        if (userType == buyer) {
            orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncryBuyerPrivKey = hostingEncryPrivKey;
        }

        if (userType == seller) {
            orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncrySellerPrivKey = hostingEncryPrivKey;
        }
    }

    function GetEncryBuyerPrivKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].userPrivKey[0];
    }

    function GetBuyerPublicKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].userPublicKey[0];
    }

    function GetEncrySellerPrivKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].userPrivKey[1];
    }

    function GetSellerPublicKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].userPublicKey[1];
    }

    function GetVirTrusteePublicKey(string orderId) public constant returns(string) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        return orders[orderBytes32Id].virTrusteePublicKey;
    }

    function GetTrusteeStoreBuyerOrSellerEncryPrivKey(string orderId, string trusteeId, uint userType) public constant returns(string) {

        // check the validity of userType
        require(userType == buyer || userType == seller);

        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        bytes32 trusteeBytes32Id = Utils.stringToBytes32(trusteeId);

        if (userType == buyer) {
            return orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncryBuyerPrivKey;
        }

        if (userType == seller) {
            return orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncrySellerPrivKey;
        }
    }

    // decide who win of an order(orderid) return id or state(0 null, 1 buyer, 2 seller)
    function JudgeWhoWin(string orderId) onlyOwner public constant returns(uint) {
        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);

        // TODO check the lenght of holded keys of trustee

        var canWinLength = orders[orderBytes32Id].K;

        var buyerHoldShardKeyLength = orders[orderBytes32Id].buyerKeyShard.length;
        if (buyerHoldShardKeyLength >= canWinLength) {
            return buyer;
        }

        var sellerHoldShardKeyLength = orders[orderBytes32Id].sellerKeyShard.length;
        if (sellerHoldShardKeyLength >= canWinLength) {
            return seller;
        }

        return 0;
    }

    // trustee judge buyer or seller win in a battle
    // will send his holding shard key to the one he think is right
    function JudgeUserWinByTrustee(string orderId, string trusteeId, uint userType) onlyOwner public returns(bool) {

        // check the validity of userType
        require(userType == buyer || userType == seller);

        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);
        bytes32 trusteeBytes32Id = Utils.stringToBytes32(trusteeId);

        // add the shard key holded by trustee to buyer's array(when a trustee decide he/she wins)
        if (userType == buyer) {
            string memory trusteeBuyerHoldShardKey = orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncryBuyerPrivKey;
            orders[orderBytes32Id].buyerKeyShard.push(trusteeBuyerHoldShardKey);
            return true;
        }

        // add the shard key holded by trustee to seller's array(when a trustee decide he/she wins)
        if (userType == seller) {
            string memory trusteeHoldSellerShardKey = orders[orderBytes32Id].custodian[trusteeBytes32Id].hostingEncrySellerPrivKey;
            orders[orderBytes32Id].sellerKeyShard.push(trusteeHoldSellerShardKey);
            return true;
        }
    }

    // get the winner's shard key holded by buyer/seller
    function GetWinerShardKey(string orderId, uint userType) view public returns(string){

        string memory spe = ",";

        // check the validity of userType
        require(userType == buyer || userType == seller);

        bytes32 orderBytes32Id = Utils.stringToBytes32(orderId);

        uint i = 0;

        if (userType == buyer) {

            string memory res = "";

            // check buyer wins or not
            uint buyerKeyLength = orders[orderBytes32Id].buyerKeyShard.length;
            require(buyerKeyLength >= orders[orderBytes32Id].K);

            for(i = 0; i < buyerKeyLength; i++) {
                string memory tmpBuyerKey = orders[orderBytes32Id].buyerKeyShard[i];

                if (i != buyerKeyLength - 1) {
                    res = Utils.strConcatThree(res, tmpBuyerKey, spe);
                } else {
                    res = Utils.strConcat(res, tmpBuyerKey);
                }
            }

            return res;
        }

        if (userType == seller) {
            string memory resSeller = "";
            // check seller wins or not
            uint sellerKeyLength = orders[orderBytes32Id].sellerKeyShard.length;
            require(sellerKeyLength >= orders[orderBytes32Id].K);

            for(i = 0; i < sellerKeyLength; i++) {
                string memory tmpSellerKey = orders[orderBytes32Id].sellerKeyShard[i];

                if (i != sellerKeyLength - 1) {
                    resSeller = Utils.strConcatThree(resSeller, tmpSellerKey, spe);
                } else {
                    resSeller = Utils.strConcat(resSeller, tmpSellerKey);
                }
            }

            return resSeller;
        }

    }


}