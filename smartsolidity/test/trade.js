import assertRevert from "zeppelin-solidity/test/helpers/assertRevert";

const GET = artifacts.require("./GEToken");
const FeeManager = artifacts.require("./FeeManager");
const Trade = artifacts.require("Trade");
const Vss = artifacts.require("Vss");

const BUYER = 1;
const SELLER = 2;

contract("Trade test", function (accounts) {

    before(async function () {
        this.GETIns = await GET.new();
        this.feeRate = web3.toWei(1, "ether");
        this.FeeManagerIns = await FeeManager.new(this.GETIns.address, this.feeRate);
        this.VssIns = await Vss.new();
        this.TradeIns = await Trade.new(this.FeeManagerIns.address, this.VssIns.address);
    });


    it("should right create order", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 1;

        await this.TradeIns.CreateNewTradeOrder(orderID, buyer, seller);
        let actualBuyer = await this.TradeIns.GetBuyerOrSeller(orderID, BUYER);
        assert.equal(actualBuyer, buyer, "buyer should be right set");

        let acutalSeller = await this.TradeIns.GetBuyerOrSeller(orderID, SELLER);
        assert.equal(acutalSeller, seller, "seller should be right set");
    })


    it("can not create two order with same orderID", async function() {
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 1;

        await assertRevert(this.TradeIns.CreateNewTradeOrder(orderID, buyer, seller));
    })


    it("only buyer/seller can upload encrypted shard(mean request judgement service)", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const others = accounts[3];
        const orderID = 2;

        await this.TradeIns.CreateNewTradeOrder(orderID, buyer, seller);

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

        // BUYER indicate this msg is send from buyer
        await this.TradeIns.UploadBuyerOrSellerShard(orderID, BUYER, sellerShard, accounts.slice(3, 6), {from: buyer});

        // reject calls from others except buyer/seller
        await assertRevert(this.TradeIns.UploadBuyerOrSellerShard(orderID, BUYER, sellerShard, accounts.slice(3, 6), {from: others}))

        // Check shard is right uploaded
        const host_1 = accounts[3];
        const host_2 = accounts[4];
        const host_3 = accounts[5];
        // SELLER indicate this is seller's shard uploaded by buyer
        let shard_1 = await this.TradeIns.GetShardByHosterID(orderID, host_1, SELLER);
        let shard_2 = await this.TradeIns.GetShardByHosterID(orderID, host_2, SELLER);
        let shard_3 = await this.TradeIns.GetShardByHosterID(orderID, host_3, SELLER);
        shard_1.should.equal(sellerEncryptedShard_1);
        shard_2.should.equal(sellerEncryptedShard_2);
        shard_3.should.equal(sellerEncryptedShard_3);

        // Check hoster get fee
        let host_1_balance = await this.GETIns.balanceOf(host_1);
        let host_2_balance = await this.GETIns.balanceOf(host_2);
        let host_3_balance = await this.GETIns.balanceOf(host_3);
        host_1_balance.should.be.bignumber.equal(this.feeRate);
        host_2_balance.should.be.bignumber.equal(this.feeRate);
        host_3_balance.should.be.bignumber.equal(this.feeRate);

    })


    it("hoster can upload buyer/seller's decrypted shard means another wins conflict", async function () {
        // order created before
        const orderID = 2;
        const decrypted_shard_1 = "sdsdfgwertydfh";
        const decrypted_shard_2 = "asdfwexbvert";
        const decrypted_shard_3 = "456fghfgh";
        // Check shard is right uploaded
        const host_1 = accounts[3];
        const host_2 = accounts[4];
        const host_3 = accounts[5];

        // Check hoster'id is right stored
        let hosters = await this.TradeIns.GetHosters(orderID, SELLER);
        hosters[0].should.equal(host_1);
        hosters[1].should.equal(host_2);
        hosters[2].should.equal(host_3);

        // This means buyer wins
        await this.TradeIns.UploadDecryptedShard(orderID, SELLER, decrypted_shard_1, {from:host_1});
        await this.TradeIns.UploadDecryptedShard(orderID, SELLER, decrypted_shard_2, {from:host_2});
        await this.TradeIns.UploadDecryptedShard(orderID, SELLER, decrypted_shard_3, {from:host_3});

        // Check decrypted shard is right uploaded

    })
});
