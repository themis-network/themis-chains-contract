import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.1.1.
 */
public final class Order extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506111f48061005e6000396000f300606060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063302f802c14610088578063364f30f6146101f157806355184edc146102c757806396c7767e1461039d5780639c3f1e90146104bf578063a6f9dae114610717578063e6ad63e914610750575b600080fd5b341561009357600080fd5b6101ef600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610876565b005b34156101fc57600080fd5b61024c600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506109b2565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561028c578082015181840152602081019050610271565b50505050905090810190601f1680156102b95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156102d257600080fd5b610322600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610a86565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610362578082015181840152602081019050610347565b50505050905090810190601f16801561038f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156103a857600080fd5b610444600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091908035906020019091905050610b5a565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610484578082015181840152602081019050610469565b50505050905090810190601f1680156104b15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156104ca57600080fd5b6104e4600480803560001916906020019091905050610d4b565b60405180806020018060200180602001806020018581038552898181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156105795780601f1061054e57610100808354040283529160200191610579565b820191906000526020600020905b81548152906001019060200180831161055c57829003601f168201915b50508581038452888181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156105fc5780601f106105d1576101008083540402835291602001916105fc565b820191906000526020600020905b8154815290600101906020018083116105df57829003601f168201915b505085810383528781815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561067f5780601f106106545761010080835404028352916020019161067f565b820191906000526020600020905b81548152906001019060200180831161066257829003601f168201915b50508581038252868181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156107025780601f106106d757610100808354040283529160200191610702565b820191906000526020600020905b8154815290600101906020018083116106e557829003601f168201915b50509850505050505050505060405180910390f35b341561072257600080fd5b61074e600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610d77565b005b341561075b57600080fd5b610874600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610ecd565b005b60003373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156108d357600080fd5b6108dc86611002565b9050846001600083600019166000191681526020019081526020016000206000019080519060200190610910929190611033565b50836001600083600019166000191681526020019081526020016000206001019080519060200190610943929190611033565b50826001600083600019166000191681526020019081526020016000206002019080519060200190610976929190611033565b508160016000836000191660001916815260200190815260200160002060030190805190602001906109a9929190611033565b50505050505050565b6109ba6110b3565b60006109c583611002565b90506001600082600019166000191681526020019081526020016000206001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a795780601f10610a4e57610100808354040283529160200191610a79565b820191906000526020600020905b815481529060010190602001808311610a5c57829003601f168201915b5050505050915050919050565b610a8e6110b3565b6000610a9983611002565b90506001600082600019166000191681526020019081526020016000206003018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b4d5780601f10610b2257610100808354040283529160200191610b4d565b820191906000526020600020905b815481529060010190602001808311610b3057829003601f168201915b5050505050915050919050565b610b626110b3565b600080610b6e86611002565b9150610b7985611002565b90506001841415610c5e57600160008360001916600019168152602001908152602001600020600401600082600019166000191681526020019081526020016000206001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c525780601f10610c2757610100808354040283529160200191610c52565b820191906000526020600020905b815481529060010190602001808311610c3557829003601f168201915b50505050509250610d42565b6002841415610d4157600160008360001916600019168152602001908152602001600020600401600082600019166000191681526020019081526020016000206002018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d355780601f10610d0a57610100808354040283529160200191610d35565b820191906000526020600020905b815481529060010190602001808311610d1857829003601f168201915b50505050509250610d42565b5b50509392505050565b600160205280600052604060002060009150905080600001908060010190806002019080600301905084565b3373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610dd257600080fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f9aecf86140d81442289f667eb72e1202a8fbb3478a686659952e145e853196566000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1682604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390a150565b600080610ed86110c7565b3373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610f3357600080fd5b610f3c87611002565b9250610f4786611002565b915060606040519081016040528087815260200186815260200185815250905080600160008560001916600019168152602001908152602001600020600401600084600019166000191681526020019081526020016000206000820151816000019080519060200190610fbb9291906110fb565b506020820151816001019080519060200190610fd89291906110fb565b506040820151816002019080519060200190610ff59291906110fb565b5090505050505050505050565b600061100c61117b565b829050600081511415611025576000600102915061102d565b602083015191505b50919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061107457805160ff19168380011785556110a2565b828001600101855582156110a2579182015b828111156110a1578251825591602001919060010190611086565b5b5090506110af919061118f565b5090565b602060405190810160405280600081525090565b6060604051908101604052806110db6111b4565b81526020016110e86111b4565b81526020016110f56111b4565b81525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061113c57805160ff191683800117855561116a565b8280016001018555821561116a579182015b8281111561116957825182559160200191906001019061114e565b5b509050611177919061118f565b5090565b602060405190810160405280600081525090565b6111b191905b808211156111ad576000816000905550600101611195565b5090565b90565b6020604051908101604052806000815250905600a165627a7a7230582056016131cbb9a434bb87f57b40df5d9a17f9cd8b87d73c28c1a6d12fd62e8f9b0029";

    private Order(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Order(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<ChangeOwnerEventResponse> getChangeOwnerEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ChangeOwner", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ChangeOwnerEventResponse> responses = new ArrayList<ChangeOwnerEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ChangeOwnerEventResponse typedResponse = new ChangeOwnerEventResponse();
            typedResponse.oriOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeOwnerEventResponse> changeOwnerEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ChangeOwner", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeOwnerEventResponse>() {
            @Override
            public ChangeOwnerEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ChangeOwnerEventResponse typedResponse = new ChangeOwnerEventResponse();
                typedResponse.oriOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> RequestHostingService(String orderId, String buyerId, String buyerEncryPrivKey, String sellerId, String sellerEncryPrivKey) {
        Function function = new Function(
                "RequestHostingService", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(buyerId), 
                new org.web3j.abi.datatypes.Utf8String(buyerEncryPrivKey), 
                new org.web3j.abi.datatypes.Utf8String(sellerId), 
                new org.web3j.abi.datatypes.Utf8String(sellerEncryPrivKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> GetEncryBuyerPrivKey(String orderId) {
        Function function = new Function("GetEncryBuyerPrivKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetEncrySellerPrivKey(String orderId) {
        Function function = new Function("GetEncrySellerPrivKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetTrusteeStoreBuyerOrSellerEncryPrivKey(String orderId, String trusteeId, BigInteger buyerOrSeller) {
        Function function = new Function("GetTrusteeStoreBuyerOrSellerEncryPrivKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(trusteeId), 
                new org.web3j.abi.datatypes.generated.Uint256(buyerOrSeller)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<String, String, String, String>> orders(byte[] param0) {
        final Function function = new Function("orders", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple4<String, String, String, String>>(
                new Callable<Tuple4<String, String, String, String>>() {
                    @Override
                    public Tuple4<String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple4<String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        Function function = new Function(
                "changeOwner", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> RequestHostingServiceTrustee(String orderId, String trusteeId, String hostingEncryBuyerPrivKey, String hostingEncrySellerPrivKey) {
        Function function = new Function(
                "RequestHostingServiceTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(trusteeId), 
                new org.web3j.abi.datatypes.Utf8String(hostingEncryBuyerPrivKey), 
                new org.web3j.abi.datatypes.Utf8String(hostingEncrySellerPrivKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Order> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Order.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Order> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Order.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Order load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Order(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Order load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Order(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ChangeOwnerEventResponse {
        public String oriOwner;

        public String newOwner;
    }
}
