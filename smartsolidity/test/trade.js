const Trade = artifacts.require("Trade");

const BUYER = 1;
const SELLER = 2;
contract("Trade test", function (accounts) {

    before(async function () {
        this.TradeIns = await Trade.new();
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


    it("only buyer/seller can upload encrypted shard(mean request judgement)", async function () {
        const buyer = accounts[1];
        const seller = accounts[2];
        const orderID = 2;

        await this.TradeIns.CreateNewTradeOrder(orderID, buyer, seller);

        // Only buyer can upload seller's encrypted shard
        const sellerEncryptedShard_1 = "asbdsdfbheg34";
        const sellerEncryptedShard_2 = "asbasdfdg34";
        const sellerEncryptedShard_3 = "asbd3rtwfg34";
        const sellerShard = sellerEncryptedShard_1 + "," + sellerEncryptedShard_2 + "," + sellerEncryptedShard_3;
        // BUYER indicate this msg is send from buyer
        await this.TradeIns.UploadBuyerOrSellerShard(orderID, BUYER, sellerShard, accounts.slice(3, 6), {from: buyer});

        // Only seller can upload buyer's encrypted shard
        await this.TradeIns.UploadBuyerOrSellerShard(orderID, SELLER, sellerShard, accounts.slice(3, 6), {from: seller});

    })
});
