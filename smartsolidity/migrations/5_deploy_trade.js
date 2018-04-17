const Trade = artifacts.require("./Trade.sol");

module.exports = (deployer) => {
    deployer.deploy(Trade);
};