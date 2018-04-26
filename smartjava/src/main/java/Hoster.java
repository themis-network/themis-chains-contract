package o;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
import org.web3j.tuples.generated.Tuple2;
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
public final class Hoster extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160208061220a833981016040525160008054600160a060020a03191633600160a060020a03908116919091179091558116151561005057600080fd5b60038054600160a060020a03928316600160a060020a03199182161790915560408051808201909152600080825260208201818152600480546001810182559252915160029091027f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b8101805492909516919093161790925590517f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19c9091015561210b806100ff6000396000f3006080604052600436106100c15763ffffffff60e060020a6000350416631566007581146100c65780634102efb1146100fe578063415d58ac1461016c57806360b10d38146101a7578063665ae96f146101cb5780636d32fea7146101ec57806388b8dcde1461020d5780639549981b146102255780639857518814610246578063a4bb65fc14610267578063a6f9dae1146102d5578063a87430ba146102f8578063b8a0caa8146103c6578063e1653ae114610431578063f7a2799a1461049d575b600080fd5b3480156100d257600080fd5b506100ea600160a060020a03600435166024356104d0565b604080519115158252519081900360200190f35b34801561010a57600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526100ea948235600160a060020a03169460248035953695946064949201919081908401838280828437509497505050923560ff1693506106f792505050565b34801561017857600080fd5b5061018460043561083d565b60408051600160a060020a03909316835260208301919091528051918290030190f35b3480156101b357600080fd5b506100ea600160a060020a0360043516602435610873565b3480156101d757600080fd5b506100ea600160a060020a03600435166109e5565b3480156101f857600080fd5b506100ea600160a060020a0360043516610a1e565b34801561021957600080fd5b506100ea600435610ae1565b34801561023157600080fd5b506100ea600160a060020a0360043516610c6c565b34801561025257600080fd5b506100ea600160a060020a0360043516610e4d565b34801561027357600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526100ea948235600160a060020a03169460248035953695946064949201919081908401838280828437509497505050923560ff169350610eed92505050565b3480156102e157600080fd5b506102f6600160a060020a036004351661101e565b005b34801561030457600080fd5b50610319600160a060020a036004351661109a565b6040518085600160a060020a0316600160a060020a031681526020018481526020018060200183600181111561034b57fe5b60ff168152602001828103825284818151815260200191508051906020019080838360005b83811015610388578181015183820152602001610370565b50505050905090810190601f1680156103b55780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b3480156103d257600080fd5b506103e1600435602435611156565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561041d578181015183820152602001610405565b505050509050019250505060405180910390f35b34801561043d57600080fd5b50604080516020601f6064356004818101359283018490048402850184019095528184526100ea94600160a060020a0381351694602480359560443595369560849493019181908401838280828437509497506116fc9650505050505050565b3480156104a957600080fd5b506104be600160a060020a03600435166118a5565b60408051918252519081900360200190f35b6000805481908190819033600160a060020a039081169116146104f257600080fd5b6104fb866109e5565b151561050657600080fd5b600160a060020a0386166000908152600160208190526040909120015461052d90866118b7565b9194509250905082151561054057600080fd5b60408051808201909152600160a060020a0387811682526020820187815260048054600181018255600082905293517f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b60029586029081018054600160a060020a031916929095169190911790935590517f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19c90920191909155546105eb919084906000190184611cc0565b50600454600160a060020a0387166000908152600560209081526040808320600019948501905560018083529281902080840154600291820180548451968116156101000290970190961691909104601f81018490048402850184019092528184526106b6948b949193919290918301828280156106aa5780601f1061067f576101008083540402835291602001916106aa565b820191906000526020600020905b81548152906001019060200180831161068d57829003601f168201915b50505050506001610eed565b50604051600160a060020a038716907ffd20370bb6caa5b25972791cbb00e60bb688b3a31e8574a2c9d84b95db345f7f90600090a250600195945050505050565b6000805433600160a060020a0390811691161461071357600080fd5b81600081600181111561072257fe5b14806107395750600181600181111561073757fe5b145b151561074457600080fd5b600160a060020a038616151561075957600080fd5b600160a060020a03868116600090815260016020526040902054161561077e57600080fd5b600160a060020a03861660008181526001602081815260409092208054600160a060020a0319169093178355820187905585516107c19260020191870190611ffd565b50600160a060020a03861660009081526001602081905260409091206003018054859260ff199091169083818111156107f657fe5b0217905550604051600160a060020a038716907ffc0fbce0ce138a79db83eb10fdbb555b13ae1dd0e8f06c3e7aff42d1338a953990600090a2600191505b50949350505050565b600480548290811061084b57fe5b600091825260209091206002909102018054600190910154600160a060020a03909116915082565b60008054819033600160a060020a0390811691161461089157600080fd5b600160a060020a03841660009081526005602052604090205415156108b557600080fd5b600160a060020a0384166000908152600560205260409020546004805490919081106108dd57fe5b90600052602060002090600202016001015490506108fc848483611d29565b151560011461090a57600080fd5b600160a060020a0384166000908152600160208181526040928390206002908101805485519481161561010002600019011691909104601f81018390048302840183019094528383526109da9388938893909291908301828280156109b05780601f10610985576101008083540402835291602001916109b0565b820191906000526020600020905b81548152906001019060200180831161099357829003601f168201915b50505050600160a060020a03891660009081526001602052604090206003015460ff169050610eed565b506001949350505050565b6000600160a060020a03821615156109fc57600080fd5b50600160a060020a039081166000818152600160205260409020549091161490565b600160a060020a0381166000908152600560205260408120548190819081908190610a519060029063ffffffff611e3c16565b91955093509150831515610a685760009450610ad8565b50600160a060020a038516600090815260056020526040902054801515610a925760009450610ad8565b85600160a060020a0316600482815481101515610aab57fe5b6000918252602090912060029091020154600160a060020a031614610ad35760009450610ad8565b600194505b50505050919050565b600160a060020a033316600090815260056020526040812054600480548392908110610b0957fe5b600091825260209091206002909102015433600160a060020a03908116911614610b3257600080fd5b5033600160a060020a0381166000908152600560205260409020541515610b5857600080fd5b600160a060020a03811660009081526001602081905260409091200154610b8190829085611d29565b1515600114610b8f57600080fd5b600160a060020a038116600090815260016020818152604092839020808301546002918201805486519581161561010002600019011692909204601f8101849004840285018401909552848452610c629486949193919291830182828015610c385780601f10610c0d57610100808354040283529160200191610c38565b820191906000526020600020905b815481529060010190602001808311610c1b57829003601f168201915b50505050600160a060020a03861660009081526001602052604090206003015460ff169050610eed565b5060019392505050565b6000805433600160a060020a03908116911614610c8857600080fd5b600160a060020a0382161515610c9d57600080fd5b600160a060020a0382166000908152600560205260409020541515610cc157600080fd5b600160a060020a038216600090815260056020526040902054610cec9060029063ffffffff611e8f16565b5060408051808201825260008082526020808301829052600160a060020a0386168252600590529190912054600480549091908110610d2757fe5b60009182526020808320845160029384029091018054600160a060020a03928316600160a060020a03199091161781559482015160019586015586168352600581526040808420849055848252928390208085015490830180548551601f97821615610100026000190190911694909404958601839004830284018301909452848352610e10948794919392830182828015610e045780601f10610dd957610100808354040283529160200191610e04565b820191906000526020600020905b815481529060010190602001808311610de757829003601f168201915b50505050506000610eed565b50604051600160a060020a038316907f1b627a79aed3d0f3943be9aad626d0f7ee2ddb40c3afa36c0c55aad055ed8a2290600090a2506001919050565b6000805433600160a060020a03908116911614610e6957600080fd5b600160a060020a038216600090815260016020819052604082208054600160a060020a031916815590810182905590610ea5600283018261207b565b50600301805460ff19169055604051600160a060020a038316907fd9130ada26096344100635679bad0874686abf52ecb1d4a8b6eb7e116e69472e90600090a2506001919050565b6000805433600160a060020a03908116911614610f0957600080fd5b816000816001811115610f1857fe5b1480610f2f57506001816001811115610f2d57fe5b145b1515610f3a57600080fd5b600160a060020a0380871660008181526001602052604090205490911614610f6157600080fd5b600160a060020a03861660008181526001602081815260409092208054600160a060020a031916909317835582018790558551610fa49260020191870190611ffd565b50600160a060020a03861660009081526001602081905260409091206003018054859260ff19909116908381811115610fd957fe5b0217905550604051600160a060020a038716907f6fe90e519e1fd89b6dfb1f6eb0593deecf53f111f69d70848a13cafa7c37ee5890600090a250600195945050505050565b60005433600160a060020a0390811691161461103957600080fd5b60008054600160a060020a031916600160a060020a038381169182179283905560408051939091168352602083019190915280517f9aecf86140d81442289f667eb72e1202a8fbb3478a686659952e145e853196569281900390910190a150565b6001602081815260009283526040928390208054818401546002808401805488516101009882161598909802600019011691909104601f8101869004860287018601909752868652600160a060020a0390921695909492938301828280156111435780601f1061111857610100808354040283529160200191611143565b820191906000526020600020905b81548152906001019060200180831161112657829003601f168201915b5050506003909301549192505060ff1684565b6060600080606060006060600061116c336109e5565b151561117757600080fd5b6000881161118457600080fd5b876040519080825280602002602001820160405280156111ae578160200160208202803883390190505b5093506111c560026000600163ffffffff611f0e16565b90965094508515156111d9578396506116f0565b841561125e5760048054869081106111ed57fe5b60009182526020909120600290910201548451600160a060020a039091169085908590811061121857fe5b600160a060020a039092166020928302909101909101526001909201918783106112415761125e565b611254600286600163ffffffff611f0e16565b90965094506111d9565b878310156114eb5782604051908082528060200260200182016040528015611290578160200160208202803883390190505b509150600090505b828110156112e55783818151811015156112ae57fe5b9060200190602002015182828151811015156112c657fe5b600160a060020a03909216602092830290910190910152600101611298565b33600160a060020a03167f455b5bd17e849ff61c137a7e952379a7fde2a2df682be5e3c97943355eb05ecf8a846040518083815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015611358578181015183820152602001611340565b50505050905001935050505060405180910390a2600354604080517fbcf0cc4a0000000000000000000000000000000000000000000000000000000081529051600160a060020a0390921691630d5125f8918c91849163bcf0cc4a9160048083019260209291908290030181600087803b1580156113d557600080fd5b505af11580156113e9573d6000803e3d6000fd5b505050506040513d60208110156113ff57600080fd5b505160405160e060020a63ffffffff851602815260048101838152339188919060240184600181111561142e57fe5b60ff16815260200183600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b8381101561148a578181015183820152602001611472565b5050505090500195505050505050602060405180830381600087803b1580156114b257600080fd5b505af11580156114c6573d6000803e3d6000fd5b505050506040513d60208110156114dc57600080fd5b505115156114e657fe5b6116ec565b33600160a060020a03167f455b5bd17e849ff61c137a7e952379a7fde2a2df682be5e3c97943355eb05ecf8a866040518083815260200180602001828103825283818151815260200191508051906020019060200280838360005b8381101561155e578181015183820152602001611546565b50505050905001935050505060405180910390a2600354604080517fbcf0cc4a0000000000000000000000000000000000000000000000000000000081529051600160a060020a0390921691630d5125f8918c91849163bcf0cc4a9160048083019260209291908290030181600087803b1580156115db57600080fd5b505af11580156115ef573d6000803e3d6000fd5b505050506040513d602081101561160557600080fd5b505160405160e060020a63ffffffff85160281526004810183815233918a919060240184600181111561163457fe5b60ff16815260200183600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015611690578181015183820152602001611678565b5050505090500195505050505050602060405180830381600087803b1580156116b857600080fd5b505af11580156116cc573d6000803e3d6000fd5b505050506040513d60208110156116e257600080fd5b505115156116ec57fe5b8396505b50505050505092915050565b6000805481908190819033600160a060020a0390811691161461171e57600080fd5b600160a060020a038816151561173357600080fd5b600160a060020a0388166000908152600560205260409020541561175657600080fd5b61175f886109e5565b1561176957600080fd5b61177387876118b7565b9194509250905082151561178657600080fd5b60408051808201909152600160a060020a0389811682526020820188815260048054600181018255600082905293517f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b60029586029081018054600160a060020a031916929095169190911790935590517f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19c9092019190915554611831919084906000190184611cc0565b50600454600160a060020a0389166000908152600560205260409020600019909101905561186288888760016106f7565b50604051600160a060020a038916907fe3d4f633a486751e5e3e4990dc7f79b728e0d79b3db91f4c3c0d00be208b380e90600090a2506001979650505050505050565b60056020526000908152604090205481565b600080808080806118d1600282600163ffffffff611f0e16565b90935091508215806118e1575081155b156118f757600160006001955095509550611cb6565b876001600060048581548110151561190b57fe5b60009182526020808320600290920290910154600160a060020a03168352820192909252604001902060010154101561194f57600160006001955095509550611cb6565b876001600060048581548110151561196357fe5b60009182526020808320600290920290910154600160a060020a031683528201929092526040019020600101541480156119bc5750866004838154811015156119a857fe5b906000526020600020906002020160010154105b156119d257600160006001955095509550611cb6565b50805b8115611cab576119ee600283600163ffffffff611f0e16565b9093509150821515611a0a576001816001955095509550611cb6565b8760016000600484815481101515611a1e57fe5b60009182526020808320600290920290910154600160a060020a031683528201929092526040019020600101541115611b2b578760016000600485815481101515611a6557fe5b60009182526020808320600290920290910154600160a060020a031683528201929092526040019020600101541015611aa8576001816001955095509550611cb6565b8760016000600485815481101515611abc57fe5b60009182526020808320600290920290910154600160a060020a03168352820192909252604001902060010154148015611b16575086600483815481101515611b0157fe5b90600052602060002090600202016001015411155b15611b2b576001816001955095509550611cb6565b8760016000600484815481101515611b3f57fe5b60009182526020808320600290920290910154600160a060020a031683528201929092526040019020600101541415611ca4578760016000600485815481101515611b8657fe5b60009182526020808320600290920290910154600160a060020a03168352820192909252604001902060010154108015611be0575086600482815481101515611bcb57fe5b90600052602060002090600202016001015410155b15611bf5576001816001955095509550611cb6565b8760016000600485815481101515611c0957fe5b60009182526020808320600290920290910154600160a060020a03168352820192909252604001902060010154148015611c63575086600482815481101515611c4e57fe5b90600052602060002090600202016001015410155b8015611c8f575086600483815481101515611c7a57fe5b90600052602060002090600202016001015411155b15611ca4576001816001955095509550611cb6565b50806119d5565b600095508594508493505b5050509250925092565b600080611ccd8685611f54565b158015611cdf5750611cdf8686611f54565b15611d2057506000848152602086815260408083208515158452909152902054611d0b86868686611fcc565b611d1786858386611fcc565b60019150610834565b60009150610834565b600160a060020a0383166000908152600560205260408120548190819081901515611d5357600080fd5b600160a060020a038716600090815260056020526040902054611d7e9060029063ffffffff611e8f16565b50611d8986866118b7565b91945092509050821515611d9c57600080fd5b604080518082018252600160a060020a03891680825260208083018990526000918252600590529190912054600480549091908110611dd757fe5b60009182526020808320845160029384029091018054600160a060020a031916600160a060020a0392831617815594820151600190950194909455928a16825260059092526040902054611e2e9190849084611cc0565b506001979650505050505050565b6000806000611e4b8585611f54565b1515611e5f57506000915081905080611e88565b505050600081815260208381526040808320838052808352818420546001808652919093529220545b9250925092565b6000811580611ea55750611ea38383611f54565b155b15611eb257506000611f08565b6000828152602084815260408083208380529091528082205460018084529190922054611ee3928692909190611fcc565b5060008181526020838152604080832083805290915280822082905560018252812055805b92915050565b600080611f1b8585611f54565b1515611f2c57506000905080611f4c565b505060008281526020848152604080832084151584529091529020546001905b935093915050565b600081815260208381526040808320838052909152812054158015611f8f575060008281526020848152604080832060018452909152902054155b15611fc45760008080526020848152604080832060018452909152902054821415611fbc57506001611f08565b506000611f08565b506001611f08565b6000828152602085815260408083209315808452938252808320869055948252948552838120911581529352912055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061203e57805160ff191683800117855561206b565b8280016001018555821561206b579182015b8281111561206b578251825591602001919060010190612050565b506120779291506120c2565b5090565b50805460018160011615610100020316600290046000825580601f106120a157506120bf565b601f0160209004906000526020600020908101906120bf91906120c2565b50565b6120dc91905b8082111561207757600081556001016120c8565b905600a165627a7a72305820dc04756fecef42b0b2103b8350f1c802e62ce001b5c07a523e4513087ddbff0d0029";

    private Hoster(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Hoster(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<AddThemisHosterEventResponse> getAddThemisHosterEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("AddThemisHoster", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<AddThemisHosterEventResponse> responses = new ArrayList<AddThemisHosterEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            AddThemisHosterEventResponse typedResponse = new AddThemisHosterEventResponse();
            typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AddThemisHosterEventResponse> addThemisHosterEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("AddThemisHoster", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, AddThemisHosterEventResponse>() {
            @Override
            public AddThemisHosterEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                AddThemisHosterEventResponse typedResponse = new AddThemisHosterEventResponse();
                typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<RemoveThemisHosterEventResponse> getRemoveThemisHosterEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("RemoveThemisHoster", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RemoveThemisHosterEventResponse> responses = new ArrayList<RemoveThemisHosterEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RemoveThemisHosterEventResponse typedResponse = new RemoveThemisHosterEventResponse();
            typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RemoveThemisHosterEventResponse> removeThemisHosterEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("RemoveThemisHoster", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RemoveThemisHosterEventResponse>() {
            @Override
            public RemoveThemisHosterEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RemoveThemisHosterEventResponse typedResponse = new RemoveThemisHosterEventResponse();
                typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<ChangeToThemisHosterEventResponse> getChangeToThemisHosterEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ChangeToThemisHoster", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ChangeToThemisHosterEventResponse> responses = new ArrayList<ChangeToThemisHosterEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ChangeToThemisHosterEventResponse typedResponse = new ChangeToThemisHosterEventResponse();
            typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeToThemisHosterEventResponse> changeToThemisHosterEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ChangeToThemisHoster", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeToThemisHosterEventResponse>() {
            @Override
            public ChangeToThemisHosterEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ChangeToThemisHosterEventResponse typedResponse = new ChangeToThemisHosterEventResponse();
                typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<GetThemisHostersEventResponse> getGetThemisHostersEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("GetThemisHosters", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<GetThemisHostersEventResponse> responses = new ArrayList<GetThemisHostersEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            GetThemisHostersEventResponse typedResponse = new GetThemisHostersEventResponse();
            typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hosters = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<GetThemisHostersEventResponse> getThemisHostersEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("GetThemisHosters", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, GetThemisHostersEventResponse>() {
            @Override
            public GetThemisHostersEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                GetThemisHostersEventResponse typedResponse = new GetThemisHostersEventResponse();
                typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.hosters = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<AddThemisUserEventResponse> getAddThemisUserEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("AddThemisUser", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<AddThemisUserEventResponse> responses = new ArrayList<AddThemisUserEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            AddThemisUserEventResponse typedResponse = new AddThemisUserEventResponse();
            typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AddThemisUserEventResponse> addThemisUserEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("AddThemisUser", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, AddThemisUserEventResponse>() {
            @Override
            public AddThemisUserEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                AddThemisUserEventResponse typedResponse = new AddThemisUserEventResponse();
                typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<UpdateThemisUserEventResponse> getUpdateThemisUserEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UpdateThemisUser", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UpdateThemisUserEventResponse> responses = new ArrayList<UpdateThemisUserEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UpdateThemisUserEventResponse typedResponse = new UpdateThemisUserEventResponse();
            typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UpdateThemisUserEventResponse> updateThemisUserEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UpdateThemisUser", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UpdateThemisUserEventResponse>() {
            @Override
            public UpdateThemisUserEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UpdateThemisUserEventResponse typedResponse = new UpdateThemisUserEventResponse();
                typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<RemoveThemisUserEventResponse> getRemoveThemisUserEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("RemoveThemisUser", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RemoveThemisUserEventResponse> responses = new ArrayList<RemoveThemisUserEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RemoveThemisUserEventResponse typedResponse = new RemoveThemisUserEventResponse();
            typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RemoveThemisUserEventResponse> removeThemisUserEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("RemoveThemisUser", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RemoveThemisUserEventResponse>() {
            @Override
            public RemoveThemisUserEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RemoveThemisUserEventResponse typedResponse = new RemoveThemisUserEventResponse();
                typedResponse.id = (String) eventValues.getIndexedValues().get(0).getValue();
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

    public RemoteCall<TransactionReceipt> updateNormalUserToHoster(String _id, BigInteger _deposit) {
        Function function = new Function(
                "updateNormalUserToHoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_deposit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addUser(String _id, BigInteger _fame, String _publicKey, BigInteger _userType) {
        Function function = new Function(
                "addUser", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_fame), 
                new org.web3j.abi.datatypes.Utf8String(_publicKey), 
                new org.web3j.abi.datatypes.generated.Uint8(_userType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<String, BigInteger>> hoster(BigInteger param0) {
        final Function function = new Function("hoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<String, BigInteger>>(
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> updateUserFame(String _id, BigInteger _newFame) {
        Function function = new Function(
                "updateUserFame", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_newFame)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isThemisUser(String _user) {
        Function function = new Function("isThemisUser", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> isHoster(String _who) {
        Function function = new Function("isHoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> updateUserDeposit(BigInteger _newDeposit) {
        Function function = new Function(
                "updateUserDeposit", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newDeposit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeHoster(String _id) {
        Function function = new Function(
                "removeHoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeUser(String _id) {
        Function function = new Function(
                "removeUser", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateUser(String _id, BigInteger _newFame, String _newPublicKey, BigInteger _userType) {
        Function function = new Function(
                "updateUser", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_newFame), 
                new org.web3j.abi.datatypes.Utf8String(_newPublicKey), 
                new org.web3j.abi.datatypes.generated.Uint8(_userType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        Function function = new Function(
                "changeOwner", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple4<String, BigInteger, String, BigInteger>> users(String param0) {
        final Function function = new Function("users", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}));
        return new RemoteCall<Tuple4<String, BigInteger, String, BigInteger>>(
                new Callable<Tuple4<String, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple4<String, BigInteger, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> getHosters(BigInteger _orderID, BigInteger _num) {
        Function function = new Function(
                "getHosters", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_orderID), 
                new org.web3j.abi.datatypes.generated.Uint256(_num)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addHoster(String _id, BigInteger _fame, BigInteger _deposit, String _publicKey) {
        Function function = new Function(
                "addHoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_fame), 
                new org.web3j.abi.datatypes.generated.Uint256(_deposit), 
                new org.web3j.abi.datatypes.Utf8String(_publicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> idIndex(String param0) {
        Function function = new Function("idIndex", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Hoster> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _feeManagerAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_feeManagerAddress)));
        return deployRemoteCall(Hoster.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Hoster> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _feeManagerAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_feeManagerAddress)));
        return deployRemoteCall(Hoster.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Hoster load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Hoster(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Hoster load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Hoster(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class AddThemisHosterEventResponse {
        public String id;
    }

    public static class RemoveThemisHosterEventResponse {
        public String id;
    }

    public static class ChangeToThemisHosterEventResponse {
        public String id;
    }

    public static class GetThemisHostersEventResponse {
        public String who;

        public BigInteger orderID;

        public List<String> hosters;
    }

    public static class AddThemisUserEventResponse {
        public String id;
    }

    public static class UpdateThemisUserEventResponse {
        public String id;
    }

    public static class RemoveThemisUserEventResponse {
        public String id;
    }

    public static class ChangeOwnerEventResponse {
        public String oriOwner;

        public String newOwner;
    }
}
