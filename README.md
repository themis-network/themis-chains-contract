# themis-chains-contract
Themis contracts. Original contract is written in solidity under smartsolidity directory.
 smartjava is java code compiled with web3j tools.

### Setup/Ganache-cli Test ###

themis-chains-contract integrates with [Truffle](https://github.com/trufflesuite/truffle), [Ganache-cli](https://github.com/trufflesuite/ganache-cli),

[zeppelin-solidity](https://github.com/OpenZeppelin/zeppelin-solidity),
an Ethereum development environment, Ethereum test tools and Ethereum smart contract library.
Please install Truffle, Ganache-cli and zeppelin-solidity before start
```bash
npm install -g truffle
npm install -g ganache-cli
```

```bash
cd  themis-chains-contract/smartsolidity

npm install  // install dependencies
npm install -E zeppelin-solidity
```

`Note that OpenZeppelin does not currently follow semantic versioning.`
 you can fix import problem by ```npm install``` or 
 [testing-in-es6](http://jamesknelson.com/testing-in-es6-with-mocha-and-babel-6/).  
 
start ganache-cli
```bash
ganache-cli
```   
on another terminal, start test. you can config truffle.js to test on private chains.
```bash
cd themis-chains-contract/smartsolidity

truffle test
```

### Java Interface ###

Compiled java contract code is on smartjava/java except test code(will be coming soon).



