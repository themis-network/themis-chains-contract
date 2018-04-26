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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public final class Trade extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516040806119eb83398101604052805160209091015160008054600160a060020a03191633600160a060020a03908116919091179091558216151561005757600080fd5b600160a060020a038116151561006c57600080fd5b60018054600160a060020a03938416600160a060020a03199182161790915560028054929093169116179055611944806100a76000396000f3006080604052600436106100c15763ffffffff60e060020a6000350416631d54c95381146100c65780632e3cb22a1461010457806334a733ec1461019b5780633c8047a1146102345780635bf608b81461025857806362fee90c1461028c5780636da647e4146102f457806379f25afa146103185780637d3566441461033c578063a666e9b11461035a578063a6f9dae114610372578063add2f13314610395578063d6a9de51146103f3578063e370e0ac1461040b578063f5947d3814610469575b600080fd5b3480156100d257600080fd5b506100f0600435600160a060020a0360243581169060443516610500565b604080519115158252519081900360200190f35b34801561011057600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100f0958335953695604494919390910191908190840183828082843750506040805187358901803560208181028481018201909552818452989b9a9989019892975090820195509350839250850190849080828437509497506106199650505050505050565b3480156101a757600080fd5b506101bf600435600160a060020a03602435166107ce565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101f95781810151838201526020016101e1565b50505050905090810190601f1680156102265780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561024057600080fd5b506101bf600435600160a060020a0360243516610810565b34801561026457600080fd5b50610270600435610825565b60408051600160a060020a039092168252519081900360200190f35b34801561029857600080fd5b506102a4600435610840565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102e05781810151838201526020016102c8565b505050509050019250505060405180910390f35b34801561030057600080fd5b506101bf600435600160a060020a0360243516610853565b34801561032457600080fd5b506101bf600435600160a060020a036024351661088e565b34801561034857600080fd5b506100f060043560ff6024351661089c565b34801561036657600080fd5b506102a4600435610942565b34801561037e57600080fd5b50610393600160a060020a036004351661094f565b005b3480156103a157600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100f09583359536956044949193909101919081908401838280828437509497506109d89650505050505050565b3480156103ff57600080fd5b50610270600435610b13565b34801561041757600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100f0958335953695604494919390910191908190840183828082843750949750610b319650505050505050565b34801561047557600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100f0958335953695604494919390910191908190840183828082843750506040805187358901803560208181028481018201909552818452989b9a998901989297509082019550935083925085019084908082843750949750610baf9650505050505050565b6000805433600160a060020a0390811691161461051c57600080fd5b600084815260036020526040902054600160a060020a03161561053e57600080fd5b600084815260036020526040902060010154600160a060020a03161561056357600080fd5b600160a060020a038316151561057857600080fd5b600160a060020a038216151561058d57600080fd5b6000848152600360209081526040918290208054600160a060020a0380881673ffffffffffffffffffffffffffffffffffffffff19928316811784556001909301805491881691909216811790915583518881529351909391927f991b1120fd176b76a9276a3d603900c298054b2b33f0e950562975eb071bdb9f928290030190a35060019392505050565b600083815260036020526040812060010154849033600160a060020a0390811691161461064557600080fd5b600154604080517f5b6a1d030000000000000000000000000000000000000000000000000000000081529051600160a060020a0390921691630d5125f89188918491635b6a1d039160048083019260209291908290030181600087803b1580156106ae57600080fd5b505af11580156106c2573d6000803e3d6000fd5b505050506040513d60208110156106d857600080fd5b505160405160e060020a63ffffffff851602815260048101838152339189919060240184600181111561070757fe5b60ff16815260200183600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b8381101561076357818101518382015260200161074b565b5050505090500195505050505050602060405180830381600087803b15801561078b57600080fd5b505af115801561079f573d6000803e3d6000fd5b505050506040513d60208110156107b557600080fd5b506107c590508560008686610d54565b95945050505050565b600082815260036020526040902054606090839033600160a060020a039081169116146107fa57600080fd5b61080684600185610f83565b91505b5092915050565b606061081e8360018461105c565b9392505050565b600090815260036020526040902054600160a060020a031690565b606061084d8260016110f9565b92915050565b600082815260036020526040902060010154606090839033600160a060020a0390811691161461088257600080fd5b61080684600085610f83565b606061081e8360008461105c565b60008080808460018111156108ad57fe5b9150600090505b600086815260036020908152604080832085845260020190915290205481101561093857600086815260036020908152604080832085845260020190915290208054600160a060020a03331691908390811061090c57fe5b600091825260209091200154600160a060020a031614156109305760019250610938565b6001016108b4565b5090949350505050565b606061084d8260006110f9565b60005433600160a060020a0390811691161461096a57600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a038381169182179283905560408051939091168352602083019190915280517f9aecf86140d81442289f667eb72e1202a8fbb3478a686659952e145e853196569281900390910190a150565b6000826109e681600161089c565b15156109f157600080fd5b600260008581526003602052604090206007015460ff166002811115610a1357fe5b1415610a1e57600080fd5b6000848152600360205260408120610af191610aec9160040190805b81526020808201929092526040908101600090812033600160a060020a0316825283528190208054825160026001831615610100026000190190921691909104601f810185900485028201850190935282815292909190830182828015610ae25780601f10610ab757610100808354040283529160200191610ae2565b820191906000526020600020905b815481529060010190602001808311610ac557829003601f168201915b505050505061118b565b6111b1565b1515610afc57600080fd5b610b08846001856111b6565b506001949350505050565b600090815260036020526040902060010154600160a060020a031690565b600082610b3f81600061089c565b1515610b4a57600080fd5b600160008581526003602052604090206007015460ff166002811115610b6c57fe5b1415610b7757600080fd5b6000848152600360205260408120610b9891610aec91600401906001610a3a565b1515610ba357600080fd5b610b08846000856111b6565b600083815260036020526040812054849033600160a060020a03908116911614610bd857600080fd5b600154604080517f5b6a1d030000000000000000000000000000000000000000000000000000000081529051600160a060020a0390921691630d5125f89188918491635b6a1d039160048083019260209291908290030181600087803b158015610c4157600080fd5b505af1158015610c55573d6000803e3d6000fd5b505050506040513d6020811015610c6b57600080fd5b505160405160e060020a63ffffffff8516028152600481018381523391899190602401846001811115610c9a57fe5b60ff16815260200183600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015610cf6578181015183820152602001610cde565b5050505090500195505050505050602060405180830381600087803b158015610d1e57600080fd5b505af1158015610d32573d6000803e3d6000fd5b505050506040513d6020811015610d4857600080fd5b506107c5905085600186865b6000610d5e6117c7565b610d666117c7565b60008060006060610dab6040805190810160405280600181526020017f2c0000000000000000000000000000000000000000000000000000000000000081525061118b565b9550610db68961118b565b9450610dc8858763ffffffff61158616565b6001019350875184141515610ddc57600080fd5b896001811115610de857fe5b60008c815260036020908152604080832084845260020182529091208a51929550610e179290918b01906117de565b50600091505b83821015610eb157610e3d610e38868863ffffffff6115ec16565b6115ff565b905080600360008d815260200190815260200160002060030160008a85815181101515610e6657fe5b6020908102909101810151600160a060020a0316825281810192909252604090810160009081208782528352208251610ea59391929190910190611850565b50600190910190610e1d565b33600160a060020a03167f4491fee63e1b2614fdd79a3cb9ab4eaf6cf253b366ee5df2f03adabbcc54c85c8c8c8c60405180848152602001836001811115610ef557fe5b60ff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610f36578181015183820152602001610f1e565b50505050905090810190601f168015610f635780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a25060019a9950505050505050505050565b6000838152600360205260408120606091600490910190846001811115610fa657fe5b815260208082019290925260409081016000908120600160a060020a038616825283528190208054825160026001831615610100026000190190921691909104601f81018590048502820185019093528281529290919083018282801561104e5780601f106110235761010080835404028352916020019161104e565b820191906000526020600020905b81548152906001019060200180831161103157829003601f168201915b505050505090509392505050565b6000838152600360208181526040808420600160a060020a03861685529092019052812060609184600181111561108f57fe5b815260208082019290925260409081016000208054825160026001831615610100026000190190921691909104601f81018590048502820185019093528281529290919083018282801561104e5780601f106110235761010080835404028352916020019161104e565b600082815260036020526040812060609160029091019083600181111561111c57fe5b815260200190815260200160002080548060200260200160405190810160405280929190818152602001828054801561117e57602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311611160575b5050505050905092915050565b6111936117c7565b50604080518082019091528151815260209182019181019190915290565b511590565b6002546040517fe5f50f000000000000000000000000000000000000000000000000000000000081526020600482018181528451602484015284516000948594600160a060020a039091169363e5f50f0093889390928392604490910191908501908083838b5b8381101561123557818101518382015260200161121d565b50505050905090810190601f1680156112625780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15801561128157600080fd5b505af1158015611295573d6000803e3d6000fd5b505050506040513d60208110156112ab57600080fd5b505115156112b857600080fd5b8360018111156112c457fe5b60008681526003602090815260408083208484526004018252808320600160a060020a033316845282529182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845293945061135293610aec9392830182828015610ae25780601f10610ab757610100808354040283529160200191610ae2565b1561137b5760008581526003602090815260408083208484526005019091529020805460010190555b60008581526003602090815260408083208484526004018252808320600160a060020a0333168452825290912084516113b692860190611850565b50600260009054906101000a9004600160a060020a0316600160a060020a03166387f6af246040518163ffffffff1660e060020a028152600401602060405180830381600087803b15801561140a57600080fd5b505af115801561141e573d6000803e3d6000fd5b505050506040513d602081101561143457600080fd5b50516000868152600360209081526040808320858452600501909152902054106114ba57600084600181111561146657fe5b1415611489576000858152600360205260409020600701805460ff191660021790555b600184600181111561149757fe5b14156114ba576000858152600360205260409020600701805460ff191660011790555b33600160a060020a03167f24538d8d2b02982080c2a529355335e2ed33e94f5f6f2e9a6be5e54a64af2d8d868686604051808481526020018360018111156114fe57fe5b60ff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561153f578181015183820152602001611527565b50505050905090810190601f16801561156c5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a2506001949350505050565b60008082600001516115aa8560000151866020015186600001518760200151611652565b0190505b835160208501510181116108095782516020808601518651918601516001909501946115e4929185039091039084908490611652565b0190506115ae565b6115f46117c7565b610809838383611712565b606080600083600001516040519080825280601f01601f191660200182016040528015611636578160200160208202803883390190505b5091506020820190506108098185602001518660000151611783565b600083818080808080808c8b116116fc5760208b116116c55760018b60200360080260020a03196001029550858a511694508a8d8d010393508588511692505b8285146116bd578388106116aa578c8c019850611702565b8780600101985050858851169250611692565b879850611702565b8a8a209150600096505b8a8d0387116116fc5750898720818114156116ec57879850611702565b60019788019796909601956116cf565b8c8c0198505b5050505050505050949350505050565b61171a6117c7565b60006117388560000151866020015186600001518760200151611652565b60208087018051918601919091528051820385528651905191925001811415611764576000855261177a565b8351835186519101900385528351810160208601525b50909392505050565b60005b602082106117a8578251845260209384019390920191601f1990910190611786565b50905182516020929092036101000a6000190180199091169116179052565b604080518082019091526000808252602082015290565b828054828255906000526020600020908101928215611840579160200282015b82811115611840578251825473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039091161782556020909201916001909101906117fe565b5061184c9291506118ca565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061189157805160ff19168380011785556118be565b828001600101855582156118be579182015b828111156118be5782518255916020019190600101906118a3565b5061184c9291506118fe565b6118fb91905b8082111561184c57805473ffffffffffffffffffffffffffffffffffffffff191681556001016118d0565b90565b6118fb91905b8082111561184c57600081556001016119045600a165627a7a72305820a1e0db828ccaf02144a204cfa9947642000f86abec618074b8c2eb9193625ec60029";

    private Trade(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Trade(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<CreateOrderEventResponse> getCreateOrderEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("CreateOrder", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<CreateOrderEventResponse> responses = new ArrayList<CreateOrderEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            CreateOrderEventResponse typedResponse = new CreateOrderEventResponse();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CreateOrderEventResponse> createOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("CreateOrder", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, CreateOrderEventResponse>() {
            @Override
            public CreateOrderEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                CreateOrderEventResponse typedResponse = new CreateOrderEventResponse();
                typedResponse.buyer = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.seller = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<UploadEncryptedShardEventResponse> getUploadEncryptedShardEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UploadEncryptedShard", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UploadEncryptedShardEventResponse> responses = new ArrayList<UploadEncryptedShardEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UploadEncryptedShardEventResponse typedResponse = new UploadEncryptedShardEventResponse();
            typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.shards = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UploadEncryptedShardEventResponse> uploadEncryptedShardEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UploadEncryptedShard", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UploadEncryptedShardEventResponse>() {
            @Override
            public UploadEncryptedShardEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UploadEncryptedShardEventResponse typedResponse = new UploadEncryptedShardEventResponse();
                typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.shards = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<RecoverPrivKeyEventResponse> getRecoverPrivKeyEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("RecoverPrivKey", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RecoverPrivKeyEventResponse> responses = new ArrayList<RecoverPrivKeyEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RecoverPrivKeyEventResponse typedResponse = new RecoverPrivKeyEventResponse();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.privKey = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RecoverPrivKeyEventResponse> recoverPrivKeyEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("RecoverPrivKey", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RecoverPrivKeyEventResponse>() {
            @Override
            public RecoverPrivKeyEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RecoverPrivKeyEventResponse typedResponse = new RecoverPrivKeyEventResponse();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.privKey = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<UploadDecryptedShardsEventResponse> getUploadDecryptedShardsEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UploadDecryptedShards", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UploadDecryptedShardsEventResponse> responses = new ArrayList<UploadDecryptedShardsEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UploadDecryptedShardsEventResponse typedResponse = new UploadDecryptedShardsEventResponse();
            typedResponse.hoster = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.decryptedShard = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UploadDecryptedShardsEventResponse> uploadDecryptedShardsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UploadDecryptedShards", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UploadDecryptedShardsEventResponse>() {
            @Override
            public UploadDecryptedShardsEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UploadDecryptedShardsEventResponse typedResponse = new UploadDecryptedShardsEventResponse();
                typedResponse.hoster = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.decryptedShard = (String) eventValues.getNonIndexedValues().get(2).getValue();
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

    public RemoteCall<TransactionReceipt> createNewTradeOrder(BigInteger _orderID, String _buyer, String _seller) {
        Function function = new Function(
                "createNewTradeOrder", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Address(_buyer), 
                new org.web3j.abi.datatypes.Address(_seller)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> uploadBuyerShardFromSeller(BigInteger _orderID, String _shard, List<String> _hosterID) {
        Function function = new Function(
                "uploadBuyerShardFromSeller", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Utf8String(_shard), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_hosterID, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getSellerDecryptedShard(BigInteger _orderID, String _hoster) {
        Function function = new Function("getSellerDecryptedShard", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Address(_hoster)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getSellerShardByHosterID(BigInteger _orderID, String _hosterID) {
        Function function = new Function("getSellerShardByHosterID", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Address(_hosterID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getBuyer(BigInteger _orderID) {
        Function function = new Function("getBuyer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<List<String>> getSellerShardHosters(BigInteger _orderID) {
        Function function = new Function("getSellerShardHosters", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function, List<String>.class);
    }

    public RemoteCall<String> getBuyerDecryptedShard(BigInteger _orderID, String _hoster) {
        Function function = new Function("getBuyerDecryptedShard", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Address(_hoster)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getBuyerShardByHosterID(BigInteger _orderID, String _hosterID) {
        Function function = new Function("getBuyerShardByHosterID", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Address(_hosterID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isUserHoster(BigInteger _orderID, BigInteger _userType) {
        Function function = new Function("isUserHoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.generated.Uint8(_userType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<List<String>> getBuyerShardHosters(BigInteger _orderID) {
        Function function = new Function("getBuyerShardHosters", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function, List<String>.class);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        Function function = new Function(
                "changeOwner", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> uploadSellerDecryptedShard(BigInteger _orderID, String _decryptedShard) {
        Function function = new Function(
                "uploadSellerDecryptedShard", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Utf8String(_decryptedShard)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getSeller(BigInteger _orderID) {
        Function function = new Function("getSeller", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> uploadBuyerDecryptedShard(BigInteger _orderID, String _decryptedShard) {
        Function function = new Function(
                "uploadBuyerDecryptedShard", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Utf8String(_decryptedShard)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> uploadSellerShardFromBuyer(BigInteger _orderID, String _shard, List<String> _hosterID) {
        Function function = new Function(
                "uploadSellerShardFromBuyer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.Utf8String(_shard), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_hosterID, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Trade> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _feeManager, String _vss) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_feeManager), 
                new org.web3j.abi.datatypes.Address(_vss)));
        return deployRemoteCall(Trade.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Trade> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _feeManager, String _vss) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_feeManager), 
                new org.web3j.abi.datatypes.Address(_vss)));
        return deployRemoteCall(Trade.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Trade load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trade(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Trade load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trade(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class CreateOrderEventResponse {
        public String buyer;

        public String seller;

        public BigInteger orderID;
    }

    public static class UploadEncryptedShardEventResponse {
        public String who;

        public BigInteger orderID;

        public BigInteger userType;

        public String shards;
    }

    public static class RecoverPrivKeyEventResponse {
        public BigInteger orderID;

        public BigInteger userType;

        public String privKey;
    }

    public static class UploadDecryptedShardsEventResponse {
        public String hoster;

        public BigInteger orderID;

        public BigInteger userType;

        public String decryptedShard;
    }

    public static class ChangeOwnerEventResponse {
        public String oriOwner;

        public String newOwner;
    }
}
