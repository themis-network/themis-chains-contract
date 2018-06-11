
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
import org.web3j.abi.datatypes.generated.Uint32;
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
    private static final String BINARY = "60806040523480156200001157600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506002608060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001600063ffffffff16815260200160206040519081016040528060008152508152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020160006101000a81548163ffffffff021916908363ffffffff16021790555060608201518160030190805190602001906200016692919062000170565b505050506200021f565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620001b357805160ff1916838001178555620001e4565b82800160010185558215620001e4579182015b82811115620001e3578251825591602001919060010190620001c6565b5b509050620001f39190620001f7565b5090565b6200021c91905b8082111562000218576000816000905550600101620001fe565b5090565b90565b612876806200022f6000396000f3006080604052600436106100d0576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063023b59df146100d5578063031205061461018657806305b050de146101e15780630b2414d6146102035780631dc6fa5e146102485780632d16ddf3146102c9578063545e85bd1461034b57806359e2e3ee146103a2578063715018a61461040d578063717d63a4146104245780638da5cb5b14610517578063c214e6d51461056e578063c784cd1714610638578063f2fde38b14610693575b600080fd5b3480156100e157600080fd5b5061016c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803563ffffffff169060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506106d6565b604051808215151515815260200191505060405180910390f35b34801561019257600080fd5b506101c7600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610931565b604051808215151515815260200191505060405180910390f35b6101e9610c81565b604051808215151515815260200191505060405180910390f35b34801561020f57600080fd5b5061022e60048036038101908080359060200190929190505050610e08565b604051808215151515815260200191505060405180910390f35b34801561025457600080fd5b506102af600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610fed565b604051808215151515815260200191505060405180910390f35b3480156102d557600080fd5b506102f460048036038101908080359060200190929190505050611133565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561033757808201518184015260208101905061031c565b505050509050019250505060405180910390f35b34801561035757600080fd5b5061038c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061146a565b6040518082815260200191505060405180910390f35b3480156103ae57600080fd5b506103f3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803563ffffffff169060200190929190505050611482565b604051808215151515815260200191505060405180910390f35b34801561041957600080fd5b506104226115d8565b005b34801561043057600080fd5b5061044f600480360381019080803590602001909291905050506116da565b604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018363ffffffff1663ffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156104d95780820151818401526020810190506104be565b50505050905090810190601f1680156105065780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34801561052357600080fd5b5061052c6117e1565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561057a57600080fd5b506105af600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611806565b6040518084815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156105fb5780820151818401526020810190506105e0565b50505050905090810190601f1680156106285780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561064457600080fd5b50610679600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506119ec565b604051808215151515815260200191505060405180910390f35b34801561069f57600080fd5b506106d4600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611b04565b005b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561073757600080fd5b610740876119ec565b15151561074c57600080fd5b610757866000611c59565b80935081945082955050505082151561076f57600080fd5b60026080604051908101604052808973ffffffffffffffffffffffffffffffffffffffff168152602001600081526020018863ffffffff168152602001878152509080600181540180825580915050906001820390600052602060002090600402016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020160006101000a81548163ffffffff021916908363ffffffff160217905550606082015181600301908051906020019061086f929190612725565b5050505061089382600160028054905003836001612068909392919063ffffffff16565b50600160028054905003600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508673ffffffffffffffffffffffffffffffffffffffff167f7f7407b5db92d6c46dfaeac45e133f38df1f1202c4d96a5811e5d5c3fcd2a4eb60405160405180910390a2600193505050509392505050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561098f57600080fd5b610998836119ec565b15156109a357600080fd5b6002600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156109f157fe5b9060005260206000209060040201600101549050610a58600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205460016120e990919063ffffffff16565b50608060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001600063ffffffff16815260200160206040519081016040528060008152508152506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610af857fe5b906000526020600020906004020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020160006101000a81548163ffffffff021916908363ffffffff1602179055506060820151816003019080519060200190610b9a929190612725565b509050506000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506000811115610c34578273ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f19350505050158015610c32573d6000803e3d6000fd5b505b8273ffffffffffffffffffffffffffffffffffffffff167f85c7d121e584c108defd0d2a0a6539823e13c0e4afeabce14ff5a4330236ca3060405160405180910390a26001915050919050565b600080600080610c90336119ec565b1515610c9b57600080fd5b600034111515610caa57600080fd5b3392506002600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610cfb57fe5b906000526020600020906004020160020160009054906101000a900463ffffffff169150610d91346002600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610d7157fe5b9060005260206000209060040201600101546121d790919063ffffffff16565b905060011515610da28484846121f3565b1515141515610db057600080fd5b3373ffffffffffffffffffffffffffffffffffffffff167f6c5e7e4e203673af133e4db76883b962ff3faf782281e7bea6f78f2c7874dba0346040518082815260200191505060405180910390a26001935050505090565b6000806000806000610e19336119ec565b1515610e2457600080fd5b600086111515610e3357600080fd5b3393506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610e8457fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1692506002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054815481101515610ef657fe5b9060005260206000209060040201600101549150818611151515610f1957600080fd5b610f2c86836124fe90919063ffffffff16565b905060011515610f3d8585846121f3565b1515141515610f4b57600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc879081150290604051600060405180830381858888f19350505050158015610f91573d6000803e3d6000fd5b503373ffffffffffffffffffffffffffffffffffffffff167fc712b41e0b9b7891d0d4ffebc1102bbe2f6ffa1ad4d8047cda3a6dd03ca6ed42876040518082815260200191505060405180910390a26001945050505050919050565b6000610ff8336119ec565b151561100357600080fd5b816002600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561105257fe5b906000526020600020906004020160030190805190602001906110769291906127a5565b503373ffffffffffffffffffffffffffffffffffffffff167f659b5f2849c0905a190866892969f81dbde54b9f354dea5212cce955104ef8bf836040518080602001828103825283818151815260200191508051906020019080838360005b838110156110f05780820151818401526020810190506110d5565b50505050905090810190601f16801561111d5780820380516001836020036101000a031916815260200191505b509250505060405180910390a260019050919050565b60606000806060600060606000808811151561114e57600080fd5b8760405190808252806020026020018201604052801561117d5781602001602082028038833980820191505090505b50935061119760006001806125179092919063ffffffff16565b80965081975050508515156111de5760006040519080825280602002602001820160405280156111d65781602001602082028038833980820191505090505b50965061145f565b5b6000851415156112ad576002858154811015156111f857fe5b906000526020600020906004020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16848481518110151561123857fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508280600101935050878310151561128a576112ad565b6112a0856001806125179092919063ffffffff16565b80965081975050506111df565b878310156113e357826040519080825280602002602001820160405280156112e45781602001602082028038833980820191505090505b509150600090505b8281101561136357838181518110151561130257fe5b90602001906020020151828281518110151561131a57fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505080806001019150506112ec565b7fe91db684c5662f94c81cc2ae7fbec373c3bfba4ce8fa5e377a877d0d41a81fa7826040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156113c85780820151818401526020810190506113ad565b505050509050019250505060405180910390a181965061145f565b7fe91db684c5662f94c81cc2ae7fbec373c3bfba4ce8fa5e377a877d0d41a81fa7846040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561144857808201518184015260208101905061142d565b505050509050019250505060405180910390a18396505b505050505050919050565b60036020528060005260406000206000915090505481565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156114e057600080fd5b6114e9846119ec565b15156114f457600080fd5b6002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561154257fe5b9060005260206000209060040201600101549050600115156115658585846121f3565b151514151561157357600080fd5b8373ffffffffffffffffffffffffffffffffffffffff167fff0dab668dad6b4bee4a8805c07f97701c95538d5c03c1516f930f8057e2324584604051808263ffffffff1663ffffffff16815260200191505060405180910390a2600191505092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561163357600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482060405160405180910390a260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6002818154811015156116e957fe5b90600052602060002090600402016000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020160009054906101000a900463ffffffff1690806003018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156117d75780601f106117ac576101008083540402835291602001916117d7565b820191906000526020600020905b8154815290600101906020018083116117ba57829003601f168201915b5050505050905084565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060606002600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561185957fe5b906000526020600020906004020160020160009054906101000a900463ffffffff166002600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156118c957fe5b9060005260206000209060040201600101546002600360008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481548110151561192957fe5b90600052602060002090600402016003018263ffffffff169250808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156119d85780601f106119ad576101008083540402835291602001916119d8565b820191906000526020600020905b8154815290600101906020018083116119bb57829003601f168201915b505050505090509250925092509193909250565b6000806000806000600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205493506000841415611a485760009450611afb565b611a5c84600161257390919063ffffffff16565b809350819450829550505050821515611a785760009450611afb565b8573ffffffffffffffffffffffffffffffffffffffff16600285815481101515611a9e57fe5b906000526020600020906004020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611af65760009450611afb565b600194505b50505050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515611b5f57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515611b9b57600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600080600080600080611c7960006001806125179092919063ffffffff16565b8093508194505050821580611c8e5750600082145b15611ca45760016000600195509550955061205e565b8763ffffffff16600283815481101515611cba57fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff161015611cf95760016000600195509550955061205e565b8763ffffffff16600283815481101515611d0f57fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff16148015611d64575086600283815481101515611d4f57fe5b90600052602060002090600402016001015411155b15611d7a5760016000600195509550955061205e565b8190505b60008214151561204f57611d9e826001806125179092919063ffffffff16565b8093508194505050821515611dbd57600181600195509550955061205e565b8763ffffffff16600282815481101515611dd357fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff161115611ed6578763ffffffff16600283815481101515611e1757fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff161015611e5557600181600195509550955061205e565b8763ffffffff16600283815481101515611e6b57fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff16148015611ec0575086600283815481101515611eab57fe5b90600052602060002090600402016001015411155b15611ed557600181600195509550955061205e565b5b8763ffffffff16600282815481101515611eec57fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff161415612047578763ffffffff16600283815481101515611f3057fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff16108015611f85575086600282815481101515611f7057fe5b90600052602060002090600402016001015410155b15611f9a57600181600195509550955061205e565b8763ffffffff16600283815481101515611fb057fe5b906000526020600020906004020160020160009054906101000a900463ffffffff1663ffffffff16148015612005575086600282815481101515611ff057fe5b90600052602060002090600402016001015410155b801561203157508660028381548110151561201c57fe5b90600052602060002090600402016001015411155b1561204657600181600195509550955061205e565b5b819050611d7e565b60008060008191509550955095505b5050509250925092565b6000806120758685612605565b15801561208857506120878686612605565b5b156120db578560000160008681526020019081526020016000206000841515151581526020019081526020016000205490506120c6868686866126c0565b6120d2868583866126c0565b600191506120e0565b600091505b50949350505050565b60008082148061210057506120fe8383612605565b155b1561210e57600090506121d1565b6121708384600001600085815260200190815260200160002060008015151515815260200190815260200160002054856000016000868152602001908152602001600020600060011515151581526020019081526020016000205460016126c0565b8260000160008381526020019081526020016000206000801515151581526020019081526020016000206000905582600001600083815260200190815260200160002060006001151515158152602001908152602001600020600090558190505b92915050565b600081830190508281101515156121ea57fe5b80905092915050565b60008060008061224c600360008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205460016120e990919063ffffffff16565b506122578686611c59565b80935081945082955050505082151561226f57600080fd5b6080604051908101604052808873ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018763ffffffff1681526020016002600360008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156122f757fe5b90600052602060002090600402016003018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561239c5780601f106123715761010080835404028352916020019161239c565b820191906000526020600020905b81548152906001019060200180831161237f57829003601f168201915b50505050508152506002600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548154811015156123f257fe5b906000526020600020906004020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020160006101000a81548163ffffffff021916908363ffffffff1602179055506060820151816003019080519060200190612494929190612725565b509050506124ef82600360008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054836001612068909392919063ffffffff16565b50600193505050509392505050565b600082821115151561250c57fe5b818303905092915050565b6000806125248585612605565b1515612539576000808090509150915061256b565b600185600001600086815260200190815260200160002060008515151515815260200190815260200160002054915091505b935093915050565b60008060006125828585612605565b151561259e5760008060008191508090509250925092506125fe565b60018560000160008681526020019081526020016000206000801515151581526020019081526020016000205486600001600087815260200190815260200160002060006001151515158152602001908152602001600020549250925092505b9250925092565b6000808360000160008481526020019081526020016000206000801515151581526020019081526020016000205414801561266b575060008360000160008481526020019081526020016000206000600115151515815260200190815260200160002054145b156126b55781836000016000808152602001908152602001600020600060011515151581526020019081526020016000205414156126ac57600190506126ba565b600090506126ba565b600190505b92915050565b828460000160008481526020019081526020016000206000831515151515815260200190815260200160002081905550818460000160008581526020019081526020016000206000831515151581526020019081526020016000208190555050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061276657805160ff1916838001178555612794565b82800160010185558215612794579182015b82811115612793578251825591602001919060010190612778565b5b5090506127a19190612825565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106127e657805160ff1916838001178555612814565b82800160010185558215612814579182015b828111156128135782518255916020019190600101906127f8565b5b5090506128219190612825565b5090565b61284791905b8082111561284357600081600090555060010161282b565b5090565b905600a165627a7a72305820eda1a8db0293aec8d1d480237eab31e9c01c8f4695906a9ceea4a66d8ba3cc660029";

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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
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

    public RemoteCall<TransactionReceipt> addTrustee(String ID, BigInteger fame, String publicKey) {
        Function function = new Function(
                "addTrustee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.generated.Uint32(fame), 
                new org.web3j.abi.datatypes.Utf8String(publicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteCall<TransactionReceipt> updateTrusteeFame(String ID, BigInteger newFame) {
        Function function = new Function(
                "updateTrusteeFame", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.generated.Uint32(newFame)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Utf8String>() {}));
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
