const Hoster = artifacts.require("./Hoster.sol");
const GET = artifacts.require("./GEToken.sol");
const FeeManager = artifacts.require("./FeeManager.sol");


module.exports = (deployer) => {
    deployer.deploy(GET).then(function() {
        const feeRate = web3.toWei(1, "ether");
        return deployer.deploy(FeeManager, GET.address, feeRate);
    }).then(function () {
        return deployer.deploy(Hoster, FeeManager.address);
    });
};