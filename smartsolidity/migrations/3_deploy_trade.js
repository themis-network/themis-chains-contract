const Trade = artifacts.require("./Trade.sol");
const FeeManager = artifacts.require("./FeeManager.sol");

module.exports = (deployer) => {
    deployer.deploy(Trade);
};