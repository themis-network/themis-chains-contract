import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.failNotEquals;

public class SmartTest {

    BigInteger gasPrice = new BigInteger("0");
    BigInteger gasLimit = new BigInteger("4500000");

    // data for test
    String orderId = "orderId";
    String buyerId = "buyerId";
    String buyerPublicKey = "fasdfwere";
    String buyerPrivKey = "asdfwrrgdgwer";
    String sellerId = "sellerId";
    String sellerPublicKey = "fsdfwenrnwer23";
    String sellerPrivKey = "as4jdjgieijtgjkdfkg";
    String trustAId = "qwewrasd";
    String trustABuyPrivKey = "23434tdgsdfg";
    String trustASellPrivkey = "34gaertedg";
    String trustBId = "wertg324th4trgf";
    String trustBBuyPrivKey = "dsfg42625g";
    String trustBSellPrivKey = "wergf2453452345";
    String trustCId = "dgt324tgfsewrg";
    String trustCBuyPrivKey = "twert3245dfg3";
    String trustCSellPrivKey = "sdfg4t4gerwg";
    String virTrusteePublicKey = "asdg3q453gaedg";
    String virTrusteePrivKey = "fasdf4tgrq3egerg";
    BigInteger K = new BigInteger("2");
    BigInteger N = new BigInteger("3");

    public Web3j GetConnection(String url) {
        Web3j web3j;

        if ("".equals(url)) {
            web3j = Web3j.build(new HttpService());// defaults to http://localhost:8545/
        } else {
            web3j = Web3j.build(new HttpService(url));
        }

        return web3j;
    }

