
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
    private static final String BINARY = "60806040523480156200001157600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506002608060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016000815260200160206040519081016040528060008152508152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155604082015181600201556060820151816003019080519060200190620001439291906200014d565b50505050620001fc565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200019057805160ff1916838001178555620001c1565b82800160010185558215620001c1579182015b82811115620001c0578251825591602001919060010190620001a3565b5b509050620001d09190620001d4565b5090565b620001f991905b80821115620001f5576000816000905550600101620001db565b5090565b90565b6126b3806200020c6000396000f3006080604052600436106100d0576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806303120506146100d557806305b050de146101305780630b2414d6146101525780631dc6fa5e1461018a5780632d16ddf31461020b578063545e85bd1461028d578063715018a6146102e4578063717d63a4146102fb5780638da5cb5b146103e2578063c214e6d514610439578063c784cd1714610503578063d56fe3cd1461055e578063d7ba3d8714610609578063f2fde38b1461066e575b600080fd5b3480156100e157600080fd5b50610116600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506106b1565b604051808215151515815260200191505060405180910390f35b6101386109de565b604051808215151515815260200191505060405180910390f35b61017060048036038101908080359060200190929190505050610b55565b604051808215151515815260200191505060405180910390f35b34801561019657600080fd5b506101f1600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610d2a565b604051808215151515815260200191505060405180910390f35b34801561021757600080fd5b5061023660048036038101908080359060200190929190505050610e70565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561027957808201518184015260208101905061025e565b505050509050019250505060405180910390f35b34801561029957600080fd5b506102ce600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506111a7565b6040518082815260200191505060405180910390f35b3480156102f057600080fd5b506102f96111bf565b005b34801561030757600080fd5b50610326600480360381019080803590602001909291905050506112c1565b604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156103a4578082015181840152602081019050610389565b50505050905090810190601f1680156103d15780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b3480156103ee57600080fd5b506103f76113b8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561044557600080fd5b5061047a600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506113dd565b6040518084815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156104c65780820151818401526020810190506104ab565b50505050905090810190601f1680156104f35780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561050f57600080fd5b50610544600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506115aa565b604051808215151515815260200191505060405180910390f35b34801561056a57600080fd5b506105ef600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506116c2565b604051808215151515815260200191505060405180910390f35b34801561061557600080fd5b50610654600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506118fa565b604051808215151515815260200191505060405180910390f35b34801561067a57600080fd5b506106af600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611a44565b005b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561070f57600080fd5b610718836115aa565b151561072357600080fd5b6002600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561077157fe5b90600052602060002090600402016001015490506107d8600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546001611b9990919063ffffffff16565b50608060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016000815260200160206040519081016040528060008152508152506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561087257fe5b906000526020600020906004020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002015560608201518160030190805190602001906108f7929190612562565b509050506000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506000811115610991578273ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f1935050505015801561098f573d6000803e3d6000fd5b505b8273ffffffffffffffffffffffffffffffffffffffff167f85c7d121e584c108defd0d2a0a6539823e13c0e4afeabce14ff5a4330236ca3060405160405180910390a26001915050919050565b6000806000806109ed336115aa565b15156109f857600080fd5b600034111515610a0757600080fd5b3392506002600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610a5857fe5b9060005260206000209060040201600201549150610ade346002600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610abe57fe5b906000526020600020906004020160010154611c8790919063ffffffff16565b905060011515610aef848484611ca3565b1515141515610afd57600080fd5b3373ffffffffffffffffffffffffffffffffffffffff167f6c5e7e4e203673af133e4db76883b962ff3faf782281e7bea6f78f2c7874dba0346040518082815260200191505060405180910390a26001935050505090565b6000806000806000610b66336115aa565b1515610b7157600080fd5b600086111515610b8057600080fd5b3393506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610bd157fe5b90600052602060002090600402016002015492506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610c3357fe5b9060005260206000209060040201600101549150818611151515610c5657600080fd5b610c698683611f8b90919063ffffffff16565b905060011515610c7a858584611ca3565b1515141515610c8857600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc879081150290604051600060405180830381858888f19350505050158015610cce573d6000803e3d6000fd5b503373ffffffffffffffffffffffffffffffffffffffff167fc712b41e0b9b7891d0d4ffebc1102bbe2f6ffa1ad4d8047cda3a6dd03ca6ed42876040518082815260200191505060405180910390a26001945050505050919050565b6000610d35336115aa565b1515610d4057600080fd5b816002600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610d8f57fe5b90600052602060002090600402016003019080519060200190610db39291906125e2565b503373ffffffffffffffffffffffffffffffffffffffff167f659b5f2849c0905a190866892969f81dbde54b9f354dea5212cce955104ef8bf836040518080602001828103825283818151815260200191508051906020019080838360005b83811015610e2d578082015181840152602081019050610e12565b50505050905090810190601f168015610e5a5780820380516001836020036101000a031916815260200191505b509250505060405180910390a260019050919050565b606060008060606000606060008088111515610e8b57600080fd5b87604051908082528060200260200182016040528015610eba5781602001602082028038833980820191505090505b509350610ed46000600180611fa49092919063ffffffff16565b8096508197505050851515610f1b576000604051908082528060200260200182016040528015610f135781602001602082028038833980820191505090505b50965061119c565b5b600085141515610fea57600285815481101515610f3557fe5b906000526020600020906004020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168484815181101515610f7557fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505082806001019350508783101515610fc757610fea565b610fdd85600180611fa49092919063ffffffff16565b8096508197505050610f1c565b8783101561112057826040519080825280602002602001820160405280156110215781602001602082028038833980820191505090505b509150600090505b828110156110a057838181518110151561103f57fe5b90602001906020020151828281518110151561105757fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508080600101915050611029565b7fe91db684c5662f94c81cc2ae7fbec373c3bfba4ce8fa5e377a877d0d41a81fa7826040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156111055780820151818401526020810190506110ea565b505050509050019250505060405180910390a181965061119c565b7fe91db684c5662f94c81cc2ae7fbec373c3bfba4ce8fa5e377a877d0d41a81fa7846040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561118557808201518184015260208101905061116a565b505050509050019250505060405180910390a18396505b505050505050919050565b60036020528060005260406000206000915090505481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561121a57600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482060405160405180910390a260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6002818154811015156112d057fe5b90600052602060002090600402016000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015490806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113ae5780601f10611383576101008083540402835291602001916113ae565b820191906000526020600020905b81548152906001019060200180831161139157829003601f168201915b5050505050905084565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060606002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561143057fe5b9060005260206000209060040201600201546002600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561149057fe5b9060005260206000209060040201600101546002600360008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156114f057fe5b9060005260206000209060040201600301808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115965780601f1061156b57610100808354040283529160200191611596565b820191906000526020600020905b81548152906001019060200180831161157957829003601f168201915b505050505090509250925092509193909250565b6000806000806000600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549350600084141561160657600094506116b9565b61161a84600161200090919063ffffffff16565b80935081945082955050505082151561163657600094506116b9565b8573ffffffffffffffffffffffffffffffffffffffff1660028581548110151561165c57fe5b906000526020600020906004020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156116b457600094506116b9565b600194505b50505050919050565b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561172357600080fd5b61172c876115aa565b15151561173857600080fd5b611743866000612092565b80935081945082955050505082151561175b57600080fd5b60026080604051908101604052808973ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001888152602001878152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155604082015181600201556060820151816003019080519060200190611838929190612562565b5050505061185c826001600280549050038360016123c1909392919063ffffffff16565b50600160028054905003600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508673ffffffffffffffffffffffffffffffffffffffff167f7f7407b5db92d6c46dfaeac45e133f38df1f1202c4d96a5811e5d5c3fcd2a4eb60405160405180910390a2600193505050509392505050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561195857600080fd5b611961846115aa565b151561196c57600080fd5b6002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156119ba57fe5b9060005260206000209060040201600101549050600115156119dd858584611ca3565b15151415156119eb57600080fd5b8373ffffffffffffffffffffffffffffffffffffffff167f06a2b555e47dc4c403c86b0c6eef7fe53ff5ab40b8b42ff78a599f22de9904d6846040518082815260200191505060405180910390a2600191505092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515611a9f57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515611adb57600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600080821480611bb05750611bae8383612442565b155b15611bbe5760009050611c81565b611c208384600001600085815260200190815260200160002060008015151515815260200190815260200160002054856000016000868152602001908152602001600020600060011515151581526020019081526020016000205460016124fd565b8260000160008381526020019081526020016000206000801515151581526020019081526020016000206000905582600001600083815260200190815260200160002060006001151515158152602001908152602001600020600090558190505b92915050565b60008183019050828110151515611c9a57fe5b80905092915050565b600080600080611cfc600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546001611b9990919063ffffffff16565b50611d078686612092565b809350819450829550505050821515611d1f57600080fd5b6080604051908101604052808873ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018781526020016002600360008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515611da157fe5b90600052602060002090600402016003018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611e465780601f10611e1b57610100808354040283529160200191611e46565b820191906000526020600020905b815481529060010190602001808311611e2957829003601f168201915b50505050508152506002600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515611e9c57fe5b906000526020600020906004020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155604082015181600201556060820151816003019080519060200190611f21929190612562565b50905050611f7c82600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548360016123c1909392919063ffffffff16565b50600193505050509392505050565b6000828211151515611f9957fe5b818303905092915050565b600080611fb18585612442565b1515611fc65760008080905091509150611ff8565b600185600001600086815260200190815260200160002060008515151515815260200190815260200160002054915091505b935093915050565b600080600061200f8585612442565b151561202b57600080600081915080905092509250925061208b565b60018560000160008681526020019081526020016000206000801515151581526020019081526020016000205486600001600087815260200190815260200160002060006001151515158152602001908152602001600020549250925092505b9250925092565b6000806000806000806120b26000600180611fa49092919063ffffffff16565b80935081945050508215806120c75750600082145b156120dd576001600060019550955095506123b7565b876002838154811015156120ed57fe5b9060005260206000209060040201600201541015612116576001600060019550955095506123b7565b8760028381548110151561212657fe5b90600052602060002090600402016002015414801561216557508660028381548110151561215057fe5b90600052602060002090600402016001015411155b1561217b576001600060019550955095506123b7565b8190505b6000821415156123a85761219f82600180611fa49092919063ffffffff16565b80935081945050508215156121be5760018160019550955095506123b7565b876002828154811015156121ce57fe5b906000526020600020906004020160020154111561228357876002838154811015156121f657fe5b906000526020600020906004020160020154101561221e5760018160019550955095506123b7565b8760028381548110151561222e57fe5b90600052602060002090600402016002015414801561226d57508660028381548110151561225857fe5b90600052602060002090600402016001015411155b156122825760018160019550955095506123b7565b5b8760028281548110151561229357fe5b90600052602060002090600402016002015414156123a057876002838154811015156122bb57fe5b9060005260206000209060040201600201541080156122fa5750866002828154811015156122e557fe5b90600052602060002090600402016001015410155b1561230f5760018160019550955095506123b7565b8760028381548110151561231f57fe5b90600052602060002090600402016002015414801561235e57508660028281548110151561234957fe5b90600052602060002090600402016001015410155b801561238a57508660028381548110151561237557fe5b90600052602060002090600402016001015411155b1561239f5760018160019550955095506123b7565b5b81905061217f565b60008060008191509550955095505b5050509250925092565b6000806123ce8685612442565b1580156123e157506123e08686612442565b5b156124345785600001600086815260200190815260200160002060008415151515815260200190815260200160002054905061241f868686866124fd565b61242b868583866124fd565b60019150612439565b600091505b50949350505050565b600080836000016000848152602001908152602001600020600080151515158152602001908152602001600020541480156124a8575060008360000160008481526020019081526020016000206000600115151515815260200190815260200160002054145b156124f25781836000016000808152602001908152602001600020600060011515151581526020019081526020016000205414156124e957600190506124f7565b600090506124f7565b600190505b92915050565b828460000160008481526020019081526020016000206000831515151515815260200190815260200160002081905550818460000160008581526020019081526020016000206000831515151581526020019081526020016000208190555050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106125a357805160ff19168380011785556125d1565b828001600101855582156125d1579182015b828111156125d05782518255916020019190600101906125b5565b5b5090506125de9190612662565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061262357805160ff1916838001178555612651565b82800160010185558215612651579182015b82811115612650578251825591602001919060010190612635565b5b50905061265e9190612662565b5090565b61268491905b80821115612680576000816000905550600101612668565b5090565b905600a165627a7a7230582033d440b5365f409f9b4f06cc3a8ba325b64267f0f69a68e5e5e7e73f413cbe6d0029";

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

    public RemoteCall<TransactionReceipt> decreaseDeposit(BigInteger amount, BigInteger weiValue) {
        Function function = new Function(
                "decreaseDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
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
