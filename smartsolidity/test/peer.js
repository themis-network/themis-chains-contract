const Peer = artifacts.require("Peer");

contract("Peer test", function(accounts) {
    beforeEach(async function () {
        this.Peer = await Peer.new();
    });

    it("should right join peer", async function () {
        let peerIP1 = "192.168.1.11:800";
        let ad = accounts[1];
        let info = "this is peer's info; in beijing china now";

        await this.Peer.JoinOrUpdate(peerIP1, ad, info);

        let peers = await this.Peer.GetAllPeers();
        assert.equal(peers, peerIP1, "not right join peer");
    });

    it("should right update peer", async function () {
        let peerIP1 = "192.168.1.11:800";
        let ad = accounts[1];
        let info = "this is peer's info; in beijing china now";

        await this.Peer.JoinOrUpdate(peerIP1, ad, info);

        let updated_ad = accounts[2];
        await this.Peer.JoinOrUpdate(peerIP1, updated_ad, info);

        let actualAd = await this.Peer.GetPeerAccount(peerIP1);
        assert.equal(updated_ad, actualAd, "not right update peer account");

        let peerIP2 = "192.111.111.1:0909";
        await this.Peer.JoinOrUpdate(peerIP2, updated_ad, info);
        let peers = await this.Peer.GetAllPeers();
        assert.equal(peers, peerIP1 + "," + peerIP2, "not right return peers' ip");
    });
});