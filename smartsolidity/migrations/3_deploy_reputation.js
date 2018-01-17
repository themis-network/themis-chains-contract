const Reputation = artifacts.require("./Reputation.sol");

module.exports = (deployer) => {
    deployer.deploy(Reputation);
};