const fs = require('fs')

// First read in the secrets.json to get our mnemonic
let secrets
let mnemonic
if (fs.existsSync('secrets.json')) {
  secrets = JSON.parse(fs.readFileSync('secrets.json', 'utf8'))
  mnemonic = secrets.mnemonic
} else {
  console.log('No secrets.json found. If you are trying to publish EPM ' +
              'this will fail. Otherwise, you can ignore this message!')
  mnemonic = ''
}

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
