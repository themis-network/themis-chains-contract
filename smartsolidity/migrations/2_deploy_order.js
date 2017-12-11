const Order = artifacts.require("./Order.sol");

module.exports = (deployer) => {
    deployer.deploy(Order);
};