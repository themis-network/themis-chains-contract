const Hoster = artifacts.require("./Hoster.sol");
const GET = artifacts.require("./GEToken.sol");
const FeeManager = artifacts.require("./FeeManager");


module.exports = (deployer) => {
    deployer.deploy(GET).then(function() {
        const feeRate = 1;
        deployer.deploy(FeeManager, GET.address, feeRate).then(function () {
            deployer.deploy(Hoster, FeeManager.address);
        });
    });
};