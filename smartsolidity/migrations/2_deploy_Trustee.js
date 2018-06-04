const Trustee = artifacts.require("./Trustee.sol");


module.exports = (deployer) => {
    deployer.deploy(Trustee);
};