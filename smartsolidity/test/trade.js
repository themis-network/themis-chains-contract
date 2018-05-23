import assertRevert from "zeppelin-solidity/test/helpers/assertRevert";
import { assertEquals, assertBigger } from "./help";

const FeeManager = artifacts.require("./FeeManager");
const Trade = artifacts.require("Trade");
const Vss = artifacts.require("Vss");
const Hoster = artifacts.require("Hoster");

const NORMAL_USER = 0;
const testFee = web3.toWei(3, "ether");
const BigNumber = web3.BigNumber;
const should = require('chai')
    .use(require('chai-as-promised'))
    .use(require('chai-bignumber')(BigNumber))
    .should();

contract("Trade test", function (accounts) {

    before(async function () {
        this.feeRate = web3.toWei(1, "ether");
        this.FeeManagerIns = await FeeManager.new();
        this.VssIns = await Vss.new();
        this.TradeIns = await Trade.new(this.FeeManagerIns.address, this.VssIns.address);
        this.HosterIns = await Hoster.new(this.FeeManagerIns.address);

        // Set Hoster/trade contract address
        await this.FeeManagerIns.updateHosterContract(this.HosterIns.address);
        await this.FeeManagerIns.updateTradeContract(this.TradeIns.address);

        await this.TradeIns.updateHosterContract(this.HosterIns.address);

        // Add hoster 1
        const hoster_1 = accounts[3];
        const hoster_1Fame = 7;
        const hoster_1Deposit = web3.toWei(10, "ether");
        const hoster_1PublicKey = "asdf123";
        await this.HosterIns.addHoster(hoster_1, hoster_1Fame, hoster_1PublicKey, {value: hoster_1Deposit});

        // Add hoster 2
        const hoster_2 = accounts[4];
        const hoster_2Fame = 6;
        const hoster_2Deposit = web3.toWei(10, "ether");
        const hoster_2PublicKey = "asdf123";
        await this.HosterIns.addHoster(hoster_2, hoster_2Fame, hoster_2PublicKey, {value: hoster_2Deposit});

        // Add hoster 3
        const hoster_3 = accounts[5];
        const hoster_3Fame = 5;
        const hoster_3Deposit = web3.toWei(10, "ether");
        const hoster_3PublicKey = "asdf123";
        await this.HosterIns.addHoster(hoster_3, hoster_3Fame, hoster_3PublicKey, {value: hoster_3Deposit});

        // Add hoster 4
        const hoster_4 = accounts[6];
        const hoster_4Fame = 4;
        const hoster_4Deposit = web3.toWei(10, "ether");
        const hoster_4PublicKey = "asdf123";
        await this.HosterIns.addHoster(hoster_4, hoster_4Fame, hoster_4PublicKey, {value: hoster_4Deposit});
    });


    it("should right create order and pay fee(GET) for it", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 1;

        // Add buyer to themis user
        const newUser = buyer;
        const fame = 0;
        const publicKey = "adfsfs";
        await this.HosterIns.addUser(newUser, fame, publicKey, NORMAL_USER);
        let isThemisUser = await this.HosterIns.isThemisUser(newUser);
        assert.equal(isThemisUser, true, "should be right added to themis user");

        // Add seller to themis user
        const sellerFame = 2;
        const sellerPublicKey = "asdfwe";
        await this.HosterIns.addUser(seller, sellerFame, sellerPublicKey, NORMAL_USER);

        const buyerBalanceBefore = await web3.eth.getBalance(buyer);

        // User should pay GET tokens for this server
        await this.TradeIns.createNewTradeOrder(orderID, seller, 3, {from: buyer, value: testFee});

        let actualBuyer = await this.TradeIns.getBuyer(orderID);
        assert.equal(actualBuyer, buyer, "buyer should be right set");

        let acutalSeller = await this.TradeIns.getSeller(orderID);
        assert.equal(acutalSeller, seller, "seller should be right set");

        const buyerBalanceAfter = await web3.eth.getBalance(buyer);
        const feePayed = buyerBalanceBefore.sub(buyerBalanceAfter);

        // Pay some fee for transaction
        assertBigger(feePayed, testFee, "feePayed should a little bigger than hoster get");
    })

    it("should return actual hosters(even) when number given by buyer is bigger than number of hosters", async function () {
        // Condition: 4 hosters, but buyer want to get 5 hosters, contract will auto get 3(even) hosters
        let buyer = accounts[1];
        let seller = accounts[2];
        let orderID = 33;
        let hostersLen = 5;

        // Try to get 5 hosters
        await this.TradeIns.createNewTradeOrder(orderID, seller, hostersLen, {from: buyer, value: testFee});

        const actualHosters = await this.TradeIns.getShardHosters(orderID);
        assert.equal(actualHosters.length, 3, "should auto select 3 hosters in condition below");
    })


    it("can not create two order with same orderID", async function() {
        // buyer is added to themis user before
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 1;

        // Have approve enough get tokens for fee manager contract
        await assertRevert(this.TradeIns.createNewTradeOrder(orderID, seller, 3, {from: buyer, value: testFee}));
    })


    it("only buyer/seller can upload encrypted shard(mean request artibaion service)", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const others = accounts[8];
        const orderID = 2;

        await this.TradeIns.createNewTradeOrder(orderID, seller, 3, {from: buyer, value: testFee});

        // Only buyer can upload seller's encrypted shard
        const sellerEncryptedShard_1 = "asbdsdfbheg34";
        const sellerEncryptedShard_2 = "asbasdfdg34";
        const sellerEncryptedShard_3 = "asbd3rtwfg34";
        const sellerShard = sellerEncryptedShard_1 + "," + sellerEncryptedShard_2 + "," + sellerEncryptedShard_3;

        // This service will cost GET Tokens
        // Check shard is right uploaded
        const host_1 = accounts[3];
        const host_2 = accounts[4];
        const host_3 = accounts[5];

        let host_1_balance_before = await web3.eth.getBalance(host_1);
        let host_2_balance_before = await web3.eth.getBalance(host_2);
        let host_3_balance_before = await web3.eth.getBalance(host_3);

        await this.TradeIns.uploadSellerShardFromBuyer(orderID, sellerShard, {from: buyer, value: testFee});

        // reject calls from others except buyer/seller
        await assertRevert(this.TradeIns.uploadSellerShardFromBuyer(orderID, sellerShard, {from: others, value: testFee}));

        // SELLER indicate this is seller's shard uploaded by buyer
        let shard_1 = await this.TradeIns.getSellerShardByHosterID(orderID, host_1);
        let shard_2 = await this.TradeIns.getSellerShardByHosterID(orderID, host_2);
        let shard_3 = await this.TradeIns.getSellerShardByHosterID(orderID, host_3);

        assert.equal(shard_1, sellerEncryptedShard_1, "shard_1 should be the same");
        assert.equal(shard_2, sellerEncryptedShard_2, "shard_2 should be the same");
        assert.equal(shard_3, sellerEncryptedShard_3, "shard_3 should be the same");

        // Check hoster get fee
        let host_1_balance = await web3.eth.getBalance(host_1);
        let host_2_balance = await web3.eth.getBalance(host_2);
        let host_3_balance = await web3.eth.getBalance(host_3);

        let getBalance_1 = host_1_balance.sub(host_1_balance_before);
        let getBalance_2 = host_2_balance.sub(host_2_balance_before);
        let getBalance_3 = host_3_balance.sub(host_3_balance_before);

        assertEquals(getBalance_1, this.feeRate, "host_1 should get 1*this.feeRate get coins");
        assertEquals(getBalance_2, this.feeRate, "host_1 should get 1*this.feeRate get coins");
        assertEquals(getBalance_3, this.feeRate, "host_1 should get 1*this.feeRate get coins");
    })


    it("hoster can upload buyer/seller's decrypted shard means another wins conflict", async function () {
        // order created before
        const buyer = accounts[1];
        const orderID = 2;
        const decrypted_shard_1 = "sdsdfgwertydfh";
        const decrypted_shard_2 = "asdfwexbvert";
        const decrypted_shard_3 = "456fghfgh";
        // Check shard is right uploaded
        const host_1 = accounts[3];
        const host_2 = accounts[4];
        const host_3 = accounts[5];

        // Check hoster'id is right stored
        let hosters = await this.TradeIns.getShardHosters(orderID);
        hosters[0].should.equal(host_1);
        hosters[1].should.equal(host_2);
        hosters[2].should.equal(host_3);

        // This means buyer wins
        await this.TradeIns.uploadSellerDecryptedShard(orderID, decrypted_shard_1, {from:host_1});
        await this.TradeIns.uploadSellerDecryptedShard(orderID, decrypted_shard_2, {from:host_2});
        await this.TradeIns.uploadSellerDecryptedShard(orderID, decrypted_shard_3, {from:host_3});

        // Check decrypted shard is right uploaded
        let actual_decrypted_shard_1 = await this.TradeIns.getSellerDecryptedShard(orderID, host_1, {from: buyer});
        let actual_decrypted_shard_2 = await this.TradeIns.getSellerDecryptedShard(orderID, host_2, {from: buyer});
        let actual_decrypted_shard_3 = await this.TradeIns.getSellerDecryptedShard(orderID, host_3, {from: buyer});

        actual_decrypted_shard_1.should.equal(decrypted_shard_1);
        actual_decrypted_shard_2.should.equal(decrypted_shard_2);
        actual_decrypted_shard_3.should.equal(decrypted_shard_3);
    })

    it("should revert if hoster want to upload both seller and buyer's decryptedShard", async function () {
        // hoster have uploaded seller's decrypted shard
        const decryptedShard = "sdf234";
        const orderID = 2;
        const hoster_1 = accounts[3];
        const hoster_2 = accounts[4];
        const hoster_3 = accounts[5];

        await assertRevert(this.TradeIns.uploadBuyerDecryptedShard(orderID, decryptedShard, {from: hoster_1}));
        await assertRevert(this.TradeIns.uploadBuyerDecryptedShard(orderID, decryptedShard, {from: hoster_2}));
        await assertRevert(this.TradeIns.uploadBuyerDecryptedShard(orderID, decryptedShard, {from: hoster_3}));
    })
});
