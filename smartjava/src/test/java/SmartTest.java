import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;

public class SmartTest {

    BigInteger gasPrice = new BigInteger("0");
    BigInteger gasLimit = new BigInteger("4500000");

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

        // store priv key
        String orderId = "orderId";
        String buyerId = "buyerId";
        String buyerPrivKey = "asdfwrrgdgwer";
        String sellerId = "sellerId";
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

        // request for hosting service
        contract.RequestHostingService(orderId, buyerId, buyerPrivKey, sellerId, sellerPrivKey).send();
        contract.RequestHostingServiceTrustee(orderId, trustAId, trustABuyPrivKey, trustASellPrivkey).send();
        contract.RequestHostingServiceTrustee(orderId, trustBId, trustBBuyPrivKey, trustBSellPrivKey).send();
        contract.RequestHostingServiceTrustee(orderId, trustCId, trustCBuyPrivKey, trustCSellPrivKey).send();


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
    }

    @Test
    public void TestOwner() throws Exception {
        Web3j web3j = GetConnection("");
        String contractAddress = DeployContract(web3j);
        Credentials credentials = WalletUtils.loadCredentials("test2", "wallet2.json");
        Order contract = Order.load(contractAddress, web3j, credentials, gasPrice, gasLimit);

        String orderId = "orderId";
        String buyerId = "buyerId";
        String buyerPrivKey = "asdfwrrgdgwer";
        String sellerId = "sellerId";
        String sellerPrivKey = "as4jdjgieijtgjkdfkg";
        String trustAId = "qwewrasd";
        String trustABuyPrivKey = "23434tdgsdfg";
        String trustASellPrivkey = "34gaertedg";

        // the RequestHostingService()/RequestHostingServiceTrustee() can be called by owner only.
        boolean execFalse = false;
        try {
            contract.RequestHostingService(orderId, buyerId, buyerPrivKey, sellerId, sellerPrivKey).send();
        }
        catch (Exception e) {
            execFalse = true;
        }

        assertEquals(true, execFalse);

        execFalse = false;
        try {
            contract.RequestHostingServiceTrustee(orderId, trustAId, trustABuyPrivKey, trustASellPrivkey).send();
        }
        catch (Exception e) {
            execFalse = true;
        }

        assertEquals(true, execFalse);
    }
}
