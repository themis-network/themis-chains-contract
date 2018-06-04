import assertRevert from "zeppelin-solidity/test/helpers/assertRevert";
import { assertEquals, assertBigger } from "./help";

const Trade = artifacts.require("Trade");
const Trustee = artifacts.require("Trustee");

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
        this.TradeIns = await Trade.new();
        this.TrusteeIns = await Trustee.new();

        await this.TradeIns.updateTrusteeContract(this.TrusteeIns.address);

        // Add trustee 1
        const trustee_1 = accounts[3];
        const trustee_1Fame = 7;
        const trustee_1Deposit = web3.toWei(10, "ether");
        const trustee_1PublicKey = "asdf123";
        await this.TrusteeIns.addTrustee(trustee_1, trustee_1Fame, trustee_1PublicKey);
        await this.TrusteeIns.increaseDeposit({from: trustee_1, value: trustee_1Deposit});

        // Add trustee 2
        const trustee_2 = accounts[4];
        const trustee_2Fame = 6;
        const trustee_2Deposit = web3.toWei(10, "ether");
        const trustee_2PublicKey = "asdf123";
        await this.TrusteeIns.addTrustee(trustee_2, trustee_2Fame, trustee_2PublicKey);
        await this.TrusteeIns.increaseDeposit({from: trustee_2, value: trustee_2Deposit});

        // Add trustee 3
        const trustee_3 = accounts[5];
        const trustee_3Fame = 5;
        const trustee_3Deposit = web3.toWei(10, "ether");
        const trustee_3PublicKey = "asdf123";
        await this.TrusteeIns.addTrustee(trustee_3, trustee_3Fame, trustee_3PublicKey);
        await this.TrusteeIns.increaseDeposit({from: trustee_3, value: trustee_3Deposit});

        // Add trustee 4
        const trustee_4 = accounts[6];
        const trustee_4Fame = 4;
        const trustee_4Deposit = web3.toWei(10, "ether");
        const trustee_4PublicKey = "asdf123";
        await this.TrusteeIns.addTrustee(trustee_4, trustee_4Fame, trustee_4PublicKey);
        await this.TrusteeIns.increaseDeposit({from: trustee_4, value: trustee_4Deposit});

        // Add trustee 5
        const trustee_5 = accounts[7];
        const trustee_5Fame = 4;
        const trustee_5Deposit = web3.toWei(9, "ether");
        const trustee_5PublicKey = "sdfwww44";
        await this.TrusteeIns.addTrustee(trustee_5, trustee_5Fame, trustee_5PublicKey);
        await this.TrusteeIns.increaseDeposit({from: trustee_5, value: trustee_5Deposit});
    });


    it("should right create order by themis user", async function () {
        const buyer = 12345;
        const orderID = 1;
        const fee = web3.toWei(20, "ether");

        await this.TradeIns.createNewTradeOrder(orderID, buyer, BUYER, {value: fee});

        let actualBuyer = await this.TradeIns.getOrderBuyer(orderID);
        assert.equal(actualBuyer, buyer, "buyer should be right set");

    })

    it("can not create two order with same orderID", async function() {
        // buyer is added to themis user before
        const buyer = 12345;
        const orderID = 1;
        const fee = web3.toWei(20, "ether");

        await assertRevert(this.TradeIns.createNewTradeOrder(orderID, buyer, BUYER, {value: fee}));
    })

    it("only owner can create order", async function() {
        // Buyer is not themis user
        const buyer = 12345;
        const orderID = 2;
        const fee = web3.toWei(20, "ether");
        const others = accounts[1];

        await assertRevert(this.TradeIns.createNewTradeOrder(orderID, buyer, BUYER, {from: others, value: fee}));
    })


    it("seller/buyer can not be same when confirm an order", async function () {
        const seller = 12345;
        const orderID = 1;
        const fee = web3.toWei(20, "ether");

        await assertRevert(this.TradeIns.confirmTradeOrder(orderID, seller, {value: fee}));
    })

    it("another user can confirm order, and should return actual trustees(even)", async function () {
        let seller = 43213;
        let orderID = 1;
        const fee = web3.toWei(20, "ether");

        // Will　get five trustees
        await this.TradeIns.confirmTradeOrder(orderID, seller, {value: fee});

        const actualTrustees = await this.TradeIns.getOrderTrustees(orderID);
        assert.equal(actualTrustees.length, 5, "should auto get 5 trustees");

        const actualSeller = await this.TradeIns.getOrderSeller(orderID);
        assert.equal(actualSeller, seller, "seller should be right added");
    })


    it("creator can upload encrypted shard", async function () {
        const buyer = 12345;
        const seller = 43213;
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

        await this.TradeIns.uploadSecret(orderID, sellerShard, seller);

        // reject calls from others except buyer/seller
        await assertRevert(this.TradeIns.uploadSecret(orderID, sellerShard, seller, {from: others}));

        //
        let shard_1 = await this.TradeIns.getSecret(orderID, host_1, seller);
        let shard_2 = await this.TradeIns.getSecret(orderID, host_2, seller);
        let shard_3 = await this.TradeIns.getSecret(orderID, host_3, seller);
        let shard_4 = await this.TradeIns.getSecret(orderID, host_4, seller);
        let shard_5 = await this.TradeIns.getSecret(orderID, host_5, seller);

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
        await this.TradeIns.uploadSecret(orderID, buyerShard, buyer);

        let buyer_shard_1 = await this.TradeIns.getSecret(orderID, host_1, buyer);
        let buyer_shard_2 = await this.TradeIns.getSecret(orderID, host_2, buyer);
        let buyer_shard_3 = await this.TradeIns.getSecret(orderID, host_3, buyer);
        let buyer_shard_4 = await this.TradeIns.getSecret(orderID, host_4, buyer);
        let buyer_shard_5 = await this.TradeIns.getSecret(orderID, host_5, buyer);

        assert.equal(buyer_shard_1, buyerEncryptedShard_1, "buyer shard_1 should be the same");
        assert.equal(buyer_shard_2, buyerEncryptedShard_2, "buyer shard_2 should be the same");
        assert.equal(buyer_shard_3, buyerEncryptedShard_3, "buyer shard_3 should be the same");
        assert.equal(buyer_shard_4, buyerEncryptedShard_4, "buyer shard_4 should be the same");
        assert.equal(buyer_shard_5, buyerEncryptedShard_5, "buyer shard_5 should be the same");
    })

    it("upload secret will be revert when secret's length is not same with trustee's length", async function () {
        const seller = 43213;
        const orderID = 1;

        // Seller upload buyer's shard
        const buyerEncryptedShard_1 = "sxecfdr4444";
        const buyerEncryptedShard_2 = "sxsdfcf24444";
        const buyerEncryptedShard_3 = "sxe123r4444";
        const buyerEncryptedShard_4 = "sxe3h4r5r4444";
        const buyerShard = buyerEncryptedShard_1 + "," + buyerEncryptedShard_2 + "," + buyerEncryptedShard_3 + "," + buyerEncryptedShard_4;
        await assertRevert(this.TradeIns.uploadSecret(orderID, buyerShard, seller));
    })

    it("upload secret will be revert when userID is not buyer/seller", async function () {
        const orderID = 1;
        const other = 23456;

        // Seller upload buyer's shard
        const buyerEncryptedShard_1 = "sxecfdr4444";
        const buyerEncryptedShard_2 = "sxsdfcf24444";
        const buyerEncryptedShard_3 = "sxe123r4444";
        const buyerEncryptedShard_4 = "sxe3h4r5r4444";
        const buyerEncryptedShard_5 = "fsdf345dfg";
        const buyerShard = buyerEncryptedShard_1 + "," + buyerEncryptedShard_2 + "," + buyerEncryptedShard_3 + "," + buyerEncryptedShard_4 + "," + buyerEncryptedShard_5;
        await assertRevert(this.TradeIns.uploadSecret(orderID, buyerShard, other));
    })

    it("owner can request for arbitration for user", async function () {
        const buyer = 12345;
        const orderID = 1;

        await this.TradeIns.arbitrate(orderID, buyer);

        let requester = await this.TradeIns.getRequester(orderID);
        assert.equal(requester, buyer, "buyer can request for arbitration");
    })

    it("only judge can judge the order", async function () {
        const orderID = 1;
        const judge = accounts[9];
        await this.TradeIns.addArbitrator(judge);

        const isJudge = await this.TradeIns.isArbitrator(judge);
        assert.equal(isJudge, true, "should right add judge");

        const winner = 12345;
        await this.TradeIns.judge(orderID, winner, {from: judge});

        const acutalWinner = await this.TradeIns.getWinner(orderID);
        assert.equal(acutalWinner, winner, "shoud right judge who win");
    })
});
