const Order = artifacts.require("Order");

const orderId = "order-test1";
const buyId = "lihq";
const buyPrivKey = "asdf23rfasddfvzxv";
const sellerId = "zhangsan";
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


contract("Order test", function(accounts) {
    beforeEach(async function () {
        this.order = await Order.new();
    });
    
    it("should save data rightly", async function () {
        await this.order.RequestHostingService(orderId, buyId, buyPrivKey, sellerId, sellerPrivKey);
        await this.order.RequestHostingServiceTrustee(orderId, trusteeA_id, trusteeA_priv_buy, trusteeA_priv_sell);
        await this.order.RequestHostingServiceTrustee(orderId, trusteeB_id, trusteeB_priv_buy, trusteeB_priv_sell);
        await this.order.RequestHostingServiceTrustee(orderId, trusteeC_id, trusteeC_priv_buy, trusteeC_priv_sell);

        const acutalOrder = await this.order.orders.call(orderId);
        assert.equal(acutalOrder[0], buyId);
        assert.equal(acutalOrder[2], sellerId);

        const actualBuyerPriv = await this.order.GetEncryBuyerPrivKey.call(orderId);
        assert.equal(actualBuyerPriv, buyPrivKey);

        const actualSellerPriv = await this.order.GetEncrySellerPrivKey.call(orderId);
        assert.equal(actualSellerPriv, sellerPrivKey);

        const actualTrusteeAPriv = await this.order.GetTrusteeStoreBuyerOrSellerEncryPrivKey.call(orderId, trusteeA_id, 1);
        assert.equal(actualTrusteeAPriv, trusteeA_priv_buy);
    });

    it("only owner can call RequestHostingService", async function () {
        try {
            await this.order.RequestHostingService(orderId, buyId, buyPrivKey, sellerId, sellerPrivKey, {from: accounts[1]});
        }
        catch (error) {
            return;
        }

        assert.fail("should return before");
    });

    it("only owner can call RequestHostingServiceTrustee", async function () {
        try {
            await this.order.RequestHostingServiceTrustee(orderId, buyId, buyPrivKey, sellerId, sellerPrivKey, {from: accounts[1]});
        }
        catch (error) {
            return;
        }

        assert.fail("should return before");
    });
});