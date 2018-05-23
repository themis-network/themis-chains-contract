const Hoster = artifacts.require("./Hoster.sol");
const FeeManager = artifacts.require("./FeeManager.sol");


module.exports = (deployer) => {
    deployer.deploy(FeeManager).then(function () {
        return deployer.deploy(Hoster, FeeManager.address);
    });
};