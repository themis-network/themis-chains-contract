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
import org.web3j.abi.datatypes.generated.Uint256;
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
    private static final String BINARY = "6060604052341561000f57600080fd5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506125b38061005e6000396000f3006060604052600436106100d0576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630c482cda146100d5578063364f30f6146101ab5780634d8a5424146102815780634eab7dd21461035757806355184edc1461042d578063669ef4191461050357806396c7767e146105ef5780639c3f1e9014610711578063a6f9dae114610865578063beede7581461089e578063c8d41c851461090f578063d1e30d4014610b10578063dc5ab5a914610bf3578063e75f6cf314610cb4575b600080fd5b34156100e057600080fd5b610130600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610d93565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610170578082015181840152602081019050610155565b50505050905090810190601f16801561019d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156101b657600080fd5b610206600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610e76565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561024657808201518184015260208101905061022b565b50505050905090810190601f1680156102735780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561028c57600080fd5b6102dc600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610f59565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561031c578082015181840152602081019050610301565b50505050905090810190601f1680156103495780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561036257600080fd5b6103b2600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061103c565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103f25780820151818401526020810190506103d7565b50505050905090810190601f16801561041f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561043857600080fd5b610488600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050611110565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156104c85780820151818401526020810190506104ad565b50505050905090810190601f1680156104f55780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561050e57600080fd5b6105ed600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080359060200190919050506111f3565b005b34156105fa57600080fd5b610696600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001909190505061133b565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156106d65780820151818401526020810190506106bb565b50505050905090810190601f1680156107035780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561071c57600080fd5b610736600480803560001916906020019091905050611546565b6040518080602001806020018581526020018481526020018381038352878181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156107cf5780601f106107a4576101008083540402835291602001916107cf565b820191906000526020600020905b8154815290600101906020018083116107b257829003601f168201915b50508381038252868181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156108525780601f1061082757610100808354040283529160200191610852565b820191906000526020600020905b81548152906001019060200180831161083557829003601f168201915b5050965050505050505060405180910390f35b341561087057600080fd5b61089c600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611574565b005b34156108a957600080fd5b6108f9600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506116ca565b6040518082815260200191505060405180910390f35b341561091a57600080fd5b610b0e600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080359060200190919080359060200190919050506117d3565b005b3415610b1b57600080fd5b610bf1600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506119fb565b005b3415610bfe57600080fd5b610c9a600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091908035906020019091905050611aed565b604051808215151515815260200191505060405180910390f35b3415610cbf57600080fd5b610d18600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091908035906020019091905050611e17565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610d58578082015181840152602081019050610d3d565b50505050905090810190601f168015610d855780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610d9b61241a565b6000610da6836121d5565b90506001600082600019166000191681526020019081526020016000206002016001600281101515610dd457fe5b018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e695780601f10610e3e57610100808354040283529160200191610e69565b820191906000526020600020905b815481529060010190602001808311610e4c57829003601f168201915b5050505050915050919050565b610e7e61241a565b6000610e89836121d5565b90506001600082600019166000191681526020019081526020016000206004016000600281101515610eb757fe5b018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f4c5780601f10610f2157610100808354040283529160200191610f4c565b820191906000526020600020905b815481529060010190602001808311610f2f57829003601f168201915b5050505050915050919050565b610f6161241a565b6000610f6c836121d5565b90506001600082600019166000191681526020019081526020016000206002016000600281101515610f9a57fe5b018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561102f5780601f106110045761010080835404028352916020019161102f565b820191906000526020600020905b81548152906001019060200180831161101257829003601f168201915b5050505050915050919050565b61104461241a565b600061104f836121d5565b90506001600082600019166000191681526020019081526020016000206008018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111035780601f106110d857610100808354040283529160200191611103565b820191906000526020600020905b8154815290600101906020018083116110e657829003601f168201915b5050505050915050919050565b61111861241a565b6000611123836121d5565b9050600160008260001916600019168152602001908152602001600020600401600160028110151561115157fe5b018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111e65780601f106111bb576101008083540402835291602001916111e6565b820191906000526020600020905b8154815290600101906020018083116111c957829003601f168201915b5050505050915050919050565b6000803373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561125157600080fd5b60018314806112605750600283145b151561126b57600080fd5b611274866121d5565b915061127f856121d5565b905060018314156112da5783600160008460001916600019168152602001908152602001600020600c016000836000191660001916815260200190815260200160002060000190805190602001906112d892919061242e565b505b60028314156113335783600160008460001916600019168152602001908152602001600020600c0160008360001916600019168152602001908152602001600020600101908051906020019061133192919061242e565b505b505050505050565b61134361241a565b60008060018414806113555750600284145b151561136057600080fd5b611369866121d5565b9150611374856121d5565b9050600184141561145957600160008360001916600019168152602001908152602001600020600c01600082600019166000191681526020019081526020016000206000018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561144d5780601f106114225761010080835404028352916020019161144d565b820191906000526020600020905b81548152906001019060200180831161143057829003601f168201915b5050505050925061153d565b600284141561153c57600160008360001916600019168152602001908152602001600020600c01600082600019166000191681526020019081526020016000206001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115305780601f1061150557610100808354040283529160200191611530565b820191906000526020600020905b81548152906001019060200180831161151357829003601f168201915b5050505050925061153d565b5b50509392505050565b60016020528060005260406000206000915090508060080190806009019080600a01549080600b0154905084565b3373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156115cf57600080fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f9aecf86140d81442289f667eb72e1202a8fbb3478a686659952e145e853196566000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1682604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390a150565b60008060008060003373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561172d57600080fd5b611736866121d5565b9350600160008560001916600019168152602001908152602001600020600a01549250600160008560001916600019168152602001908152602001600020600601805490509150828210151561178f57600194506117ca565b60016000856000191660001916815260200190815260200160002060070180549050905082811015156117c557600294506117ca565b600094505b50505050919050565b60003373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561183057600080fd5b6118398a6121d5565b905088600160008360001916600019168152602001908152602001600020600001600060028110151561186857fe5b01908051906020019061187c92919061242e565b508360016000836000191660001916815260200190815260200160002060000160016002811015156118aa57fe5b0190805190602001906118be92919061242e565b508760016000836000191660001916815260200190815260200160002060020160006002811015156118ec57fe5b01908051906020019061190092919061242e565b5086600160008360001916600019168152602001908152602001600020600401600060028110151561192e57fe5b01908051906020019061194292919061242e565b5085600160008360001916600019168152602001908152602001600020600801908051906020019061197592919061242e565b508460016000836000191660001916815260200190815260200160002060090190805190602001906119a892919061242e565b5082600160008360001916600019168152602001908152602001600020600a018190555081600160008360001916600019168152602001908152602001600020600b018190555050505050505050505050565b60003373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611a5857600080fd5b611a61846121d5565b9050826001600083600019166000191681526020019081526020016000206002016001600281101515611a9057fe5b019080519060200190611aa492919061242e565b50816001600083600019166000191681526020019081526020016000206004016001600281101515611ad257fe5b019080519060200190611ae692919061242e565b5050505050565b6000806000611afa61241a565b611b0261241a565b3373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611b5d57600080fd5b6001861480611b6c5750600286145b1515611b7757600080fd5b611b80886121d5565b9350611b8b876121d5565b92506001861415611ccc57600160008560001916600019168152602001908152602001600020600c01600084600019166000191681526020019081526020016000206000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611c645780601f10611c3957610100808354040283529160200191611c64565b820191906000526020600020905b815481529060010190602001808311611c4757829003601f168201915b505050505091506001600085600019166000191681526020019081526020016000206006018054806001018281611c9b91906124ae565b916000526020600020900160008490919091509080519060200190611cc192919061242e565b505060019450611e0c565b6002861415611e0b57600160008560001916600019168152602001908152602001600020600c01600084600019166000191681526020019081526020016000206001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611da35780601f10611d7857610100808354040283529160200191611da3565b820191906000526020600020905b815481529060010190602001808311611d8657829003601f168201915b505050505090506001600085600019166000191681526020019081526020016000206007018054806001018281611dda91906124ae565b916000526020600020900160008390919091509080519060200190611e0092919061242e565b505060019450611e0c565b5b505050509392505050565b611e1f61241a565b611e2761241a565b600080611e3261241a565b6000611e3c61241a565b611e4461241a565b6000611e4e61241a565b6040805190810160405280600181526020017f2c00000000000000000000000000000000000000000000000000000000000000815250985060018b1480611e95575060028b145b1515611ea057600080fd5b611ea98c6121d5565b97506000965060018b141561203a5760206040519081016040528060008152509550600160008960001916600019168152602001908152602001600020600601805490509450600160008960001916600019168152602001908152602001600020600a01548510151515611f1c57600080fd5b600096505b848710156120325760016000896000191660001916815260200190815260200160002060060187815481101515611f5457fe5b90600052602060002090018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611ff35780601f10611fc857610100808354040283529160200191611ff3565b820191906000526020600020905b815481529060010190602001808311611fd657829003601f168201915b5050505050935060018503871415156120185761201186858b612206565b9550612025565b6120228685612236565b95505b8680600101975050611f21565b8599506121c6565b60028b14156121c55760206040519081016040528060008152509250600160008960001916600019168152602001908152602001600020600701805490509150600160008960001916600019168152602001908152602001600020600a015482101515156120a757600080fd5b600096505b818710156121bd57600160008960001916600019168152602001908152602001600020600701878154811015156120df57fe5b90600052602060002090018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561217e5780601f106121535761010080835404028352916020019161217e565b820191906000526020600020905b81548152906001019060200180831161216157829003601f168201915b5050505050905060018203871415156121a35761219c83828b612206565b92506121b0565b6121ad8382612236565b92505b86806001019750506120ac565b8299506121c6565b5b50505050505050505092915050565b60006121df6124da565b8290506000815114156121f85760006001029150612200565b602083015191505b50919050565b61220e61241a565b61221661241a565b6122208585612236565b905061222c8184612236565b9150509392505050565b61223e61241a565b6122466124da565b61224e6124da565b61225661241a565b61225e6124da565b60008088955087945084518651016040518059106122795750595b9080825280601f01601f191660200182016040525093508392506000915060009050600090505b85518110156123535785818151811015156122b757fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f010000000000000000000000000000000000000000000000000000000000000002838380600101945081518110151561231657fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535080806001019150506122a0565b600090505b845181101561240b57848181518110151561236f57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f01000000000000000000000000000000000000000000000000000000000000000283838060010194508151811015156123ce57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053508080600101915050612358565b82965050505050505092915050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061246f57805160ff191683800117855561249d565b8280016001018555821561249d579182015b8281111561249c578251825591602001919060010190612481565b5b5090506124aa91906124ee565b5090565b8154818355818115116124d5578183600052602060002091820191016124d49190612513565b5b505050565b602060405190810160405280600081525090565b61251091905b8082111561250c5760008160009055506001016124f4565b5090565b90565b61253c91905b80821115612538576000818161252f919061253f565b50600101612519565b5090565b90565b50805460018160011615610100020316600290046000825580601f106125655750612584565b601f01602090049060005260206000209081019061258391906124ee565b5b505600a165627a7a72305820ee6ce6ae7833b7e65435276f16bdc8293dae74c88171ef493e85664c0e45e8430029";

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

    public RemoteCall<String> GetSellerPublicKey(String orderId) {
        Function function = new Function("GetSellerPublicKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetEncryBuyerPrivKey(String orderId) {
        Function function = new Function("GetEncryBuyerPrivKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetBuyerPublicKey(String orderId) {
        Function function = new Function("GetBuyerPublicKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetVirTrusteePublicKey(String orderId) {
        Function function = new Function("GetVirTrusteePublicKey", 
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

    public RemoteCall<TransactionReceipt> UploadShardKeyToTrustee(String orderId, String trusteeId, String hostingEncryPrivKey, BigInteger userType) {
        Function function = new Function(
                "UploadShardKeyToTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(trusteeId), 
                new org.web3j.abi.datatypes.Utf8String(hostingEncryPrivKey), 
                new org.web3j.abi.datatypes.generated.Uint256(userType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> GetTrusteeStoreBuyerOrSellerEncryPrivKey(String orderId, String trusteeId, BigInteger userType) {
        Function function = new Function("GetTrusteeStoreBuyerOrSellerEncryPrivKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(trusteeId), 
                new org.web3j.abi.datatypes.generated.Uint256(userType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<String, String, BigInteger, BigInteger>> orders(byte[] param0) {
        final Function function = new Function("orders", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<String, String, BigInteger, BigInteger>>(
                new Callable<Tuple4<String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple4<String, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
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

    public RemoteCall<BigInteger> JudgeWhoWin(String orderId) {
        Function function = new Function("JudgeWhoWin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> RequestHostingService(String orderId, String buyerId, String buyerPublicKey, String buyerEncryPrivKey, String virTrusteePublicKey, String virTrusteePrivKey, String sellerId, BigInteger K, BigInteger N) {
        Function function = new Function(
                "RequestHostingService", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(buyerId), 
                new org.web3j.abi.datatypes.Utf8String(buyerPublicKey), 
                new org.web3j.abi.datatypes.Utf8String(buyerEncryPrivKey), 
                new org.web3j.abi.datatypes.Utf8String(virTrusteePublicKey), 
                new org.web3j.abi.datatypes.Utf8String(virTrusteePrivKey), 
                new org.web3j.abi.datatypes.Utf8String(sellerId), 
                new org.web3j.abi.datatypes.generated.Uint256(K), 
                new org.web3j.abi.datatypes.generated.Uint256(N)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> UploadSellerKey(String orderId, String sellerPublicKey, String sellerEncryPrivKey) {
        Function function = new Function(
                "UploadSellerKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(sellerPublicKey), 
                new org.web3j.abi.datatypes.Utf8String(sellerEncryPrivKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> JudgeUserWinByTrustee(String orderId, String trusteeId, BigInteger userType) {
        Function function = new Function(
                "JudgeUserWinByTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(trusteeId), 
                new org.web3j.abi.datatypes.generated.Uint256(userType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> GetWinerShardKey(String orderId, BigInteger userType) {
        Function function = new Function("GetWinerShardKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.generated.Uint256(userType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
