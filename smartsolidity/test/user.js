const Hoster = artifacts.require("Hoster");

contract("Hoster test", function(accounts){
    before(async function () {
        this.HosterIns = await Hoster.new();
    });
    
    it("should right add/update/remove normal user", async function () {
       // Add user
       const newUser = accounts[1];
       const fame = 0;
       const publicKey = "adfsfs";
       const userType = 0;
       await this.HosterIns.AddUser(newUser, fame, publicKey, userType);
       let isThemisUser = await this.HosterIns.IsThemisUser(newUser);
       assert.equal(isThemisUser, true, "should be right added to themis user");

       // Update User
       const newFame = 1;
       const newPublicKey = "asdfzz";
       await this.HosterIns.UpdateUser(newUser, newFame, newPublicKey, userType);

       let acutalUser = await this.HosterIns.users.call(newUser);
       let actualPublicKey = acutalUser[2];
       assert.equal(actualPublicKey, newPublicKey, "should right update public key");
       let actualFame = acutalUser[1];
       assert.equal(actualFame, newFame, "should right update fame");

       // Remove user
       await this.HosterIns.RemoveUser(newUser);
       isThemisUser = await this.HosterIns.IsThemisUser(newUser);
       assert.equal(isThemisUser, false, "should right remove user");
    })


    it("should right add hoster", async function () {
        const hoster = accounts[1];
        const fame = 5;
        const deposit = 100;
        const publicKey = "adfsfs";

        await this.HosterIns.AddHoster(hoster, fame, deposit, publicKey);
        // Will add it to themis user auto
        let isThemisUser = await this.HosterIns.IsThemisUser(hoster);
        assert.equal(isThemisUser, true, "should add hoster to themis user auto when hoster is not themis user");


        const user = accounts[2];
        const newFame = 5;
        const newDeposit = 90;
        const newPublicKey = "aaaaaa";
        await this.HosterIns.AddUser(user, newFame, newPublicKey, 0);
        await this.HosterIns.AddHoster(user, newFame, newDeposit, newPublicKey);

        let acutalUser = await this.HosterIns.users.call(user);
        let userType = acutalUser[3];
        assert.equal(userType, 1, "should update user's user type when become a hoster");

        const user3 = accounts[3];
        const user3Fame = 7;
        const user3Deposit = 1;
        const user3PublicKey = "asdf123";
        await this.HosterIns.AddHoster(user3, user3Fame, user3Deposit, user3PublicKey);

        // Check node list is sort by fame, deposit or not
        let hosterList = await this.HosterIns.GetHosters(3);
        assert.equal(user3, hosterList[0], "user3 with fame 7, deposit 1 should be the first one");
        assert.equal(hoster, hosterList[1], "hoster with fame 5, deposit 100 should be the second one");
        assert.equal(user, hosterList[2], "user with fame 5, deposit 90 should be the last one");
    })
});