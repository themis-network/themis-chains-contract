const Trade = artifacts.require("./Trade.sol");
const FeeManager = artifacts.require("./FeeManager.sol");
const Vss = artifacts.require("./Vss.sol");

module.exports = (deployer) => {
    var vss;

    deployer.deploy(Vss).then(function(){
        vss = Vss.address;
        return deployer.deploy(Trade, FeeManager.address, vss);
    });
};