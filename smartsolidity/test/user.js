import assertRevert from "zeppelin-solidity/test/helpers/assertRevert"
import {assertBigger, assertEquals, assertSmaller} from "./help";

const FeeManager = artifacts.require("./FeeManager");
const Trade = artifacts.require("Trade");
const Hoster = artifacts.require("Hoster");

const NORMAL_USER = 0;

const BigNumber = web3.BigNumber;
const should = require('chai')
    .use(require('chai-as-promised'))
    .use(require('chai-bignumber')(BigNumber))
    .should();

contract("Hoster test", function(accounts){
    before(async function () {
        this.feeRate = web3.toWei(1, "ether");
        this.FeeManagerIns = await FeeManager.new();
        this.HosterIns = await Hoster.new(this.FeeManagerIns.address);

        this.TradeIns = await Trade.new();

        // Set Hoster/trade contract address
        await this.FeeManagerIns.updateHosterContract(this.HosterIns.address);
        await this.FeeManagerIns.updateTradeContract(this.TradeIns.address);
    });


    describe("Normal function test", function () {
        it("should right add/update/remove normal user", async function () {
            // Add user
            const newUser = accounts[1];
            const fame = 0;
            const publicKey = "adfsfs";
            await this.HosterIns.addUser(newUser, fame, publicKey, NORMAL_USER);
            let isThemisUser = await this.HosterIns.isThemisUser(newUser);
            assert.equal(isThemisUser, true, "should be right added to themis user");

            // Update User
            const newFame = 1;
            const newPublicKey = "asdfzz";
            await this.HosterIns.updateUser(newUser, newFame, newPublicKey, NORMAL_USER);

            let acutalUser = await this.HosterIns.users.call(newUser);
            let actualPublicKey = acutalUser[2];
            assert.equal(actualPublicKey, newPublicKey, "should right update public key");
            let actualFame = acutalUser[1];
            assert.equal(actualFame, newFame, "should right update fame");

            // Remove user
            await this.HosterIns.removeUser(newUser);
            isThemisUser = await this.HosterIns.isThemisUser(newUser);
            assert.equal(isThemisUser, false, "should right remove user");
        });
    });


    describe("Hoster function test", function () {
        it("should right add hoster", async function () {
            const hoster = accounts[1];
            const fame = 5;

            const deposit = web3.toWei(50, "ether");
            const publicKey = "adfsfs";

            const admin = accounts[0];
            let adminBalanceBefore = await web3.eth.getBalance(admin);

            await this.HosterIns.addHoster(hoster, fame, publicKey, {value: deposit});
            // Will add it to themis user auto
            let isThemisUser = await this.HosterIns.isThemisUser(hoster);
            assert.equal(isThemisUser, true, "should add hoster to themis user auto when hoster is not themis user");

            let adminBalanceAfter = await web3.eth.getBalance(admin);
            assertBigger(adminBalanceBefore.sub(adminBalanceAfter), deposit, "admin should pay deposit(user paid to admin before) and transaction fee");
        })


        it("should right update normal user to hoster", async function() {

            const user = accounts[2];
            const newFame = 5;
            const newDeposit = web3.toWei(50, "ether");
            const newPublicKey = "aaaaaa";
            await this.HosterIns.addUser(user, newFame, newPublicKey, 0);
            let isHoster = await this.HosterIns.isHoster(user);
            assert.equal(isHoster, false, "normal user is not hoster");

            const admin = accounts[0];
            let adminBalanceBefore = await web3.eth.getBalance(admin);

            await this.HosterIns.updateNormalUserToHoster(user, {value: newDeposit});
            isHoster = await this.HosterIns.isHoster(user);
            assert.equal(isHoster, true, "should right update normal user to hoster");

            let adminBalanceAfter = await web3.eth.getBalance(admin);
            assertBigger(adminBalanceBefore.sub(adminBalanceAfter), newDeposit, "admin should pay deposit and transaction fee when update user to hoster");
        })


        it("should right remove hoster and get back deposit", async function() {
            const user = accounts[2];
            let isHoster = await this.HosterIns.isHoster(user);
            assert.equal(isHoster, true, "user 2 is a hoster before");

            let balance_before = await web3.eth.getBalance(user);

            await this.HosterIns.removeHoster(user);
            isHoster = await this.HosterIns.isHoster(user);
            assert.equal(isHoster, false, "user 2 should be right remove from hoster");

            let isThemisUser = await this.HosterIns.isThemisUser(user);
            assert.equal(isThemisUser, true, "user 2 should retain themis user");

            // Should get back his/her deposit
            let balance_after = await web3.eth.getBalance(user);
            let depositBacked = balance_after.sub(balance_before);

            // pay 50 get tokens as deposit
            depositBacked.should.be.bignumber.equal(web3.toWei(50, "ether"));
        })
    })

    
    describe("Get hoster service Test", function () {

        it("should right get hoster sort by fame and depoist", async function() {
            const should_be_3 = accounts[1];

            const should_be_2 = accounts[3];
            const user3Fame = 7;
            const user3Deposit = web3.toWei(10, "ether");
            const user3PublicKey = "asdf123";

            await this.HosterIns.addHoster(should_be_2, user3Fame, user3PublicKey, {value: user3Deposit});

            const should_be_4 = accounts[4];
            const user4Fame = 4;
            const user4Deposit = web3.toWei(20, "ether");
            const user4PublicKey = "asdasdff123";

            await this.HosterIns.addHoster(should_be_4, user4Fame, user4PublicKey, {value: user4Deposit});

            const should_be_1 = accounts[5];
            const user5Fame = 7;
            const user5Deposit = web3.toWei(20, "ether");
            const user5PublicKey = "asdasdff123";

            await this.HosterIns.addHoster(should_be_1, user5Fame, user5PublicKey, {value: user5Deposit});

            // Check node list is sort by fame, deposit or not

            // Normal user get hoster'id
            const normalUser = accounts[7];
            const normalFame = 1;
            const publicKey = "fsafwe";
            await this.HosterIns.addUser(normalUser, normalFame, publicKey, NORMAL_USER);

            let { logs } = await this.HosterIns.getHosters(4, {from: normalUser});
            const log = logs.find(e => e.event === "GetThemisHosters");
            should.exist(log);
            let acutal_1 = log.args.hosters[0];
            let acutal_2 = log.args.hosters[1];
            let acutal_3 = log.args.hosters[2];
            let acutal_4 = log.args.hosters[3];

            // Should sort by fame and deposit
            acutal_1.should.equal(should_be_1);
            acutal_2.should.equal(should_be_2);
            acutal_3.should.equal(should_be_3);
            acutal_4.should.equal(should_be_4);

            // number of nodes/hosters want to get is bigger than nodes/hoster's length
            // just return all nodes
            let logs_all = await this.HosterIns.getHosters(5, {from: normalUser});
            const log_all = logs_all.logs.find(e => e.event === "GetThemisHosters");
            should.exist(log_all);
            log_all.args.hosters.length.should.equal(4);
        })
        
        
        it("should right update one's fame, list position will be changed when he/she is a hoster", async function () {

            // Should be the first one returned
            // Ori fame and deposit
            // accounts[5]: {fame:7, deposit:20GET}
            // accounts[1]: {fame:5, deposit:50GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            const should_be_1 = accounts[5];
            const newFame = 5;
            await this.HosterIns.updateHosterFame(should_be_1, newFame);
            let actualUser = await this.HosterIns.users.call(should_be_1);
            let actualFame = actualUser[1];
            actualFame.should.be.bignumber.equal(newFame);

            const new_should_be_3 = should_be_1;
            const new_should_be_2 = accounts[1];
            const new_should_be_1 = accounts[3];
            const new_should_be_4 = accounts[4];

            const normalUser = accounts[7];
            let { logs } = await this.HosterIns.getHosters(4, {from: normalUser});
            const log = logs.find(e => e.event === "GetThemisHosters");
            should.exist(log);
            // After update: fame and deposit
            // accounts[5]: {fame:5, deposit:20GET}
            // accounts[1]: {fame:5, deposit:50GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            let acutal_1 = log.args.hosters[0];
            let acutal_2 = log.args.hosters[1];
            let acutal_3 = log.args.hosters[2];
            let acutal_4 = log.args.hosters[3];

            // Should sort by fame and deposit
            acutal_1.should.equal(new_should_be_1);
            acutal_2.should.equal(new_should_be_2);
            acutal_3.should.equal(new_should_be_3);
            acutal_4.should.equal(new_should_be_4);
        });
        
        it("should right increase hoster' deposit, hoster's position will be changed", async function () {

            const should_be_1 = accounts[5];
            // before
            // accounts[5]: {fame:5, deposit:20GET}
            // accounts[1]: {fame:5, deposit:50GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            const balanceBefore = await web3.eth.getBalance(this.FeeManagerIns.address);
            const userBalanceBefore = await web3.eth.getBalance(should_be_1);
            const increaseAmount = web3.toWei(40, "ether");

            // Only user self can call this function
            await this.HosterIns.increaseDeposit({from: should_be_1, value: increaseAmount});

            const balanceAfter = await web3.eth.getBalance(this.FeeManagerIns.address);
            const userBalanceAfter = await web3.eth.getBalance(should_be_1);
            assertEquals(balanceAfter.sub(balanceBefore), increaseAmount, "FeeManage contract should get GET coin of deposit");
            assertBigger(userBalanceBefore.sub(userBalanceAfter), increaseAmount, "User should pay deposit and transaction fee");
            // After update: fame and deposit
            // accounts[5]: {fame:5, deposit:60GET}
            // accounts[1]: {fame:5, deposit:50GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}

            const normalUser = accounts[7];
            let tx = await this.HosterIns.getHosters(4, {from: normalUser});
            const new_log = tx.logs.find(e => e.event === "GetThemisHosters");
            should.exist(new_log);
            let acutal_1 = new_log.args.hosters[0];
            let acutal_2 = new_log.args.hosters[1];
            let acutal_3 = new_log.args.hosters[2];
            let acutal_4 = new_log.args.hosters[3];

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
        
        it("should right decrease hoster's deposit, hoster's position will be changed", async function () {
            // Before
            // accounts[5]: {fame:5, deposit:60GET}
            // accounts[1]: {fame:5, deposit:50GET}
            // accounts[3]: {fame:7, deposit:10GET}
            // accounts[4]: {fame:4, deposit:20GET}
            const one = accounts[5];
            const decreaseAmount = web3.toWei(13, "ether");

            let balanceBefore = await web3.eth.getBalance(this.FeeManagerIns.address);
            let userBalanceBefore = await web3.eth.getBalance(one);

            await this.HosterIns.decreaseDeposit(decreaseAmount, {from: one});

            let balanceAfter = await web3.eth.getBalance(this.FeeManagerIns.address);
            let userBalanceAfter = await web3.eth.getBalance(one);
            // Hoster should get back amount of get he decreasing
            assertEquals(balanceBefore.sub(balanceAfter), decreaseAmount, "FeeManage contract should send back get coin");
            assertSmaller(userBalanceAfter.sub(userBalanceBefore), decreaseAmount, "user should get back deposit and pay transaction fee when decrease deposit");

            const normalUser = accounts[7];
            let tx = await this.HosterIns.getHosters(4, {from: normalUser});
            const new_log = tx.logs.find(e => e.event === "GetThemisHosters");
            should.exist(new_log);
            let acutal_1 = new_log.args.hosters[0];
            let acutal_2 = new_log.args.hosters[1];
            let acutal_3 = new_log.args.hosters[2];
            let acutal_4 = new_log.args.hosters[3];

            // After
            // accounts[5]: {fame:5, deposit:47GET}
            // accounts[1]: {fame:5, deposit:50GET}
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