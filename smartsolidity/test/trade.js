import assertRevert from "zeppelin-solidity/test/helpers/assertRevert";

const GET = artifacts.require("./GEToken");
const FeeManager = artifacts.require("./FeeManager");
const Trade = artifacts.require("Trade");
const Vss = artifacts.require("Vss");
const Hoster = artifacts.require("Hoster");

const NORMAL_USER = 0;

contract("Trade test", function (accounts) {

    before(async function () {
        this.GETIns = await GET.new();
        this.feeRate = web3.toWei(1, "ether");
        this.FeeManagerIns = await FeeManager.new(this.GETIns.address, this.feeRate);
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
        await this.GETIns.transfer(hoster_1, web3.toWei(10, "ether"));
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(10, "ether"), {from: hoster_1});
        await this.HosterIns.addHoster(hoster_1, hoster_1Fame, hoster_1Deposit, hoster_1PublicKey);

        // Add hoster 2
        const hoster_2 = accounts[4];
        const hoster_2Fame = 6;
        const hoster_2Deposit = web3.toWei(10, "ether");
        const hoster_2PublicKey = "asdf123";
        await this.GETIns.transfer(hoster_2, web3.toWei(10, "ether"));
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(10, "ether"), {from: hoster_2});
        await this.HosterIns.addHoster(hoster_2, hoster_2Fame, hoster_2Deposit, hoster_2PublicKey);

        // Add hoster 3
        const hoster_3 = accounts[5];
        const hoster_3Fame = 5;
        const hoster_3Deposit = web3.toWei(10, "ether");
        const hoster_3PublicKey = "asdf123";
        await this.GETIns.transfer(hoster_3, web3.toWei(10, "ether"));
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(10, "ether"), {from: hoster_3});
        await this.HosterIns.addHoster(hoster_3, hoster_3Fame, hoster_3Deposit, hoster_3PublicKey);

        // Add hoster 4
        const hoster_4 = accounts[6];
        const hoster_4Fame = 4;
        const hoster_4Deposit = web3.toWei(10, "ether");
        const hoster_4PublicKey = "asdf123";
        await this.GETIns.transfer(hoster_4, web3.toWei(10, "ether"));
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(10, "ether"), {from: hoster_4});
        await this.HosterIns.addHoster(hoster_4, hoster_4Fame, hoster_4Deposit, hoster_4PublicKey);
    });


    it("should right create order and pay fee(GET) for it", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 1;

        const newUser = buyer;
        const fame = 0;
        const publicKey = "adfsfs";
        await this.HosterIns.addUser(newUser, fame, publicKey, NORMAL_USER);
        let isThemisUser = await this.HosterIns.isThemisUser(newUser);
        assert.equal(isThemisUser, true, "should be right added to themis user");

        // User should pay GET tokens for this server
        await this.GETIns.transfer(buyer, web3.toWei(50, "ether"));
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(50, "ether"), {from: buyer})

        await this.TradeIns.createNewTradeOrder(orderID, seller, 3, {from: buyer});

        let actualBuyer = await this.TradeIns.getBuyer(orderID);
        assert.equal(actualBuyer, buyer, "buyer should be right set");

        let acutalSeller = await this.TradeIns.getSeller(orderID);
        assert.equal(acutalSeller, seller, "seller should be right set");

        // TODO add balance check
    })


    // TODO do more test for create order
    it("can not create two order with same orderID", async function() {
        // buyer is added to themis user before
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 1;

        // Have approve enough get tokens for fee manager contract
        await assertRevert(this.TradeIns.createNewTradeOrder(orderID, buyer, seller));
    })


    it("only buyer/seller can upload encrypted shard(mean request artibaion service)", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const others = accounts[3];
        const orderID = 2;

        await this.TradeIns.createNewTradeOrder(orderID, seller, 3, {from: buyer});

        // Only buyer can upload seller's encrypted shard
        const sellerEncryptedShard_1 = "asbdsdfbheg34";
        const sellerEncryptedShard_2 = "asbasdfdg34";
        const sellerEncryptedShard_3 = "asbd3rtwfg34";
        const sellerShard = sellerEncryptedShard_1 + "," + sellerEncryptedShard_2 + "," + sellerEncryptedShard_3;

        // This service will cost GET Tokens
        await this.GETIns.transfer(buyer, web3.toWei(100, "ether"));
        await this.GETIns.transfer(seller, web3.toWei(100, "ether"));
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(50, "ether"), {from: buyer});
        await this.GETIns.approve(this.FeeManagerIns.address, web3.toWei(50, "ether"), {from: seller});

        // Check shard is right uploaded
        const host_1 = accounts[3];
        const host_2 = accounts[4];
        const host_3 = accounts[5];

        let host_1_balance_before = await this.GETIns.balanceOf(host_1);
        let host_2_balance_before = await this.GETIns.balanceOf(host_2);
        let host_3_balance_before = await this.GETIns.balanceOf(host_3);

        await this.TradeIns.uploadSellerShardFromBuyer(orderID, sellerShard, {from: buyer});

        // reject calls from others except buyer/seller
        await assertRevert(this.TradeIns.uploadSellerShardFromBuyer(orderID, sellerShard, {from: others}))

        // SELLER indicate this is seller's shard uploaded by buyer
        let shard_1 = await this.TradeIns.getSellerShardByHosterID(orderID, host_1);
        let shard_2 = await this.TradeIns.getSellerShardByHosterID(orderID, host_2);
        let shard_3 = await this.TradeIns.getSellerShardByHosterID(orderID, host_3);
        shard_1.should.equal(sellerEncryptedShard_1);
        shard_2.should.equal(sellerEncryptedShard_2);
        shard_3.should.equal(sellerEncryptedShard_3);

        // Check hoster get fee
        let host_1_balance = await this.GETIns.balanceOf(host_1);
        let host_2_balance = await this.GETIns.balanceOf(host_2);
        let host_3_balance = await this.GETIns.balanceOf(host_3);

        let getBalance_1 = host_1_balance - host_1_balance_before;
        let getBalance_2 = host_2_balance - host_2_balance_before;
        let getBalance_3 = host_3_balance - host_3_balance_before;
        getBalance_1.should.be.bignumber.equal(this.feeRate);
        getBalance_2.should.be.bignumber.equal(this.feeRate);
        getBalance_3.should.be.bignumber.equal(this.feeRate);
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
});
