const Trade = artifacts.require("./Trade.sol");
const GET = artifacts.require("./GEToken.sol");
const FeeManager = artifacts.require("./FeeManager.sol");
const Vss = artifacts.require("./Vss.sol");

module.exports = (deployer) => {
    var vss, feeManger;
    deployer.deploy(GET).then(function() {
        const feeRate = 1;
        return deployer.deploy(FeeManager, GET.address, feeRate);
    }).then(function(){
        feeManger = FeeManager.address;
        return deployer.deploy(Vss);
    }).then(function(){
        vss = Vss.address;
        deployer.deploy(Trade, feeManger, vss);
    });
};