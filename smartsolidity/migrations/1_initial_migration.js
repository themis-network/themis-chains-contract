const Migrations = artifacts.require(`./Migrations.sol`)
const TruffleConfig = require('../truffle');

module.exports = (deployer, network, addresses) => {
  if (network == "testNet") {
      const config = TruffleConfig.networks[network];
      if (process.env.ACCOUNT_PASSWORD) {
          console.log('>> Unlocking account ' + config.from);

          // use command like "ACCOUNT_PASSWORD=123 truffle deploy --network testNet"
          web3.personal.unlockAccount(config.from, process.env.ACCOUNT_PASSWORD, 36000);
      }
  }
  deployer.deploy(Migrations)
};
