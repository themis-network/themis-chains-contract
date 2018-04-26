package o;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public final class FeeManager extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160408061077983398101604052805160209091015160008054600160a060020a03191633600160a060020a03908116919091179091558216151561005757600080fd5b6000811161006457600080fd5b60018054600160a060020a031916600160a060020a0393909316929092179091556002556106e2806100976000396000f30060806040526004361061005e5763ffffffff60e060020a6000350416630d5125f881146100635780635b6a1d03146100e8578063978bbdb914610121578063a6f9dae114610148578063bcf0cc4a1461016b578063f123061514610180575b600080fd5b34801561006f57600080fd5b5060408051602060046064358181013583810280860185019096528085526100d4958335956024803560ff1696604435600160a060020a0316963696909560849593909101929091829190850190849080828437509497506101b19650505050505050565b604080519115158252519081900360200190f35b3480156100f457600080fd5b506100fd6103ea565b6040518082600181111561010d57fe5b60ff16815260200191505060405180910390f35b34801561012d57600080fd5b506101366103ef565b60408051918252519081900360200190f35b34801561015457600080fd5b50610169600160a060020a03600435166103f5565b005b34801561017757600080fd5b506100fd61047e565b34801561018c57600080fd5b50610195610483565b60408051600160a060020a039092168252519081900360200190f35b60008084818160018111156101c257fe5b14806101d9575060018160018111156101d757fe5b145b15156101e457600080fd5b600160a060020a038516151561025b57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601c60248201527f7573657220616464726573732073686f756c642062652076616c696400000000604482015290519081900360640190fd5b83516000106102f157604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152603560248201527f6e756d626572206f662073657276696365206e6f6465732f757365727320736860448201527f6f756c6420626967676572207468616e207a65726f0000000000000000000000606482015290519081900360840190fd5b6102fc868551610492565b9150600086600181111561030c57fe5b14156103205761031e878686856104b1565b505b600186600181111561032e57fe5b1415610342576103408786868561059b565b505b7f5e78445e244ee3c24ad233a4718333f3a1941b1d6388a361aa88490429ed8119878784876040518085815260200184600181111561037d57fe5b60ff16815260200183815260200180602001828103825283818151815260200191508051906020019060200280838360005b838110156103c75781810151838201526020016103af565b505050509050019550505050505060405180910390a15060019695505050505050565b600190565b60025481565b60005433600160a060020a0390811691161461041057600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a038381169182179283905560408051939091168352602083019190915280517f9aecf86140d81442289f667eb72e1202a8fbb3478a686659952e145e853196569281900390910190a150565b600090565b600154600160a060020a031681565b6002546000906104a8908363ffffffff61067816565b90505b92915050565b60008060006104ca8551856106a190919063ffffffff16565b9150600090505b845181101561058e576001548551600160a060020a03909116906323b872dd9088908890859081106104ff57fe5b60209081029091018101516040805160e060020a63ffffffff8716028152600160a060020a03948516600482015293909116602484015260448301879052516064808401938290030181600087803b15801561055a57600080fd5b505af115801561056e573d6000803e3d6000fd5b505050506040513d602081101561058457600080fd5b50506001016104d1565b5060019695505050505050565b60008060006105b48551856106a190919063ffffffff16565b9150600090505b845181101561058e576001548551600160a060020a03909116906323b872dd9088908890859081106105e957fe5b60209081029091018101516040805160e060020a63ffffffff8716028152600160a060020a03948516600482015293909116602484015260448301879052516064808401938290030181600087803b15801561064457600080fd5b505af1158015610658573d6000803e3d6000fd5b505050506040513d602081101561066e57600080fd5b50506001016105bb565b6000821515610689575060006104ab565b5081810281838281151561069957fe5b04146104ab57fe5b600081838115156106ae57fe5b0493925050505600a165627a7a723058203796bd2583b0ec60abea87d108f4d19c5579cef19c367fdc11e2bf1d153489810029";

    private FeeManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private FeeManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<FeePayedEventResponse> getFeePayedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("FeePayed", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<FeePayedEventResponse> responses = new ArrayList<FeePayedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            FeePayedEventResponse typedResponse = new FeePayedEventResponse();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serviceType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.payedFee = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.serviceNodes = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<FeePayedEventResponse> feePayedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("FeePayed", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, FeePayedEventResponse>() {
            @Override
            public FeePayedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                FeePayedEventResponse typedResponse = new FeePayedEventResponse();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.serviceType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.payedFee = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.serviceNodes = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
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

    public RemoteCall<TransactionReceipt> payFee(BigInteger orderID, BigInteger serviceType, String user, List<String> serviceNodes) {
        Function function = new Function(
                "payFee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.generated.Uint8(serviceType), 
                new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(serviceNodes, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getArbitrationServiceType() {
        Function function = new Function("getArbitrationServiceType", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> feeRate() {
        Function function = new Function("feeRate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        Function function = new Function(
                "changeOwner", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getHostServiceType() {
        Function function = new Function("getHostServiceType", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> GET() {
        Function function = new Function("GET", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<FeeManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String getAddress, BigInteger _feeRate) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(getAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_feeRate)));
        return deployRemoteCall(FeeManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<FeeManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String getAddress, BigInteger _feeRate) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(getAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_feeRate)));
        return deployRemoteCall(FeeManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static FeeManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FeeManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static FeeManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FeeManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class FeePayedEventResponse {
        public BigInteger orderID;

        public BigInteger serviceType;

        public BigInteger payedFee;

        public List<String> serviceNodes;
    }

    public static class ChangeOwnerEventResponse {
        public String oriOwner;

        public String newOwner;
    }
}
