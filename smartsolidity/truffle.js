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
      gasPrice: 0,
    },
    testNet: {
      network_id: "1111",
      port: 8555,
      host: "192.168.1.213",
      // set this can change gas used
      gas: 4712388,
      gasPrice: 0x00,
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
