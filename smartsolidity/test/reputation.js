const Reputation = artifacts.require("Reputation");
const BigNumber = web3.BigNumber;

contract("Reputation test", function(accounts) {
    beforeEach(async function () {
        this.Reputation = await Reputation.new();
    });
    
    it("shougld right add/sub reputation", async function () {
        let testId = "test";
        let pre = await this.Reputation.GetReputation(testId);

        let add = 1;
        await this.Reputation.StoreArbitrate(testId, add);

        let after = await this.Reputation.GetReputation(testId);
        let eq = new BigNumber(pre + add).equals(after);
        assert.equal(eq, true, "not right add 1");

        let sub = -1;
        await this.Reputation.StoreArbitrate(testId, sub);
        let fin = await this.Reputation.GetReputation(testId);
        let eq2 = new BigNumber(0).equals(fin);
        assert.equal(eq2, true, "not right sub 1");
    });

});