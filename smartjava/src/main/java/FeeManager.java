
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
    private static final String BINARY = "608060405234801561001057600080fd5b50604051604080611fdf8339810180604052810190808051906020019092919080519060200190929190505050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141515156100b957600080fd5b6000811115156100c857600080fd5b81600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550806002819055505050611ebe806101216000396000f3006080604052600436106100db576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b16586e146100e05780630d5125f81461013b5780634cc88ed1146101f05780635b6a1d0314610255578063715018a61461028e5780638da5cb5b146102a5578063978bbdb9146102fc578063bcf0cc4a14610327578063d51780ec14610360578063f1230615146103bb578063f2fde38b14610412578063f52706a014610455578063f6f66483146104b0578063f82ede3b14610507578063ffa640d81461056c575b600080fd5b3480156100ec57600080fd5b50610121600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506105c3565b604051808215151515815260200191505060405180910390f35b34801561014757600080fd5b506101d660048036038101908080359060200190929190803560ff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080602002602001604051908101604052809392919081815260200183836020028082843782019150505050505091929192905050506106e9565b604051808215151515815260200191505060405180910390f35b3480156101fc57600080fd5b5061023b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610af8565b604051808215151515815260200191505060405180910390f35b34801561026157600080fd5b5061026a610e24565b6040518082600181111561027a57fe5b60ff16815260200191505060405180910390f35b34801561029a57600080fd5b506102a3610e2d565b005b3480156102b157600080fd5b506102ba610f2f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561030857600080fd5b50610311610f54565b6040518082815260200191505060405180910390f35b34801561033357600080fd5b5061033c610f5a565b6040518082600181111561034c57fe5b60ff16815260200191505060405180910390f35b34801561036c57600080fd5b506103a1600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610f62565b604051808215151515815260200191505060405180910390f35b3480156103c757600080fd5b506103d06112c9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561041e57600080fd5b50610453600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506112ef565b005b34801561046157600080fd5b50610496600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611444565b604051808215151515815260200191505060405180910390f35b3480156104bc57600080fd5b506104c561156a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561051357600080fd5b50610552600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050611590565b604051808215151515815260200191505060405180910390f35b34801561057857600080fd5b50610581611ab8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561062057600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561065c57600080fd5b81600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff167f3f138d6831b42721bc3bee3903ddb9b557ad5eb3535d1fbd5591a79262d469e360405160405180910390a260019050919050565b60008084600060018111156106fa57fe5b81600181111561070657fe5b1480610727575060018081111561071957fe5b81600181111561072557fe5b145b151561073257600080fd5b600073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561079057600080fd5b600073ffffffffffffffffffffffffffffffffffffffff16600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515156107ee57600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806108975750600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b15156108a257600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff1614151515610947576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f7573657220616464726573732073686f756c642062652076616c69640000000081525060200191505060405180910390fd5b600084511115156109e6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001807f6e756d626572206f662073657276696365206e6f6465732f757365727320736881526020017f6f756c6420626967676572207468616e207a65726f000000000000000000000081525060400191505060405180910390fd5b6109f1868551611ade565b915060006001811115610a0057fe5b866001811115610a0c57fe5b1415610a2057610a1e87868685611afd565b505b600180811115610a2c57fe5b866001811115610a3857fe5b1415610a4c57610a4a87868685611c94565b505b7f5e78445e244ee3c24ad233a4718333f3a1941b1d6388a361aa88490429ed81198787848760405180858152602001846001811115610a8757fe5b60ff16815260200183815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015610ad4578082015181840152602081019050610ab9565b505050509050019550505050505060405180910390a1600192505050949350505050565b60008073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515610b5757600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610bb357600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614151515610bef57600080fd5b600082111515610bfe57600080fd5b6000600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141515610c4c57600080fd5b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd8430856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b158015610d4557600080fd5b505af1158015610d59573d6000803e3d6000fd5b505050506040513d6020811015610d6f57600080fd5b81019080805190602001909291905050501515610d8857fe5b81600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff167fdc13dd24c3f9092c265a93536bffcdd382d8c3c1482a49e30bdd4a31e43964b6836040518082815260200191505060405180910390a26001905092915050565b60006001905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610e8857600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482060405160405180910390a260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025481565b600080905090565b60008073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515610fc157600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561101d57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561105957600080fd5b6000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541115156110a757600080fd5b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb83600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b1580156111ab57600080fd5b505af11580156111bf573d6000803e3d6000fd5b505050506040513d60208110156111d557600080fd5b810190808051906020019092919050505015156111ee57fe5b8173ffffffffffffffffffffffffffffffffffffffff167f4bc04eeac71717bda5558028d2cb88f390f731327270fa2a803fe9e479bd4854600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546040518082815260200191505060405180910390a26000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060019050919050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561134a57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415151561138657600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156114a157600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141515156114dd57600080fd5b81600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff167f0782e4c3948edc00c1b675275a8869c8596945696b70345d51f49666d3ae752160405160405180910390a260019050919050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600080600073ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515156115f157600080fd5b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561164d57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff161415151561168957600080fd5b60008311151561169857600080fd5b600360008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050808311156118dd57600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd85306117358588611e2b90919063ffffffff16565b6040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b1580156117ed57600080fd5b505af1158015611801573d6000803e3d6000fd5b505050506040513d602081101561181757600080fd5b8101908080519060200190929190505050151561183057fe5b82600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff167fdc13dd24c3f9092c265a93536bffcdd382d8c3c1482a49e30bdd4a31e43964b66118bf8386611e2b90919063ffffffff16565b6040518082815260200191505060405180910390a260019150611ab1565b80831015611aac57600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb856119378685611e2b90919063ffffffff16565b6040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b1580156119bc57600080fd5b505af11580156119d0573d6000803e3d6000fd5b505050506040513d60208110156119e657600080fd5b810190808051906020019092919050505015156119ff57fe5b82600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff167f4bc04eeac71717bda5558028d2cb88f390f731327270fa2a803fe9e479bd4854611a8e8584611e2b90919063ffffffff16565b6040518082815260200191505060405180910390a260019150611ab1565b600091505b5092915050565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000611af582600254611e4490919063ffffffff16565b905092915050565b6000806000611b16855185611e7c90919063ffffffff16565b9150600090505b8451811015611c8657600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd878784815181101515611b7357fe5b90602001906020020151856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b158015611c3657600080fd5b505af1158015611c4a573d6000803e3d6000fd5b505050506040513d6020811015611c6057600080fd5b81019080805190602001909291905050501515611c7957fe5b8080600101915050611b1d565b600192505050949350505050565b6000806000611cad855185611e7c90919063ffffffff16565b9150600090505b8451811015611e1d57600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd878784815181101515611d0a57fe5b90602001906020020151856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b158015611dcd57600080fd5b505af1158015611de1573d6000803e3d6000fd5b505050506040513d6020811015611df757600080fd5b81019080805190602001909291905050501515611e1057fe5b8080600101915050611cb4565b600192505050949350505050565b6000828211151515611e3957fe5b818303905092915050565b600080831415611e575760009050611e76565b8183029050818382811515611e6857fe5b04141515611e7257fe5b8090505b92915050565b60008183811515611e8957fe5b049050929150505600a165627a7a7230582016f16d6fa53cc9d473a1ea0908876878b61401cf05798cf61968a886b076e89b0029";

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

    public List<WithDrawDepositEventResponse> getWithDrawDepositEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WithDrawDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithDrawDepositEventResponse> responses = new ArrayList<WithDrawDepositEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithDrawDepositEventResponse typedResponse = new WithDrawDepositEventResponse();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.deposit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithDrawDepositEventResponse> withDrawDepositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WithDrawDeposit", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithDrawDepositEventResponse>() {
            @Override
            public WithDrawDepositEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithDrawDepositEventResponse typedResponse = new WithDrawDepositEventResponse();
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

    public RemoteCall<TransactionReceipt> payDeposit(String user, BigInteger deposit) {
        Function function = new Function(
                "payDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Uint256(deposit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getArbitrationServiceType() {
        Function function = new Function("getArbitrationServiceType", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<TransactionReceipt> withDrawDeposit(String user) {
        Function function = new Function(
                "withDrawDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> GET() {
        Function function = new Function("GET", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> updateToNewDepsoit(String user, BigInteger _newDeposit) {
        Function function = new Function(
                "updateToNewDepsoit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.generated.Uint256(_newDeposit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> tradeContract() {
        Function function = new Function("tradeContract", 
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

    public static class PayDepositEventResponse {
        public String user;

        public BigInteger deposit;
    }

    public static class WithDrawDepositEventResponse {
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
