import assertRevert from "zeppelin-solidity/test/helpers/assertRevert"
import {assertBigger, assertEquals, assertSmaller} from "./help";

const Trade = artifacts.require("Trade");
const Trustee = artifacts.require("Trustee");

const NORMAL_USER = 0;
const BigNumber = web3.BigNumber;
const should = require('chai')
    .use(require('chai-as-promised'))
    .use(require('chai-bignumber')(BigNumber))
    .should();

contract("Trustee test", function(accounts){
    before(async function () {
        this.TrusteeIns = await Trustee.new();
    });


    describe("Add/Update/Remove trustee", function () {
        it("should get nothing when there is no trustees", async function () {
            //
        })

        it("owner should right add trustee", async function () {
            const trustee = accounts[1];
            const fame = 5;
            const publicKey = "adfsfs";

            await this.TrusteeIns.addTrustee(trustee, fame, publicKey);

            let isTrustee = await this.TrusteeIns.isTrustee(trustee);
            assert.equal(isTrustee, true, "trustee should be right added");

            let trusteeInfo = await this.TrusteeIns.getTrusteeInfo(trustee);
            assertEquals(trusteeInfo[0], new BigNumber(fame), "right add fame");
            assertEquals(trusteeInfo[1], new BigNumber(0), "deposit right init");
            assert.equal(trusteeInfo[2], publicKey, "public key right added");
        })


        it("trustee should right increase deposit", async function () {
            const trustee = accounts[1];
            const amount = web3.toWei(30, "ether");

            const { logs } = await this.TrusteeIns.increaseDeposit({from: trustee, value: amount});
            const log = logs.find(e => e.event === "LogIncreaseDeposit");
            should.exist(log);
            log.args.amount.should.be.bignumber.equal(amount);
        })


        it("should right remove trustee and get back deposit", async function() {
            const trustee = accounts[1];
            let balanceBefore = await web3.eth.getBalance(trustee);

            await this.TrusteeIns.removeTrustee(trustee);
            let isTrustee = await this.TrusteeIns.isTrustee(trustee);
            assert.equal(isTrustee, false, "trustee should be removed");

            // Should get back his/her deposit
            let balanceAfter = await web3.eth.getBalance(trustee);
            let depositBacked = balanceAfter.sub(balanceBefore);

            // pay 50 get tokens as deposit
            assertEquals(depositBacked, new BigNumber(web3.toWei(30, "ether")), "should get deposit back");
        })
    })

    
    describe("Trustee list", function () {

        it("should get trustees sort by fame and deposit", async function() {
            const should_be_3 = accounts[1];
            const user1Fame = 5;
            const user1Deposit = web3.toWei(30, "ether");
            const user1PublicKey = "4fg754dfg";
            await this.TrusteeIns.addTrustee(should_be_3, user1Fame, user1PublicKey);
            await this.TrusteeIns.increaseDeposit({from: should_be_3, value: user1Deposit});

            const should_be_2 = accounts[3];
            const user3Fame = 7;
            const user3Deposit = web3.toWei(10, "ether");
            const user3PublicKey = "asdf123";

            await this.TrusteeIns.addTrustee(should_be_2, user3Fame, user3PublicKey);
            await this.TrusteeIns.increaseDeposit({from: should_be_2, value: user3Deposit});

            const should_be_4 = accounts[4];
            const user4Fame = 4;
            const user4Deposit = web3.toWei(20, "ether");
            const user4PublicKey = "asdasdff123";

            await this.TrusteeIns.addTrustee(should_be_4, user4Fame, user4PublicKey);
            await this.TrusteeIns.increaseDeposit({from: should_be_4, value: user4Deposit});

            const should_be_1 = accounts[5];
            const user5Fame = 7;
            const user5Deposit = web3.toWei(20, "ether");
            const user5PublicKey = "asdasdff123";

            await this.TrusteeIns.addTrustee(should_be_1, user5Fame, user5PublicKey);
            await this.TrusteeIns.increaseDeposit({from: should_be_1, value: user5Deposit});

            // Check node list is sort by fame, deposit or not
            // Normal user
            const normalUser = accounts[7];

            let { logs } = await this.TrusteeIns.getTrustees(4, {from: normalUser});
            const log = logs.find(e => e.event === "LogGetTrustees");
            should.exist(log);
            let acutal_1 = log.args.trustees[0];
            let acutal_2 = log.args.trustees[1];
            let acutal_3 = log.args.trustees[2];
            let acutal_4 = log.args.trustees[3];

            // Should sort by fame and deposit
            acutal_1.should.equal(should_be_1);
            acutal_2.should.equal(should_be_2);
            acutal_3.should.equal(should_be_3);
            acutal_4.should.equal(should_be_4);

            // number of nodes/hosters want to get is bigger than nodes/hoster's length
            // just return all nodes
            let logs_all = await this.TrusteeIns.getTrustees(5, {from: normalUser});
            const log_all = logs_all.logs.find(e => e.event === "LogGetTrustees");
            should.exist(log_all);
            log_all.args.trustees.length.should.equal(4);
        })
        
        it("should right update one's fame and his position in list will be changed", async function () {

            // Should be the first one returned
            // Ori fame and deposit
            // accounts[5]: {fame:7, deposit:20GET}
            // accounts[1]: {fame:5, deposit:30GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            const should_be_1 = accounts[5];
            const newFame = 5;
            await this.TrusteeIns.updateTrusteeFame(should_be_1, newFame);
            let trsuteeInfo = await this.TrusteeIns.getTrusteeInfo(should_be_1);
            trsuteeInfo[0].should.be.bignumber.equal(newFame);

            const new_should_be_3 = should_be_1;
            const new_should_be_2 = accounts[1];
            const new_should_be_1 = accounts[3];
            const new_should_be_4 = accounts[4];

            const normalUser = accounts[7];
            let { logs } = await this.TrusteeIns.getTrustees(4, {from: normalUser});
            const log = logs.find(e => e.event === "LogGetTrustees");
            should.exist(log);
            // After update: fame and deposit
            // accounts[5]: {fame:5, deposit:20GET}
            // accounts[1]: {fame:5, deposit:30GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            let acutal_1 = log.args.trustees[0];
            let acutal_2 = log.args.trustees[1];
            let acutal_3 = log.args.trustees[2];
            let acutal_4 = log.args.trustees[3];

            // Should sort by fame and deposit
            acutal_1.should.equal(new_should_be_1);
            acutal_2.should.equal(new_should_be_2);
            acutal_3.should.equal(new_should_be_3);
            acutal_4.should.equal(new_should_be_4);
        });
        
        it("should right increase trustee' deposit and his position will be changed", async function () {

            const should_be_1 = accounts[5];
            // before
            // accounts[5]: {fame:5, deposit:20GET}
            // accounts[1]: {fame:5, deposit:30GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            const increaseAmount = web3.toWei(40, "ether");

            // Only user self can call this function
            await this.TrusteeIns.increaseDeposit({from: should_be_1, value: increaseAmount});

            // After update: fame and deposit
            // accounts[5]: {fame:5, deposit:60GET}
            // accounts[1]: {fame:5, deposit:30GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}

            const normalUser = accounts[7];
            let tx = await this.TrusteeIns.getTrustees(4, {from: normalUser});
            const new_log = tx.logs.find(e => e.event === "LogGetTrustees");
            should.exist(new_log);
            let acutal_1 = new_log.args.trustees[0];
            let acutal_2 = new_log.args.trustees[1];
            let acutal_3 = new_log.args.trustees[2];
            let acutal_4 = new_log.args.trustees[3];

            let new_2_should_be_1 = accounts[3];
            let new_2_should_be_2 = accounts[5];
            let new_2_should_be_3 = accounts[1];
            let new_2_should_be_4 = accounts[4];

            // Should sort by fame and deposit
            acutal_1.should.equal(new_2_should_be_1);
            acutal_2.should.equal(new_2_should_be_2);
            acutal_3.should.equal(new_2_should_be_3);
            acutal_4.should.equal(new_2_should_be_4);
        })
        
        it("should right decrease trustee's deposit and his position will be changed", async function () {
            // Before
            // accounts[5]: {fame:5, deposit:60GET}
            // accounts[1]: {fame:5, deposit:30GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            const one = accounts[5];
            const decreaseAmount = web3.toWei(33, "ether");

            let userBalanceBefore = await web3.eth.getBalance(one);

            await this.TrusteeIns.decreaseDeposit(decreaseAmount, {from: one});

            let userBalanceAfter = await web3.eth.getBalance(one);
            // trustee should get back amount of get he decreasing
            assertSmaller(userBalanceAfter.sub(userBalanceBefore), decreaseAmount, "trustee should get back deposit and pay transaction fee");

            const normalUser = accounts[7];
            let tx = await this.TrusteeIns.getTrustees(4, {from: normalUser});
            const new_log = tx.logs.find(e => e.event === "LogGetTrustees");
            should.exist(new_log);
            let acutal_1 = new_log.args.trustees[0];
            let acutal_2 = new_log.args.trustees[1];
            let acutal_3 = new_log.args.trustees[2];
            let acutal_4 = new_log.args.trustees[3];

            // After
            // accounts[5]: {fame:5, deposit:27GET}
            // accounts[1]: {fame:5, deposit:30GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            let new_2_should_be_1 = accounts[3];
            let new_2_should_be_2 = accounts[1];
            let new_2_should_be_3 = accounts[5];
            let new_2_should_be_4 = accounts[4];

            // Should sort by fame and deposit
            acutal_1.should.equal(new_2_should_be_1);
            acutal_2.should.equal(new_2_should_be_2);
            acutal_3.should.equal(new_2_should_be_3);
            acutal_4.should.equal(new_2_should_be_4);
        })
    })
});