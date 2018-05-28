import assertRevert from "zeppelin-solidity/test/helpers/assertRevert";
import { assertEquals, assertBigger } from "./help";

const FeeManager = artifacts.require("./FeeManager");
const Trade = artifacts.require("Trade");
const Hoster = artifacts.require("Hoster");

const NORMAL_USER = 0;
const BUYER = 0;
const SELLER = 1;
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
        this.TradeIns = await Trade.new();
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

        // Add hoster 5
        const hoster_5 = accounts[7];
        const hoster_5Fame = 4;
        const hoster_5Deposit = web3.toWei(9, "ether");
        const hoster_5PublicKey = "sdfwww44";
        await this.HosterIns.addHoster(hoster_5, hoster_5Fame, hoster_5PublicKey, {value: hoster_5Deposit});
    });


    // TODO add fee manage
    it("should right create order by themis user", async function () {
        const buyer = accounts[1];
        const orderID = 1;

        // Add buyer to themis user
        const newUser = buyer;
        const fame = 0;
        const publicKey = "adfsfs";
        await this.HosterIns.addUser(newUser, fame, publicKey, NORMAL_USER);
        let isThemisUser = await this.HosterIns.isThemisUser(newUser);
        assert.equal(isThemisUser, true, "should be right added to themis user");

        await this.TradeIns.createNewTradeOrder(orderID, BUYER, {from: buyer});

        let actualBuyer = await this.TradeIns.getOrderBuyer(orderID);
        assert.equal(actualBuyer, buyer, "buyer should be right set");

    })

    it("can not create two order with same orderID", async function() {
        // buyer is added to themis user before
        const buyer = accounts[1];
        const orderID = 1;

        await assertRevert(this.TradeIns.createNewTradeOrder(orderID, BUYER, {from: buyer}));
    })

    it("only themis user can create order", async function() {
        // Buyer is not themis user
        const buyer = accounts[2];
        const orderID = 2;

        await assertRevert(this.TradeIns.createNewTradeOrder(orderID, BUYER, {from: buyer}));
    })


    it("trader can not confirm a order created by himself", async function () {
        const buyer = accounts[1];
        const orderID = 1;

        await assertRevert(this.TradeIns.confirmTradeOrder(orderID, {from: buyer}));
    })

    it("another themis user can confirm order, and should return actual hosters(even)", async function () {
        let seller = accounts[2];
        let orderID = 1;

        // Add seller to themis user
        const sellerFame = 2;
        const sellerPublicKey = "asdfwe";
        await this.HosterIns.addUser(seller, sellerFame, sellerPublicKey, NORMAL_USER);

        // Will　get five hosters
        await this.TradeIns.confirmTradeOrder(orderID, {from: seller});

        const actualHosters = await this.TradeIns.getOrderHosters(orderID);
        assert.equal(actualHosters.length, 5, "should auto get 5 hosters");

        const acutalSeller = await this.TradeIns.getOrderSeller(orderID);
        assert.equal(acutalSeller, seller, "seller should be right added");
    })


    it("only buyer/seller can upload encrypted shard", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const others = accounts[8];
        const orderID = 1;

        // Only buyer can upload seller's encrypted shard
        const sellerEncryptedShard_1 = "asbdsdfbheg34";
        const sellerEncryptedShard_2 = "asbasdfdg34";
        const sellerEncryptedShard_3 = "asbd3rtwfg34";
        const sellerEncryptedShard_4 = "asbd3rsstwfg34";
        const sellerEncryptedShard_5 = "asbd3rtwfallg34";
        const sellerShard = sellerEncryptedShard_1 + "," + sellerEncryptedShard_2 + "," + sellerEncryptedShard_3 + "," + sellerEncryptedShard_4 + "," + sellerEncryptedShard_5;

        // This service will cost GET Tokens
        // Check shard is right uploaded
        const host_1 = accounts[3];
        const host_2 = accounts[4];
        const host_3 = accounts[5];
        const host_4 = accounts[6];
        const host_5 = accounts[7];

        await this.TradeIns.uploadSecret(orderID, sellerShard, {from: buyer});

        // reject calls from others except buyer/seller
        await assertRevert(this.TradeIns.uploadSecret(orderID, sellerShard, {from: others}));

        // SELLER indicate this is seller's shard uploaded by buyer
        let shard_1 = await this.TradeIns.getSecret(orderID, host_1, SELLER);
        let shard_2 = await this.TradeIns.getSecret(orderID, host_2, SELLER);
        let shard_3 = await this.TradeIns.getSecret(orderID, host_3, SELLER);
        let shard_4 = await this.TradeIns.getSecret(orderID, host_4, SELLER);
        let shard_5 = await this.TradeIns.getSecret(orderID, host_5, SELLER);

        assert.equal(shard_1, sellerEncryptedShard_1, "seller shard_1 should be the same");
        assert.equal(shard_2, sellerEncryptedShard_2, "seller shard_2 should be the same");
        assert.equal(shard_3, sellerEncryptedShard_3, "seller shard_3 should be the same");
        assert.equal(shard_4, sellerEncryptedShard_4, "seller shard_4 should be the same");
        assert.equal(shard_5, sellerEncryptedShard_5, "seller shard_5 should be the same");


        // Seller upload buyer's shard
        const buyerEncryptedShard_1 = "sxecfdr4444";
        const buyerEncryptedShard_2 = "sxsdfcf24444";
        const buyerEncryptedShard_3 = "sxe123r4444";
        const buyerEncryptedShard_4 = "sxe3h4r5r4444";
        const buyerEncryptedShard_5 = "sxlyudr4444";
        const buyerShard = buyerEncryptedShard_1 + "," + buyerEncryptedShard_2 + "," + buyerEncryptedShard_3 + "," + buyerEncryptedShard_4 + "," + buyerEncryptedShard_5;
        await this.TradeIns.uploadSecret(orderID, buyerShard, {from: seller});

        let buyer_shard_1 = await this.TradeIns.getSecret(orderID, host_1, BUYER);
        let buyer_shard_2 = await this.TradeIns.getSecret(orderID, host_2, BUYER);
        let buyer_shard_3 = await this.TradeIns.getSecret(orderID, host_3, BUYER);
        let buyer_shard_4 = await this.TradeIns.getSecret(orderID, host_4, BUYER);
        let buyer_shard_5 = await this.TradeIns.getSecret(orderID, host_5, BUYER);

        assert.equal(buyer_shard_1, buyerEncryptedShard_1, "buyer shard_1 should be the same");
        assert.equal(buyer_shard_2, buyerEncryptedShard_2, "buyer shard_2 should be the same");
        assert.equal(buyer_shard_3, buyerEncryptedShard_3, "buyer shard_3 should be the same");
        assert.equal(buyer_shard_4, buyerEncryptedShard_4, "buyer shard_4 should be the same");
        assert.equal(buyer_shard_5, buyerEncryptedShard_5, "buyer shard_5 should be the same");
    })

    it("upload secret will be revert when secret's length is not same with hoster's length", async function () {
        const seller = accounts[2];
        const orderID = 1;

        // Seller upload buyer's shard
        const buyerEncryptedShard_1 = "sxecfdr4444";
        const buyerEncryptedShard_2 = "sxsdfcf24444";
        const buyerEncryptedShard_3 = "sxe123r4444";
        const buyerEncryptedShard_4 = "sxe3h4r5r4444";
        const buyerShard = buyerEncryptedShard_1 + "," + buyerEncryptedShard_2 + "," + buyerEncryptedShard_3 + "," + buyerEncryptedShard_4;
        await assertRevert(this.TradeIns.uploadSecret(orderID, buyerShard, {from: seller}));
    })

    it("buyer/seller can request for arbitration", async function () {
        const buyer = accounts[1];
        const orderID = 1;

        await this.TradeIns.arbitrate(orderID, {from: buyer});

        let requester = await this.TradeIns.getRequester(orderID);
        assert.equal(requester, buyer, "buyer can request for arbitration");
    })

    it("only judge can judge the order", async function () {
        const orderID = 1;
        const judge = accounts[9];
        await this.TradeIns.addArbitrator(judge);

        const isJudge = await this.TradeIns.isArbitrator(judge);
        assert.equal(isJudge, true, "should right add judge");

        const winner = accounts[1];
        await this.TradeIns.judge(orderID, winner, {from: judge});

        const acutalWinner = await this.TradeIns.getWinner(orderID);
        assert.equal(acutalWinner, winner, "shoud right judge who win");
    })
});
