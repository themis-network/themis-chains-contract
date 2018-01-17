pragma solidity ^0.4.18;

import "./Ownable.sol";
import "./Utils.sol";
import "./StringUtils.sol";

contract Peer is Ownable {

    // info about peers on themis
    struct PeerInfo {
        string peerIP;
        address mainAccount;
        string info;
    }

    // detail info of peer
    mapping(bytes32 => PeerInfo) peersInfo;
    // store peer list
    string[] peers;

    function Peer() public {

    }

    // owner add the peer join themis
    function JoinOrUpdate(string ip, address account, string info) public onlyOwner {
        bytes32 ipBytes32Id = Utils.stringToBytes32(ip);

        // add peer info
        if(!StringUtils.equal(peersInfo[ipBytes32Id].peerIP, ip)) {
            peersInfo[ipBytes32Id].peerIP = ip;
            peersInfo[ipBytes32Id].mainAccount = account;
            peersInfo[ipBytes32Id].info = info;
            peers.push(ip);
        }

        // update peer info
        if(StringUtils.equal(peersInfo[ipBytes32Id].peerIP, ip)) {
            peersInfo[ipBytes32Id].peerIP = ip;
            peersInfo[ipBytes32Id].mainAccount = account;
            peersInfo[ipBytes32Id].info = info;
        }
    }

    // get all hosting peer node on themis
    function GetAllPeers() public view returns(string) {
        string memory res = "";
        string memory spe = ",";

        for(uint i = 0; i < peers.length; i++) {
            string memory tmpPeer = peers[i];

            if (i != peers.length - 1) {
                res = Utils.strConcatThree(res, tmpPeer, spe);
            } else {
                res = Utils.strConcat(res, tmpPeer);
            }
        }

        return res;
    }

    // get peer account
    function GetPeerAccount(string ip) public view returns(address) {
        bytes32 ipBytes32Id = Utils.stringToBytes32(ip);
        return peersInfo[ipBytes32Id].mainAccount;
    }

    // get peer info
    function GetPeerInfo(string ip) public view returns(string) {
        bytes32 ipBytes32Id = Utils.stringToBytes32(ip);
        return peersInfo[ipBytes32Id].info;
    }
}
