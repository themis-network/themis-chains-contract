
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
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
    private static final String BINARY = "6080604052670de0b6b3a7640000600155670de0b6b3a764000060025534801561002857600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611beb806100786000396000f3006080604052600436106100f1576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b16586e146100f65780630d5125f8146101515780632287b180146101f95780632bda23531461024757806351e417001461027257806359f3ef05146102cd5780635b6a1d031461031b5780636757ef9c14610354578063715018a6146103a35780638da5cb5b146103ba578063b9e5981514610411578063bcf0cc4a1461043c578063c3fb1a6e14610475578063f2fde38b146104da578063f52706a01461051d578063f6f6648314610578578063ffa640d8146105cf575b600080fd5b34801561010257600080fd5b50610137600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610626565b604051808215151515815260200191505060405180910390f35b6101df60048036038101908080359060200190929190803560ff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019082018035906020019080806020026020016040519081016040528093929190818152602001838360200280828437820191505050505050919291929050505061074c565b604051808215151515815260200191505060405180910390f35b61022d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610bca565b604051808215151515815260200191505060405180910390f35b34801561025357600080fd5b5061025c610dbc565b6040518082815260200191505060405180910390f35b34801561027e57600080fd5b506102b3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610dc2565b604051808215151515815260200191505060405180910390f35b610301600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611068565b604051808215151515815260200191505060405180910390f35b34801561032757600080fd5b5061033061125d565b6040518082600181111561034057fe5b60ff16815260200191505060405180910390f35b34801561036057600080fd5b506103896004803603810190808035906020019092919080359060200190929190505050611266565b604051808215151515815260200191505060405180910390f35b3480156103af57600080fd5b506103b86112f5565b005b3480156103c657600080fd5b506103cf6113f7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561041d57600080fd5b5061042661141c565b6040518082815260200191505060405180910390f35b34801561044857600080fd5b50610451611422565b6040518082600181111561046157fe5b60ff16815260200191505060405180910390f35b34801561048157600080fd5b506104c0600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061142a565b604051808215151515815260200191505060405180910390f35b3480156104e657600080fd5b5061051b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506116b2565b005b34801561052957600080fd5b5061055e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611807565b604051808215151515815260200191505060405180910390f35b34801561058457600080fd5b5061058d61192d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156105db57600080fd5b506105e4611953565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561068357600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141515156106bf57600080fd5b81600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff167f3f138d6831b42721bc3bee3903ddb9b557ad5eb3535d1fbd5591a79262d469e360405160405180910390a260019050919050565b600080846000600181111561075d57fe5b81600181111561076957fe5b148061078a575060018081111561077c57fe5b81600181111561078857fe5b145b151561079557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515156107f357600080fd5b600073ffffffffffffffffffffffffffffffffffffffff16600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561085157600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806108fa5750600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b151561090557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff16141515156109aa576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f7573657220616464726573732073686f756c642062652076616c69640000000081525060200191505060405180910390fd5b60008451111515610a49576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001807f6e756d626572206f662073657276696365206e6f6465732f757365727320736881526020017f6f756c6420626967676572207468616e207a65726f000000000000000000000081525060400191505060405180910390fd5b610a54868551611979565b9150813410151515610a6557600080fd5b60006001811115610a7257fe5b866001811115610a7e57fe5b1415610a9157610a8f8585846119fa565b505b600180811115610a9d57fe5b866001811115610aa957fe5b1415610abc57610aba858584611a9b565b505b81341115610b1e578473ffffffffffffffffffffffffffffffffffffffff166108fc610af18434611b3c90919063ffffffff16565b9081150290604051600060405180830381858888f19350505050158015610b1c573d6000803e3d6000fd5b505b7f5e78445e244ee3c24ad233a4718333f3a1941b1d6388a361aa88490429ed81198787848760405180858152602001846001811115610b5957fe5b60ff16815260200183815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015610ba6578082015181840152602081019050610b8b565b505050509050019550505050505060405180910390a1600192505050949350505050565b60008073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515610c2957600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610c8557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614151515610cc157600080fd5b600034111515610cd057600080fd5b610d2234600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054611b5590919063ffffffff16565b600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508173ffffffffffffffffffffffffffffffffffffffff167fdc13dd24c3f9092c265a93536bffcdd382d8c3c1482a49e30bdd4a31e43964b6346040518082815260200191505060405180910390a260019050919050565b60015481565b60008073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515610e2157600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610e7d57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614151515610eb957600080fd5b6000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054111515610f0757600080fd5b8173ffffffffffffffffffffffffffffffffffffffff166108fc600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549081150290604051600060405180830381858888f19350505050158015610f8c573d6000803e3d6000fd5b508173ffffffffffffffffffffffffffffffffffffffff167f195ddc41d185a27fe901831dcad44dd85716c95be78b1d71aa42393697966d40600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546040518082815260200191505060405180910390a26000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060019050919050565b600080600073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515156110c957600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561112557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415151561116157600080fd5b60003411151561117057600080fd5b6000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541415156111be57600080fd5b34905080600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff167fdc13dd24c3f9092c265a93536bffcdd382d8c3c1482a49e30bdd4a31e43964b6826040518082815260200191505060405180910390a26001915050919050565b60006001905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156112c357600080fd5b6000831115156112d257600080fd5b6000821115156112e157600080fd5b826001819055508160028190555092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561135057600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482060405160405180910390a260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025481565b600080905090565b60008073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561148957600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156114e557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415151561152157600080fd5b60008211151561153057600080fd5b600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054821115151561157e57600080fd5b8273ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f193505050501580156115c4573d6000803e3d6000fd5b5061161782600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054611b3c90919063ffffffff16565b600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff167f195ddc41d185a27fe901831dcad44dd85716c95be78b1d71aa42393697966d40836040518082815260200191505060405180910390a26001905092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561170d57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415151561174957600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561186457600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141515156118a057600080fd5b81600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff167f0782e4c3948edc00c1b675275a8869c8596945696b70345d51f49666d3ae752160405160405180910390a260019050919050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600080600181111561198757fe5b83600181111561199357fe5b14156119b5576119ae82600154611b7190919063ffffffff16565b90506119f4565b6001808111156119c157fe5b8360018111156119cd57fe5b14156119ef576119e882600254611b7190919063ffffffff16565b90506119f4565b600090505b92915050565b6000806000611a13855185611ba990919063ffffffff16565b9150600090505b8451811015611a8e578481815181101515611a3157fe5b9060200190602002015173ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f19350505050158015611a80573d6000803e3d6000fd5b508080600101915050611a1a565b6001925050509392505050565b6000806000611ab4855185611ba990919063ffffffff16565b9150600090505b8451811015611b2f578481815181101515611ad257fe5b9060200190602002015173ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f19350505050158015611b21573d6000803e3d6000fd5b508080600101915050611abb565b6001925050509392505050565b6000828211151515611b4a57fe5b818303905092915050565b60008183019050828110151515611b6857fe5b80905092915050565b600080831415611b845760009050611ba3565b8183029050818382811515611b9557fe5b04141515611b9f57fe5b8090505b92915050565b60008183811515611bb657fe5b049050929150505600a165627a7a7230582023751905c6fb4cd7c5bb157feb6cbe37264e793f7c6c64e83d48403d0afb183a0029";

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

    public List<PayDepositEventResponse> getPayDepositEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("PayDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<PayDepositEventResponse> responses = new ArrayList<PayDepositEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            PayDepositEventResponse typedResponse = new PayDepositEventResponse();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.deposit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PayDepositEventResponse> payDepositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("PayDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, PayDepositEventResponse>() {
            @Override
            public PayDepositEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                PayDepositEventResponse typedResponse = new PayDepositEventResponse();
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.deposit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<WithdrawDepositEventResponse> getWithdrawDepositEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WithdrawDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithdrawDepositEventResponse> responses = new ArrayList<WithdrawDepositEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithdrawDepositEventResponse typedResponse = new WithdrawDepositEventResponse();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.deposit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawDepositEventResponse> withdrawDepositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WithdrawDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawDepositEventResponse>() {
            @Override
            public WithdrawDepositEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithdrawDepositEventResponse typedResponse = new WithdrawDepositEventResponse();
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.deposit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<UpdateHosterContractEventResponse> getUpdateHosterContractEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UpdateHosterContract", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UpdateHosterContractEventResponse> responses = new ArrayList<UpdateHosterContractEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UpdateHosterContractEventResponse typedResponse = new UpdateHosterContractEventResponse();
            typedResponse.hoster = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UpdateHosterContractEventResponse> updateHosterContractEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UpdateHosterContract", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UpdateHosterContractEventResponse>() {
            @Override
            public UpdateHosterContractEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UpdateHosterContractEventResponse typedResponse = new UpdateHosterContractEventResponse();
                typedResponse.hoster = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<UpdateTradeContractEventResponse> getUpdateTradeContractEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UpdateTradeContract", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UpdateTradeContractEventResponse> responses = new ArrayList<UpdateTradeContractEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UpdateTradeContractEventResponse typedResponse = new UpdateTradeContractEventResponse();
            typedResponse.trade = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UpdateTradeContractEventResponse> updateTradeContractEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UpdateTradeContract", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UpdateTradeContractEventResponse>() {
            @Override
            public UpdateTradeContractEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UpdateTradeContractEventResponse typedResponse = new UpdateTradeContractEventResponse();
                typedResponse.trade = (String) eventValues.getIndexedValues().get(0).getValue();
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

    public RemoteCall<TransactionReceipt> updateTradeContract(String _newTrade) {
        Function function = new Function(
                "updateTradeContract", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newTrade)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> payFee(BigInteger orderID, BigInteger serviceType, String user, List<String> serviceNodes, BigInteger weiValue) {
        Function function = new Function(
                "payFee", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.generated.Uint8(serviceType), 
                new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(serviceNodes, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> increaseDeposit(String user, BigInteger weiValue) {
        Function function = new Function(
                "increaseDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> hostFeeRate() {
        Function function = new Function("hostFeeRate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> withdrawDeposit(String user) {
        Function function = new Function(
                "withdrawDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> payDeposit(String user, BigInteger weiValue) {
        Function function = new Function(
                "payDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> getArbitrationServiceType() {
        Function function = new Function("getArbitrationServiceType", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> updateFeeRate(BigInteger _newHostFeeRate, BigInteger _newArbitrationFeeRate) {
        Function function = new Function(
                "updateFeeRate", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newHostFeeRate), 
                new org.web3j.abi.datatypes.generated.Uint256(_newArbitrationFeeRate)), 
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

    public RemoteCall<String> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> arbitrationFeeRate() {
        Function function = new Function("arbitrationFeeRate", 
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

    public RemoteCall<TransactionReceipt> decreaseDeposit(String user, BigInteger amount) {
        Function function = new Function(
                "decreaseDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
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

    public RemoteCall<TransactionReceipt> updateHosterContract(String _newHoster) {
        Function function = new Function(
                "updateHosterContract", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newHoster)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> hosterContract() {
        Function function = new Function("hosterContract", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> tradeContract() {
        Function function = new Function("tradeContract", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<FeeManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FeeManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<FeeManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FeeManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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

    public static class PayDepositEventResponse {
        public String user;

        public BigInteger deposit;
    }

    public static class WithdrawDepositEventResponse {
        public String user;

        public BigInteger deposit;
    }

    public static class UpdateHosterContractEventResponse {
        public String hoster;
    }

    public static class UpdateTradeContractEventResponse {
        public String trade;
    }

    public static class OwnershipRenouncedEventResponse {
        public String previousOwner;
    }

    public static class OwnershipTransferredEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