    public String DeployContract(Web3j web3j) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials("test", "wallet.json");
        Order contract = Order.deploy(web3j, credentials, gasPrice, gasLimit).send();
        String contractAddress = contract.getContractAddress();
        return contractAddress;
    }

    @Test
    public void TestSmart() throws Exception {
        // get deployed contract
        Web3j web3j = GetConnection("");
        String contractAddress = DeployContract(web3j);
        Credentials credentials = WalletUtils.loadCredentials("test", "wallet.json");
        Order contract = Order.load(contractAddress, web3j, credentials, gasPrice, gasLimit);

        // request for hosting service
        contract.RequestHostingService(orderId, buyerId, buyerPublicKey, buyerPrivKey, virTrusteePublicKey, virTrusteePrivKey, sellerId, K, N).send();
        contract.UploadShardKeyToTrustee(orderId, trustAId, trustABuyPrivKey, new BigInteger("1")).send();
        contract.UploadShardKeyToTrustee(orderId, trustBId, trustBBuyPrivKey, new BigInteger("1")).send();
        contract.UploadShardKeyToTrustee(orderId, trustCId, trustCBuyPrivKey, new BigInteger("1")).send();

        contract.UploadSellerKey(orderId, sellerPublicKey, sellerPrivKey).send();
        contract.UploadShardKeyToTrustee(orderId, trustAId, trustASellPrivkey, new BigInteger("2")).send();
        contract.UploadShardKeyToTrustee(orderId, trustBId, trustBSellPrivKey, new BigInteger("2")).send();
        contract.UploadShardKeyToTrustee(orderId, trustCId, trustCSellPrivKey, new BigInteger("2")).send();


        // get stored priv_key
        String acutalBuyerPrivKey = contract.GetEncryBuyerPrivKey(orderId).send();
        String acutalSellerPrivKey = contract.GetEncrySellerPrivKey(orderId).send();
        assertEquals(buyerPrivKey, acutalBuyerPrivKey);
        assertEquals(sellerPrivKey, acutalSellerPrivKey);

        String trusteeA_buyer_privKey = contract.GetTrusteeStoreBuyerOrSellerEncryPrivKey(orderId, trustAId, new BigInteger("1")).send();
        String trusteeA_seller_privKey = contract.GetTrusteeStoreBuyerOrSellerEncryPrivKey(orderId, trustAId, new BigInteger("2")).send();
        assertEquals(trustABuyPrivKey, trusteeA_buyer_privKey);
        assertEquals(trustASellPrivkey, trusteeA_seller_privKey);

        String trusteeB_buyer_privKey = contract.GetTrusteeStoreBuyerOrSellerEncryPrivKey(orderId, trustBId, new BigInteger("1")).send();
        String trusteeB_seller_privKey = contract.GetTrusteeStoreBuyerOrSellerEncryPrivKey(orderId, trustBId, new BigInteger("2")).send();
        assertEquals(trustBBuyPrivKey, trusteeB_buyer_privKey);
        assertEquals(trustBSellPrivKey, trusteeB_seller_privKey);

        String trusteeC_buyer_privKey = contract.GetTrusteeStoreBuyerOrSellerEncryPrivKey(orderId, trustCId, new BigInteger("1")).send();
        String trusteeC_seller_privKey = contract.GetTrusteeStoreBuyerOrSellerEncryPrivKey(orderId, trustCId, new BigInteger("2")).send();
        assertEquals(trustCBuyPrivKey, trusteeC_buyer_privKey);
        assertEquals(trustCSellPrivKey, trusteeC_seller_privKey);

        contract.JudgeUserWinByTrustee(orderId, trustAId, new BigInteger("1")).send();
        contract.JudgeUserWinByTrustee(orderId, trustBId, new BigInteger("2")).send();

        BigInteger winner = contract.JudgeWhoWin(orderId).send();
        assertEquals(new BigInteger("0"), winner);

        contract.JudgeUserWinByTrustee(orderId, trustCId, new BigInteger("1")).send();

        winner = contract.JudgeWhoWin(orderId).send();
        assertEquals(new BigInteger("1"), winner);

        String keys = contract.GetWinerShardKey(orderId, new BigInteger("1")).send();
        assertEquals(trustABuyPrivKey + "," + trustCBuyPrivKey, keys);
    }

    @Test
    public void TestOwner() throws Exception {
        Web3j web3j = GetConnection("");
        String contractAddress = DeployContract(web3j);
        Credentials credentials = WalletUtils.loadCredentials("test2", "wallet2.json");
        Order contract = Order.load(contractAddress, web3j, credentials, gasPrice, gasLimit);

        // the RequestHostingService()/UploadShardKeyToTrustee() can be called by owner only.
        boolean execFalse = false;
        try {
            contract.RequestHostingService(orderId, buyerId, buyerPublicKey, buyerPrivKey, virTrusteePublicKey, virTrusteePrivKey, sellerId, K, N).send();
        }
        catch (Exception e) {
            execFalse = true;
        }

        assertEquals(true, execFalse);

        execFalse = false;
        try {
            contract.UploadShardKeyToTrustee(orderId, trustAId, trustABuyPrivKey, new BigInteger("1")).send();
        }
        catch (Exception e) {
            execFalse = true;
        }

        assertEquals(true, execFalse);

        execFalse = false;
        try {
            contract.UploadSellerKey(orderId, sellerPublicKey, sellerPrivKey).send();
        }
        catch (Exception e) {
            execFalse = true;
        }

        assertEquals(true, execFalse);
    }

    @Test
    public void TestReputation() throws Exception {
        Web3j web3j = GetConnection("");
        Credentials credentials = WalletUtils.loadCredentials("test2", "wallet2.json");
        Reputation contract = Reputation.deploy(web3j, credentials, gasPrice, gasLimit).send();

        String id = "test";
        BigInteger reputation = contract.GetReputation(id).send();
        assertEquals(reputation, new BigInteger("0"));

        BigInteger step = new BigInteger("1");
        contract.StoreArbitrate(id, step).send();

        BigInteger after = contract.GetReputation(id).send();
        assertEquals(after.subtract(step), reputation);

        BigInteger newStep = new BigInteger("-1");
        contract.StoreArbitrate(id, newStep).send();

        BigInteger fin = contract.GetReputation(id).send();
        assertEquals(fin.subtract(after), newStep);
    }

    @Test
    public void TestPeer() throws Exception {
        Web3j web3j = GetConnection("");
        Credentials credentials = WalletUtils.loadCredentials("test2", "wallet2.json");
        Peer contract = Peer.deploy(web3j, credentials, gasPrice, gasLimit).send();

        String peer1 = "192.179.1.1:080";
        String address = "0x05defc201c84076e1515bab50a3a25a39ab9c4d4";
        String info = "this is peer one, in beijing china";

        contract.JoinOrUpdate(peer1, address, info).send();
        String peers = contract.GetAllPeers().send();
        assertEquals(peer1, peers);

        String newAddress = "0xc311e6346c9817bbc8db89fc8db79faf6d454c2c";
        contract.JoinOrUpdate(peer1, newAddress, info).send();
        String acutalAdd = contract.GetPeerAccount(peer1).send();
        assertEquals(acutalAdd, newAddress);

        String peer2 = "192.23.123.11:999";
        contract.JoinOrUpdate(peer2, newAddress, info).send();
        String newPeers = contract.GetAllPeers().send();
        assertEquals(newPeers, peer1 + "," + peer2);
    }

    @Test
    public void TestObBlock() throws Exception {
        CatchToLastetBlock();
        CatchFromStartToEnd();
    }

    // this will throw exception when reach latest block
    public void CatchToLastetBlock() throws Exception {
        Web3j web3j = GetConnection("http://192.168.1.205:8545");

        BigInteger start = new BigInteger("1");
        DefaultBlockParameterNumber startBlock = new DefaultBlockParameterNumber(start);

        web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(startBlock, false).subscribe(block -> {
            System.out.println("Block number " + block.getBlock().getNumber());
            System.out.println("Miner is : " + block.getBlock().getMiner());
        }, Throwable::printStackTrace);
    }

    // this not occur exception
    public void CatchFromStartToEnd() throws Exception {
        Web3j web3j = GetConnection("http://192.168.1.205:8545");
        BigInteger start = new BigInteger("1");
        DefaultBlockParameterNumber startBlock = new DefaultBlockParameterNumber(start);

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        DefaultBlockParameterNumber endBlock = new DefaultBlockParameterNumber(blockNumber.getBlockNumber());

        web3j.replayBlocksObservable(startBlock, endBlock, false).subscribe(block -> {
            System.out.println("Block number " + block.getBlock().getNumber());
            System.out.println("Miner is : " + block.getBlock().getMiner());
        },  Throwable::printStackTrace);
    }

    @Test
    public void TestCreateEthereum() throws Exception {

        // back end build web3j
        Web3j web3j = GetConnection("http://192.168.1.205:8545");

        // load on front end
        Credentials credentials = WalletUtils.loadCredentials("test", "wallet.json");
        String to = "0xc311e6346c9817bbc8db89fc8db79faf6d454c2c";
        BigInteger value = new BigInteger("111");

        EthGetBalance ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        System.out.println("eth balance is :" + ethGetBalance.getBalance());

        // This will send request to back end
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        System.out.println("nonce is :" + nonce);

        // generate transaction on front end
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Hex.toHexString(signedMessage);
        // this is not on doc; but without 0x, ethSendRawTransaction method will fail
        if (!hexValue.startsWith("0x")) {
            hexValue = "0x" + hexValue;
        }

        // back end send transaction to web3j
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        Response.Error err = ethSendTransaction.getError();
        if (err != null) {
            System.out.println(err.getMessage());
            return;
        }

        System.out.println("tx hash is: " + ethSendTransaction.getTransactionHash());

    }

    @Test
    public void TestCreateContract() throws Exception {
        // do on back end
        Web3j web3j = GetConnection("http://192.168.1.205:8545");

        // do on front end
        Credentials credentials = WalletUtils.loadCredentials("test", "wallet.json");

        // do on front end; send request to back end to get nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();


        String _to = "0xc311e6346c9817bbc8db89fc8db79faf6d454c2c";
        BigInteger _value = new BigInteger("1000000000");
        Function function = new Function(
                "transfer",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to),
                        new org.web3j.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);

        // peer contract address
        String contractAddress = "0x69f30389d2250e5261422020757597bC6d7516b8";
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, encodedFunction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Hex.toHexString(signedMessage);
        // this is not on doc; but without 0x, ethSendRawTransaction method will fail
        if (!hexValue.startsWith("0x")) {
            hexValue = "0x" + hexValue;
        }

        // back end send transaction to web3j
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        Response.Error err = ethSendTransaction.getError();
        if (err != null) {
            System.out.println(err.getMessage());
            return;
        }

        System.out.println("tx hash is: " + ethSendTransaction.getTransactionHash());
    }
}
