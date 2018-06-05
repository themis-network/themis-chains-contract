
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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
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
import org.web3j.tuples.generated.Tuple3;
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
public final class Trustee extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506002608060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016000815260200160206040519081016040528060008152508152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155604082015181600201556060820151816003019080519060200190620001439291906200014d565b50505050620001fc565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200019057805160ff1916838001178555620001c1565b82800160010185558215620001c1579182015b82811115620001c0578251825591602001919060010190620001a3565b5b509050620001d09190620001d4565b5090565b620001f991905b80821115620001f5576000816000905550600101620001db565b5090565b90565b6126c0806200020c6000396000f3006080604052600436106100d0576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806303120506146100d557806305b050de146101305780630b2414d6146101525780631dc6fa5e146101975780632d16ddf314610218578063545e85bd1461029a578063715018a6146102f1578063717d63a4146103085780638da5cb5b146103ef578063c214e6d514610446578063c784cd1714610510578063d56fe3cd1461056b578063d7ba3d8714610616578063f2fde38b1461067b575b600080fd5b3480156100e157600080fd5b50610116600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506106be565b604051808215151515815260200191505060405180910390f35b6101386109eb565b604051808215151515815260200191505060405180910390f35b34801561015e57600080fd5b5061017d60048036038101908080359060200190929190505050610b62565b604051808215151515815260200191505060405180910390f35b3480156101a357600080fd5b506101fe600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610d37565b604051808215151515815260200191505060405180910390f35b34801561022457600080fd5b5061024360048036038101908080359060200190929190505050610e7d565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561028657808201518184015260208101905061026b565b505050509050019250505060405180910390f35b3480156102a657600080fd5b506102db600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506111b4565b6040518082815260200191505060405180910390f35b3480156102fd57600080fd5b506103066111cc565b005b34801561031457600080fd5b50610333600480360381019080803590602001909291905050506112ce565b604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156103b1578082015181840152602081019050610396565b50505050905090810190601f1680156103de5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b3480156103fb57600080fd5b506104046113c5565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561045257600080fd5b50610487600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506113ea565b6040518084815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156104d35780820151818401526020810190506104b8565b50505050905090810190601f1680156105005780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561051c57600080fd5b50610551600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506115b7565b604051808215151515815260200191505060405180910390f35b34801561057757600080fd5b506105fc600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506116cf565b604051808215151515815260200191505060405180910390f35b34801561062257600080fd5b50610661600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050611907565b604051808215151515815260200191505060405180910390f35b34801561068757600080fd5b506106bc600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611a51565b005b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561071c57600080fd5b610725836115b7565b151561073057600080fd5b6002600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561077e57fe5b90600052602060002090600402016001015490506107e5600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546001611ba690919063ffffffff16565b50608060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016000815260200160206040519081016040528060008152508152506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561087f57fe5b906000526020600020906004020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155606082015181600301908051906020019061090492919061256f565b509050506000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550600081111561099e578273ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f1935050505015801561099c573d6000803e3d6000fd5b505b8273ffffffffffffffffffffffffffffffffffffffff167f85c7d121e584c108defd0d2a0a6539823e13c0e4afeabce14ff5a4330236ca3060405160405180910390a26001915050919050565b6000806000806109fa336115b7565b1515610a0557600080fd5b600034111515610a1457600080fd5b3392506002600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610a6557fe5b9060005260206000209060040201600201549150610aeb346002600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610acb57fe5b906000526020600020906004020160010154611c9490919063ffffffff16565b905060011515610afc848484611cb0565b1515141515610b0a57600080fd5b3373ffffffffffffffffffffffffffffffffffffffff167f6c5e7e4e203673af133e4db76883b962ff3faf782281e7bea6f78f2c7874dba0346040518082815260200191505060405180910390a26001935050505090565b6000806000806000610b73336115b7565b1515610b7e57600080fd5b600086111515610b8d57600080fd5b3393506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610bde57fe5b90600052602060002090600402016002015492506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610c4057fe5b9060005260206000209060040201600101549150818611151515610c6357600080fd5b610c768683611f9890919063ffffffff16565b905060011515610c87858584611cb0565b1515141515610c9557600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc879081150290604051600060405180830381858888f19350505050158015610cdb573d6000803e3d6000fd5b503373ffffffffffffffffffffffffffffffffffffffff167fc712b41e0b9b7891d0d4ffebc1102bbe2f6ffa1ad4d8047cda3a6dd03ca6ed42876040518082815260200191505060405180910390a26001945050505050919050565b6000610d42336115b7565b1515610d4d57600080fd5b816002600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610d9c57fe5b90600052602060002090600402016003019080519060200190610dc09291906125ef565b503373ffffffffffffffffffffffffffffffffffffffff167f659b5f2849c0905a190866892969f81dbde54b9f354dea5212cce955104ef8bf836040518080602001828103825283818151815260200191508051906020019080838360005b83811015610e3a578082015181840152602081019050610e1f565b50505050905090810190601f168015610e675780820380516001836020036101000a031916815260200191505b509250505060405180910390a260019050919050565b606060008060606000606060008088111515610e9857600080fd5b87604051908082528060200260200182016040528015610ec75781602001602082028038833980820191505090505b509350610ee16000600180611fb19092919063ffffffff16565b8096508197505050851515610f28576000604051908082528060200260200182016040528015610f205781602001602082028038833980820191505090505b5096506111a9565b5b600085141515610ff757600285815481101515610f4257fe5b906000526020600020906004020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168484815181101515610f8257fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505082806001019350508783101515610fd457610ff7565b610fea85600180611fb19092919063ffffffff16565b8096508197505050610f29565b8783101561112d578260405190808252806020026020018201604052801561102e5781602001602082028038833980820191505090505b509150600090505b828110156110ad57838181518110151561104c57fe5b90602001906020020151828281518110151561106457fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508080600101915050611036565b7fe91db684c5662f94c81cc2ae7fbec373c3bfba4ce8fa5e377a877d0d41a81fa7826040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156111125780820151818401526020810190506110f7565b505050509050019250505060405180910390a18196506111a9565b7fe91db684c5662f94c81cc2ae7fbec373c3bfba4ce8fa5e377a877d0d41a81fa7846040518080602001828103825283818151815260200191508051906020019060200280838360005b83811015611192578082015181840152602081019050611177565b505050509050019250505060405180910390a18396505b505050505050919050565b60036020528060005260406000206000915090505481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561122757600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482060405160405180910390a260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6002818154811015156112dd57fe5b90600052602060002090600402016000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015490806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113bb5780601f10611390576101008083540402835291602001916113bb565b820191906000526020600020905b81548152906001019060200180831161139e57829003601f168201915b5050505050905084565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060606002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561143d57fe5b9060005260206000209060040201600201546002600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561149d57fe5b9060005260206000209060040201600101546002600360008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156114fd57fe5b9060005260206000209060040201600301808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115a35780601f10611578576101008083540402835291602001916115a3565b820191906000526020600020905b81548152906001019060200180831161158657829003601f168201915b505050505090509250925092509193909250565b6000806000806000600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549350600084141561161357600094506116c6565b61162784600161200d90919063ffffffff16565b80935081945082955050505082151561164357600094506116c6565b8573ffffffffffffffffffffffffffffffffffffffff1660028581548110151561166957fe5b906000526020600020906004020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156116c157600094506116c6565b600194505b50505050919050565b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561173057600080fd5b611739876115b7565b15151561174557600080fd5b61175086600061209f565b80935081945082955050505082151561176857600080fd5b60026080604051908101604052808973ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001888152602001878152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155606082015181600301908051906020019061184592919061256f565b50505050611869826001600280549050038360016123ce909392919063ffffffff16565b50600160028054905003600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508673ffffffffffffffffffffffffffffffffffffffff167f7f7407b5db92d6c46dfaeac45e133f38df1f1202c4d96a5811e5d5c3fcd2a4eb60405160405180910390a2600193505050509392505050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561196557600080fd5b61196e846115b7565b151561197957600080fd5b6002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156119c757fe5b9060005260206000209060040201600101549050600115156119ea858584611cb0565b15151415156119f857600080fd5b8373ffffffffffffffffffffffffffffffffffffffff167f06a2b555e47dc4c403c86b0c6eef7fe53ff5ab40b8b42ff78a599f22de9904d6846040518082815260200191505060405180910390a2600191505092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515611aac57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515611ae857600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600080821480611bbd5750611bbb838361244f565b155b15611bcb5760009050611c8e565b611c2d83846000016000858152602001908152602001600020600080151515158152602001908152602001600020548560000160008681526020019081526020016000206000600115151515815260200190815260200160002054600161250a565b8260000160008381526020019081526020016000206000801515151581526020019081526020016000206000905582600001600083815260200190815260200160002060006001151515158152602001908152602001600020600090558190505b92915050565b60008183019050828110151515611ca757fe5b80905092915050565b600080600080611d09600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546001611ba690919063ffffffff16565b50611d14868661209f565b809350819450829550505050821515611d2c57600080fd5b6080604051908101604052808873ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018781526020016002600360008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515611dae57fe5b90600052602060002090600402016003018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611e535780601f10611e2857610100808354040283529160200191611e53565b820191906000526020600020905b815481529060010190602001808311611e3657829003601f168201915b50505050508152506002600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515611ea957fe5b906000526020600020906004020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155604082015181600201556060820151816003019080519060200190611f2e92919061256f565b50905050611f8982600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548360016123ce909392919063ffffffff16565b50600193505050509392505050565b6000828211151515611fa657fe5b818303905092915050565b600080611fbe858561244f565b1515611fd35760008080905091509150612005565b600185600001600086815260200190815260200160002060008515151515815260200190815260200160002054915091505b935093915050565b600080600061201c858561244f565b1515612038576000806000819150809050925092509250612098565b60018560000160008681526020019081526020016000206000801515151581526020019081526020016000205486600001600087815260200190815260200160002060006001151515158152602001908152602001600020549250925092505b9250925092565b6000806000806000806120bf6000600180611fb19092919063ffffffff16565b80935081945050508215806120d45750600082145b156120ea576001600060019550955095506123c4565b876002838154811015156120fa57fe5b9060005260206000209060040201600201541015612123576001600060019550955095506123c4565b8760028381548110151561213357fe5b90600052602060002090600402016002015414801561217257508660028381548110151561215d57fe5b90600052602060002090600402016001015411155b15612188576001600060019550955095506123c4565b8190505b6000821415156123b5576121ac82600180611fb19092919063ffffffff16565b80935081945050508215156121cb5760018160019550955095506123c4565b876002828154811015156121db57fe5b9060005260206000209060040201600201541115612290578760028381548110151561220357fe5b906000526020600020906004020160020154101561222b5760018160019550955095506123c4565b8760028381548110151561223b57fe5b90600052602060002090600402016002015414801561227a57508660028381548110151561226557fe5b90600052602060002090600402016001015411155b1561228f5760018160019550955095506123c4565b5b876002828154811015156122a057fe5b90600052602060002090600402016002015414156123ad57876002838154811015156122c857fe5b9060005260206000209060040201600201541080156123075750866002828154811015156122f257fe5b90600052602060002090600402016001015410155b1561231c5760018160019550955095506123c4565b8760028381548110151561232c57fe5b90600052602060002090600402016002015414801561236b57508660028281548110151561235657fe5b90600052602060002090600402016001015410155b801561239757508660028381548110151561238257fe5b90600052602060002090600402016001015411155b156123ac5760018160019550955095506123c4565b5b81905061218c565b60008060008191509550955095505b5050509250925092565b6000806123db868561244f565b1580156123ee57506123ed868661244f565b5b156124415785600001600086815260200190815260200160002060008415151515815260200190815260200160002054905061242c8686868661250a565b6124388685838661250a565b60019150612446565b600091505b50949350505050565b600080836000016000848152602001908152602001600020600080151515158152602001908152602001600020541480156124b5575060008360000160008481526020019081526020016000206000600115151515815260200190815260200160002054145b156124ff5781836000016000808152602001908152602001600020600060011515151581526020019081526020016000205414156124f65760019050612504565b60009050612504565b600190505b92915050565b828460000160008481526020019081526020016000206000831515151515815260200190815260200160002081905550818460000160008581526020019081526020016000206000831515151581526020019081526020016000208190555050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106125b057805160ff19168380011785556125de565b828001600101855582156125de579182015b828111156125dd5782518255916020019190600101906125c2565b5b5090506125eb919061266f565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061263057805160ff191683800117855561265e565b8280016001018555821561265e579182015b8281111561265d578251825591602001919060010190612642565b5b50905061266b919061266f565b5090565b61269191905b8082111561268d576000816000905550600101612675565b5090565b905600a165627a7a72305820a1e7cac4a71ac7be1e1f9f256f0e41acb4b0bba3962438c51f5fd6e8f90a56220029";

    private Trustee(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Trustee(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<LogAddTrusteeEventResponse> getLogAddTrusteeEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogAddTrustee", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogAddTrusteeEventResponse> responses = new ArrayList<LogAddTrusteeEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogAddTrusteeEventResponse typedResponse = new LogAddTrusteeEventResponse();
            typedResponse.ID = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogAddTrusteeEventResponse> logAddTrusteeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogAddTrustee", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogAddTrusteeEventResponse>() {
            @Override
            public LogAddTrusteeEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogAddTrusteeEventResponse typedResponse = new LogAddTrusteeEventResponse();
                typedResponse.ID = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogRemoveTrusteeEventResponse> getLogRemoveTrusteeEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogRemoveTrustee", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogRemoveTrusteeEventResponse> responses = new ArrayList<LogRemoveTrusteeEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogRemoveTrusteeEventResponse typedResponse = new LogRemoveTrusteeEventResponse();
            typedResponse.ID = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogRemoveTrusteeEventResponse> logRemoveTrusteeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogRemoveTrustee", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogRemoveTrusteeEventResponse>() {
            @Override
            public LogRemoveTrusteeEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogRemoveTrusteeEventResponse typedResponse = new LogRemoveTrusteeEventResponse();
                typedResponse.ID = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogGetTrusteesEventResponse> getLogGetTrusteesEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogGetTrustees", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogGetTrusteesEventResponse> responses = new ArrayList<LogGetTrusteesEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogGetTrusteesEventResponse typedResponse = new LogGetTrusteesEventResponse();
            typedResponse.trustees = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogGetTrusteesEventResponse> logGetTrusteesEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogGetTrustees", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogGetTrusteesEventResponse>() {
            @Override
            public LogGetTrusteesEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogGetTrusteesEventResponse typedResponse = new LogGetTrusteesEventResponse();
                typedResponse.trustees = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogIncreaseDepositEventResponse> getLogIncreaseDepositEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogIncreaseDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogIncreaseDepositEventResponse> responses = new ArrayList<LogIncreaseDepositEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogIncreaseDepositEventResponse typedResponse = new LogIncreaseDepositEventResponse();
            typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogIncreaseDepositEventResponse> logIncreaseDepositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogIncreaseDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogIncreaseDepositEventResponse>() {
            @Override
            public LogIncreaseDepositEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogIncreaseDepositEventResponse typedResponse = new LogIncreaseDepositEventResponse();
                typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogDecreaseDepositEventResponse> getLogDecreaseDepositEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogDecreaseDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogDecreaseDepositEventResponse> responses = new ArrayList<LogDecreaseDepositEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogDecreaseDepositEventResponse typedResponse = new LogDecreaseDepositEventResponse();
            typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogDecreaseDepositEventResponse> logDecreaseDepositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogDecreaseDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogDecreaseDepositEventResponse>() {
            @Override
            public LogDecreaseDepositEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogDecreaseDepositEventResponse typedResponse = new LogDecreaseDepositEventResponse();
                typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogUpdateTrusteeFameEventResponse> getLogUpdateTrusteeFameEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogUpdateTrusteeFame", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogUpdateTrusteeFameEventResponse> responses = new ArrayList<LogUpdateTrusteeFameEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogUpdateTrusteeFameEventResponse typedResponse = new LogUpdateTrusteeFameEventResponse();
            typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newFame = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogUpdateTrusteeFameEventResponse> logUpdateTrusteeFameEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogUpdateTrusteeFame", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogUpdateTrusteeFameEventResponse>() {
            @Override
            public LogUpdateTrusteeFameEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogUpdateTrusteeFameEventResponse typedResponse = new LogUpdateTrusteeFameEventResponse();
                typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newFame = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogUpdateTrusteePublicKeyEventResponse> getLogUpdateTrusteePublicKeyEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogUpdateTrusteePublicKey", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogUpdateTrusteePublicKeyEventResponse> responses = new ArrayList<LogUpdateTrusteePublicKeyEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogUpdateTrusteePublicKeyEventResponse typedResponse = new LogUpdateTrusteePublicKeyEventResponse();
            typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newPublicKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogUpdateTrusteePublicKeyEventResponse> logUpdateTrusteePublicKeyEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogUpdateTrusteePublicKey", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogUpdateTrusteePublicKeyEventResponse>() {
            @Override
            public LogUpdateTrusteePublicKeyEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogUpdateTrusteePublicKeyEventResponse typedResponse = new LogUpdateTrusteePublicKeyEventResponse();
                typedResponse.trustee = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newPublicKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<OwnershipRenouncedEventResponse> getOwnershipRenouncedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipRenounced", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<OwnershipRenouncedEventResponse> responses = new ArrayList<OwnershipRenouncedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipRenouncedEventResponse> ownershipRenouncedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipRenounced", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipRenouncedEventResponse>() {
            @Override
            public OwnershipRenouncedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> removeTrustee(String ID) {
        Function function = new Function(
                "removeTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> increaseDeposit(BigInteger weiValue) {
        Function function = new Function(
                "increaseDeposit", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> decreaseDeposit(BigInteger amount) {
        Function function = new Function(
                "decreaseDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updatePublicKey(String newKey) {
        Function function = new Function(
                "updatePublicKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(newKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getTrustees(BigInteger number) {
        Function function = new Function(
                "getTrustees", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> IDIndex(String param0) {
        Function function = new Function("IDIndex", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        Function function = new Function(
                "renounceOwnership", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple4<String, BigInteger, BigInteger, String>> trustees(BigInteger param0) {
        final Function function = new Function("trustees", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple4<String, BigInteger, BigInteger, String>>(
                new Callable<Tuple4<String, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple4<String, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple4<String, BigInteger, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<String> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<BigInteger, BigInteger, String>> getTrusteeInfo(String who) {
        final Function function = new Function("getTrusteeInfo", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<BigInteger, BigInteger, String>>(
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> isTrustee(String who) {
        Function function = new Function("isTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> addTrustee(String ID, BigInteger fame, String publicKey) {
        Function function = new Function(
                "addTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.generated.Uint256(fame), 
                new org.web3j.abi.datatypes.Utf8String(publicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateTrusteeFame(String ID, BigInteger newFame) {
        Function function = new Function(
                "updateTrusteeFame", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.generated.Uint256(newFame)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        Function function = new Function(
                "transferOwnership", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Trustee> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Trustee.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Trustee> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Trustee.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Trustee load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trustee(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Trustee load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trustee(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class LogAddTrusteeEventResponse {
        public String ID;
    }

    public static class LogRemoveTrusteeEventResponse {
        public String ID;
    }

    public static class LogGetTrusteesEventResponse {
        public List<String> trustees;
    }

    public static class LogIncreaseDepositEventResponse {
        public String trustee;

        public BigInteger amount;
    }

    public static class LogDecreaseDepositEventResponse {
        public String trustee;

        public BigInteger amount;
    }

    public static class LogUpdateTrusteeFameEventResponse {
        public String trustee;

        public BigInteger newFame;
    }

    public static class LogUpdateTrusteePublicKeyEventResponse {
        public String trustee;

        public String newPublicKey;
    }

    public static class OwnershipRenouncedEventResponse {
        public String previousOwner;
    }

    public static class OwnershipTransferredEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
