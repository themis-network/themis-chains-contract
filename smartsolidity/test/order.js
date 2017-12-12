const Order = artifacts.require("Order");

const orderId = "order-test1";
const buyId = "lihq";
const buyPublicKey = "asdfwesgdbfg";
const buyPrivKey = "asdf23rfasddfvzxv";
const sellerId = "zhangsan";
const sellerPublicKey = "fasdfew4twesbfgb";
const sellerPrivKey = "adferbavzwer13rfgd";
const trusteeA_id = "lisi";
const trusteeA_priv_buy = "asdfq35135dgwert";
const trusteeA_priv_sell = "asdfq34trtherthfdgfh";
const trusteeB_id = "wangwu";
const trusteeB_priv_buy = "asdf34htrhbfdgbh";
const trusteeB_priv_sell = "4h3twer354whrtertwret";
const trusteeC_id = "zhouliu";
const trusteeC_priv_buy = "345yrth354dbhwergtwerg";
const trusteeC_priv_sell = "adf34wertewr";
const virTrusteePublicKey = "234ger24g42g5wtg5hrs";
const virTrusteePrivKey = "fsjdn883i4jtkeder";
const K = 2;
const N = 3;


contract("Order test", function(accounts) {
    beforeEach(async function () {
        this.order = await Order.new();
    });
    
    it("should save data rightly", async function () {
        await this.order.RequestHostingService(orderId, buyId, buyPublicKey, buyPrivKey, virTrusteePublicKey, virTrusteePrivKey, sellerId, K, N);
        await this.order.UploadShardKeyToTrustee(orderId, trusteeA_id, trusteeA_priv_buy, 1);
        await this.order.UploadShardKeyToTrustee(orderId, trusteeB_id, trusteeB_priv_buy, 1);
        await this.order.UploadShardKeyToTrustee(orderId, trusteeC_id, trusteeC_priv_buy, 1);

        await this.order.UploadSellerKey(orderId, sellerPublicKey, sellerPrivKey);
        await this.order.UploadShardKeyToTrustee(orderId, trusteeA_id, trusteeA_priv_sell, 2);
        await this.order.UploadShardKeyToTrustee(orderId, trusteeB_id, trusteeB_priv_sell, 2);
        await this.order.UploadShardKeyToTrustee(orderId, trusteeC_id, trusteeC_priv_sell, 2);

        const actualBuyerPriv = await this.order.GetEncryBuyerPrivKey.call(orderId);
        assert.equal(actualBuyerPriv, buyPrivKey);

        const actualSellerPriv = await this.order.GetEncrySellerPrivKey.call(orderId);
        assert.equal(actualSellerPriv, sellerPrivKey);

        const actualVirTrusteePublicKey = await this.order.GetVirTrusteePublicKey.call(orderId);
        assert.equal(actualVirTrusteePublicKey, virTrusteePublicKey);

        const actualTrusteeAPrivBuyer = await this.order.GetTrusteeStoreBuyerOrSellerEncryPrivKey.call(orderId, trusteeA_id, 1);
        assert.equal(actualTrusteeAPrivBuyer, trusteeA_priv_buy);
        const actualTrusteeAPrivSeller = await this.order.GetTrusteeStoreBuyerOrSellerEncryPrivKey.call(orderId, trusteeA_id, 2);
        assert.equal(actualTrusteeAPrivSeller, trusteeA_priv_sell);

        await this.order.JudgeUserWinByTrustee(orderId, trusteeA_id, 1);
        await this.order.JudgeUserWinByTrustee(orderId, trusteeB_id, 1);
        await this.order.JudgeUserWinByTrustee(orderId, trusteeC_id, 2);

        const winner = await this.order.JudgeWhoWin.call(orderId);
        assert(winner, 1);

        const keys = await this.order.GetWinerShardKey.call(orderId, 1);
        assert(keys, trusteeA_priv_buy + "," + trusteeB_priv_buy);
    });

    it("only owner can call RequestHostingService", async function () {
        try {
            await this.order.RequestHostingService(orderId, buyId, buyPublicKey, buyPrivKey, virTrusteePublicKey, virTrusteePrivKey, sellerId, K, N, {from: accounts[1]});
        }
        catch (error) {
            return;
        }

        assert.fail("should return before");
    });

    it("only owner can call UploadShardKeyToTrustee", async function () {
        try {
            await this.order.UploadShardKeyToTrustee(orderId, trusteeA_id, trusteeA_priv_buy, 1, {from: accounts[1]});
        }
        catch (error) {
            return;
        }

        assert.fail("should return before");
    });

    it("only owner can call JudgeUserWinByTrustee", async function () {
        try {
            await this.order.JudgeUserWinByTrustee(orderId, trusteeA_id, 1, {from: accounts[1]});
        }
        catch (error) {
            return;
        }

        assert.fail("should return before");
    });
});