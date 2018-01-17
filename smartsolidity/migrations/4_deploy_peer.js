const Peer = artifacts.require("./Peer.sol");

module.exports = (deployer) => {
    deployer.deploy(Peer);
};