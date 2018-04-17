const Hoster = artifacts.require("./Hoster.sol");

module.exports = (deployer) => {
    deployer.deploy(Hoster);
};