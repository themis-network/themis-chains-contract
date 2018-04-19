require('babel-register')({
    // Important to have zeppelin-solidity working on
    ignore: /node_modules\/(?!zeppelin-solidity)/
});
require('babel-polyfill');

module.exports = {
  networks: {
    development: {
      network_id: "*",
      port: 8545,
      host: "localhost",
      gasLimit: 4500000,
    },
    testNet: {
      network_id: "20",
      port: 8545,
      host: "192.168.1.205",
      // set this can change gas used
      gas: 4000000,
      // can use truffle console --network testNet
      // web3.eth.getBlock("latest").gasLimit to set gasLimit
      gasLimit: 4812388,
    },
    mainNet: {
      network_id: "*",
      port: 8545,
      host: "192.168.1.102",
      gasLimit: 5000000,
    },
  }
}
