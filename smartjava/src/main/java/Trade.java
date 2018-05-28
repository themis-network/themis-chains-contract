
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    private static final String BINARY = "6080604052600560005533600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550613137806100596000396000f30060806040526004361061013d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680626bd02f1461014257806301fabd751461018757806309ec6cc7146101e25780632495a13114610227578063364f0b01146102525780634129b2c9146102b757806342551d3b146103245780634cd2110114610369578063715018a6146103ae5780637494ad81146103c55780637d3b3c6d1461042a57806382d0d319146104625780638da5cb5b146104cf578063973ad2701461052657806399c6679d146105815780639c409ae4146105ee5780639f6bd2a9146106c1578063a8c9fe8b1461071c578063f012be381461079e578063f0d06926146107e3578063f2fde38b14610850578063f52706a014610893578063f55ed73d146108ee578063f6f6648314610979575b600080fd5b34801561014e57600080fd5b5061016d600480360381019080803590602001909291905050506109d0565b604051808215151515815260200191505060405180910390f35b34801561019357600080fd5b506101c8600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610aa1565b604051808215151515815260200191505060405180910390f35b3480156101ee57600080fd5b5061020d60048036038101908080359060200190929190505050610ba3565b604051808215151515815260200191505060405180910390f35b34801561023357600080fd5b5061023c610cee565b6040518082815260200191505060405180910390f35b34801561025e57600080fd5b5061029d60048036038101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610cf4565b604051808215151515815260200191505060405180910390f35b3480156102c357600080fd5b506102e260048036038101908080359060200190929190505050610dc4565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561033057600080fd5b5061034f60048036038101908080359060200190929190505050610e04565b604051808215151515815260200191505060405180910390f35b61039460048036038101908080359060200190929190803560ff169060200190929190505050611001565b604051808215151515815260200191505060405180910390f35b3480156103ba57600080fd5b506103c3611470565b005b3480156103d157600080fd5b5061041060048036038101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611575565b604051808215151515815260200191505060405180910390f35b610448600480360381019080803590602001909291905050506117e8565b604051808215151515815260200191505060405180910390f35b34801561046e57600080fd5b5061048d60048036038101908080359060200190929190505050611e36565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156104db57600080fd5b506104e4611e76565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561053257600080fd5b50610567600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611e9c565b604051808215151515815260200191505060405180910390f35b34801561058d57600080fd5b506105ac60048036038101908080359060200190929190505050611f95565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156105fa57600080fd5b5061064660048036038101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560ff169060200190929190505050611fd5565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561068657808201518184015260208101905061066b565b50505050905090810190601f1680156106b35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156106cd57600080fd5b50610702600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506120eb565b604051808215151515815260200191505060405180910390f35b34801561072857600080fd5b5061074760048036038101908080359060200190929190505050612148565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561078a57808201518184015260208101905061076f565b505050509050019250505060405180910390f35b3480156107aa57600080fd5b506107c9600480360381019080803590602001909291905050506121ec565b604051808215151515815260200191505060405180910390f35b3480156107ef57600080fd5b5061080e6004803603810190808035906020019092919050505061230a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561085c57600080fd5b50610891600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061234a565b005b34801561089f57600080fd5b506108d4600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506124a2565b604051808215151515815260200191505060405180910390f35b3480156108fa57600080fd5b5061095f60048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050612582565b604051808215151515815260200191505060405180910390f35b34801561098557600080fd5b5061098e612ad1565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610a2e57600080fd5b60038210151515610a3e57600080fd5b6000600283811515610a4c57fe5b0614151515610a5a57600080fd5b816000819055507f25703f8039df629ee4f1c133d7410bfda3816709b91fe4006c183a14e9c8733c826040518082815260200191505060405180910390a160019050919050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610aff57600080fd5b6001600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff167f1cfba79c837dd282b5affd88ad85c693d8f3fc6abb9999b92849f776f499045b60405160405180910390a260019050919050565b6000813373ffffffffffffffffffffffffffffffffffffffff166002600083815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610c1657600080fd5b60006004811115610c2357fe5b6002600085815260200190815260200160002060060160009054906101000a900460ff166004811115610c5257fe5b141515610c5e57600080fd5b60016002600085815260200190815260200160002060060160006101000a81548160ff02191690836004811115610c9157fe5b02179055503373ffffffffffffffffffffffffffffffffffffffff167f1ffa1614a3d29e647f57152e7ffc0a1d36ddd281c9e0a36dd7af40c860f17f75846040518082815260200191505060405180910390a26001915050919050565b60005481565b6000806000809150600090505b6002600086815260200190815260200160002060040180549050811015610db9578373ffffffffffffffffffffffffffffffffffffffff166002600087815260200190815260200160002060040182815481101515610d5c57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610dac5760019150610db9565b8080600101915050610d01565b819250505092915050565b60006005600083815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6000813373ffffffffffffffffffffffffffffffffffffffff166002600083815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161480610ed857503373ffffffffffffffffffffffffffffffffffffffff166002600083815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b1515610ee357600080fd5b600073ffffffffffffffffffffffffffffffffffffffff166005600085815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610f5457600080fd5b336005600085815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff167f732127bee9b841a7689153db9c508a07fb2603e7e0e1229f700989bb9b808027846040518082815260200191505060405180910390a26001915050919050565b60008073ffffffffffffffffffffffffffffffffffffffff16600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561106057600080fd5b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663665ae96f336040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b15801561111d57600080fd5b505af1158015611131573d6000803e3d6000fd5b505050506040513d602081101561114757600080fd5b8101908080519060200190929190505050151561116357600080fd5b816000600181111561117157fe5b81600181111561117d57fe5b148061119e575060018081111561119057fe5b81600181111561119c57fe5b145b15156111a957600080fd5b600073ffffffffffffffffffffffffffffffffffffffff166002600086815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561121a57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff166002600086815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561128b57600080fd5b82600181111561129757fe5b600060018111156112a457fe5b141561130057336002600086815260200190815260200160002060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b82600181111561130c57fe5b60018081111561131857fe5b141561137457336002600086815260200190815260200160002060020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b336002600086815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060006002600086815260200190815260200160002060060160006101000a81548160ff021916908360048111156113fc57fe5b02179055503373ffffffffffffffffffffffffffffffffffffffff167f78048396f193baa9a33031c179cc0a28722e10fdf665d5045673690245744d3185856040518083815260200182600181111561145157fe5b60ff1681526020019250505060405180910390a2600191505092915050565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156114cc57600080fd5b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482060405160405180910390a26000600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b600060011515600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1615151415156115d657600080fd5b600073ffffffffffffffffffffffffffffffffffffffff166005600085815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561164857600080fd5b6002600084815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16148061171957506002600084815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16145b151561172457600080fd5b816005600085815260200190815260200160002060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f536c20da4b511cb741c285222ca87b05a9fb5272c17d19f5e3fae46be8333157856040518082815260200191505060405180910390a36001905092915050565b60006060600073ffffffffffffffffffffffffffffffffffffffff16600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561184a57600080fd5b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663665ae96f336040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b15801561190757600080fd5b505af115801561191b573d6000803e3d6000fd5b505050506040513d602081101561193157600080fd5b8101908080519060200190929190505050151561194d57600080fd5b6000600481111561195a57fe5b6002600085815260200190815260200160002060060160009054906101000a900460ff16600481111561198957fe5b14151561199557600080fd5b600073ffffffffffffffffffffffffffffffffffffffff166002600085815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611acc573373ffffffffffffffffffffffffffffffffffffffff166002600085815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515611a7257600080fd5b336002600085815260200190815260200160002060020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611c00565b600073ffffffffffffffffffffffffffffffffffffffff166002600085815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611bff573373ffffffffffffffffffffffffffffffffffffffff166002600085815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515611ba957600080fd5b336002600085815260200190815260200160002060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b600280600085815260200190815260200160002060060160006101000a81548160ff02191690836004811115611c3257fe5b0217905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16631daf5d9b6000546040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050600060405180830381600087803b158015611cca57600080fd5b505af1158015611cde573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052506020811015611d0857600080fd5b810190808051640100000000811115611d2057600080fd5b82810190506020810184811115611d3657600080fd5b8151856020820283011164010000000082111715611d5357600080fd5b505092919050505090506000548151141515611d6e57600080fd5b80600260008581526020019081526020016000206004019080519060200190611d98929190612f7f565b503373ffffffffffffffffffffffffffffffffffffffff167f03cb17cf178cd2491dd9f5ee59240dce067265b43471b393866ccd4b2614dbbe84836040518083815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015611e1c578082015181840152602081019050611e01565b50505050905001935050505060405180910390a250919050565b60006002600083815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515611efa57600080fd5b600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81549060ff02191690558173ffffffffffffffffffffffffffffffffffffffff167ff9f012dbf94ec6f26d3a73fbbae56a56fc7b236c4390d891201576ba3aaeb89160405160405180910390a260019050919050565b60006005600083815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b60606002600085815260200190815260200160002060050160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600083600181111561203857fe5b81526020019081526020016000206000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156120dd5780601f106120b2576101008083540402835291602001916120dd565b820191906000526020600020905b8154815290600101906020018083116120c057829003601f168201915b505050505090509392505050565b600060011515600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff161515149050919050565b6060600260008381526020019081526020016000206004018054806020026020016040519081016040528092919081815260200182805480156121e057602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311612196575b50505050509050919050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561224a57600080fd5b6003600481111561225757fe5b6002600084815260200190815260200160002060060160009054906101000a900460ff16600481111561228657fe5b14151561229257600080fd5b60046002600084815260200190815260200160002060060160006101000a81548160ff021916908360048111156122c557fe5b02179055507f566d1387472cc6e719ff1a2585d3a6b11b93d3c0f4aeb6dfbceb65747a391889826040518082815260200191505060405180910390a160019050919050565b60006002600083815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156123a657600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515156123e257600080fd5b8073ffffffffffffffffffffffffffffffffffffffff16600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a380600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561250057600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561253c57600080fd5b81600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550919050565b600061258c613009565b612594613009565b600060606000806060893373ffffffffffffffffffffffffffffffffffffffff166002600083815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16148061266f57503373ffffffffffffffffffffffffffffffffffffffff166002600083815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b151561267a57600080fd5b6002600481111561268757fe5b600260008d815260200190815260200160002060060160009054906101000a900460ff1660048111156126b657fe5b1415156126c257600080fd5b6127006040805190810160405280600181526020017f2c00000000000000000000000000000000000000000000000000000000000000815250612af7565b975061270b8a612af7565b965060016127228989612b2590919063ffffffff16565b019550600260008c81526020019081526020016000206004018054806020026020016040519081016040528092919081815260200182805480156127bb57602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311612771575b505050505094508451861415156127d157600080fd5b3373ffffffffffffffffffffffffffffffffffffffff16600260008d815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561284a5760018081111561284757fe5b93505b3373ffffffffffffffffffffffffffffffffffffffff16600260008d815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156128c457600060018111156128c157fe5b93505b600092505b8583101561298e576128ec6128e78989612b9c90919063ffffffff16565b612bb6565b915081600260008d81526020019081526020016000206005016000878681518110151561291557fe5b9060200190602002015173ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008681526020019081526020016000206000019080519060200190612980929190613023565b5082806001019350506128c9565b6001600260008d815260200190815260200160002060030160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908315150217905550612a038b612c18565b503373ffffffffffffffffffffffffffffffffffffffff167fbcd9767915967b657be53b8432bce88b79ba16948935a12a856b9ee15ed831d18c8c6040518083815260200180602001828103825283818151815260200191508051906020019080838360005b83811015612a84578082015181840152602081019050612a69565b50505050905090810190601f168015612ab15780820380516001836020036101000a031916815260200191505b50935050505060405180910390a260019850505050505050505092915050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b612aff613009565b600060208301905060408051908101604052808451815260200182815250915050919050565b6000808260000151612b498560000151866020015186600001518760200151612db0565b0190505b836000015184602001510181111515612b955781806001019250508260000151612b8d856020015183038660000151038386600001518760200151612db0565b019050612b4d565b5092915050565b612ba4613009565b612baf838383612e96565b5092915050565b606080600083600001516040519080825280601f01601f191660200182016040528015612bf25781602001602082028038833980820191505090505b509150602082019050612c0e8185602001518660000151612f34565b8192505050919050565b60008060006002600085815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1691506002600085815260200190815260200160002060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600115156002600086815260200190815260200160002060030160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff161515148015612d675750600115156002600086815260200190815260200160002060030160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff161515145b15612da55760036002600086815260200190815260200160002060060160006101000a81548160ff02191690836004811115612d9f57fe5b02179055505b600192505050919050565b60008060008060008060008060008b97508c8b111515612e805760208b111515612e3a5760018b60200360080260020a03196001029550858a511694508a8d8d010393508588511692505b84600019168360001916141515612e32578388101515612e1f578c8c019850612e86565b8780600101985050858851169250612dfb565b879850612e86565b8a8a209150600096505b8a8d0387111515612e7f578a88209050806000191682600019161415612e6c57879850612e86565b6001880197508680600101975050612e44565b5b8c8c0198505b5050505050505050949350505050565b612e9e613009565b6000612ebc8560000151866020015186600001518760200151612db0565b90508460200151836020018181525050846020015181038360000181815250508460000151856020015101811415612efe576000856000018181525050612f29565b8360000151836000015101856000018181510391508181525050836000015181018560200181815250505b829150509392505050565b60005b602082101515612f5c5782518452602084019350602083019250602082039150612f37565b6001826020036101000a0390508019835116818551168181178652505050505050565b828054828255906000526020600020908101928215612ff8579160200282015b82811115612ff75782518260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555091602001919060010190612f9f565b5b50905061300591906130a3565b5090565b604080519081016040528060008152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061306457805160ff1916838001178555613092565b82800160010185558215613092579182015b82811115613091578251825591602001919060010190613076565b5b50905061309f91906130e6565b5090565b6130e391905b808211156130df57600081816101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055506001016130a9565b5090565b90565b61310891905b808211156131045760008160009055506001016130ec565b5090565b905600a165627a7a72305820465db21cb39d02bbaa8e3259be95b197eca62cbb2265cfac96b0f87048fb92ed0029";

    private Trade(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Trade(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<UpdateDefaultHosterNumberEventResponse> getUpdateDefaultHosterNumberEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UpdateDefaultHosterNumber", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UpdateDefaultHosterNumberEventResponse> responses = new ArrayList<UpdateDefaultHosterNumberEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UpdateDefaultHosterNumberEventResponse typedResponse = new UpdateDefaultHosterNumberEventResponse();
            typedResponse.newNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UpdateDefaultHosterNumberEventResponse> updateDefaultHosterNumberEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UpdateDefaultHosterNumber", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UpdateDefaultHosterNumberEventResponse>() {
            @Override
            public UpdateDefaultHosterNumberEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UpdateDefaultHosterNumberEventResponse typedResponse = new UpdateDefaultHosterNumberEventResponse();
                typedResponse.newNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
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
                typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<CreateOrderEventResponse> getCreateOrderEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("CreateOrder", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<CreateOrderEventResponse> responses = new ArrayList<CreateOrderEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            CreateOrderEventResponse typedResponse = new CreateOrderEventResponse();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CreateOrderEventResponse> createOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("CreateOrder", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, CreateOrderEventResponse>() {
            @Override
            public CreateOrderEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                CreateOrderEventResponse typedResponse = new CreateOrderEventResponse();
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.userType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<CancelTradeEventResponse> getCancelTradeEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("CancelTrade", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<CancelTradeEventResponse> responses = new ArrayList<CancelTradeEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            CancelTradeEventResponse typedResponse = new CancelTradeEventResponse();
            typedResponse.creator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CancelTradeEventResponse> cancelTradeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("CancelTrade", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, CancelTradeEventResponse>() {
            @Override
            public CancelTradeEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                CancelTradeEventResponse typedResponse = new CancelTradeEventResponse();
                typedResponse.creator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<ConfirmTradeOrderEventResponse> getConfirmTradeOrderEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ConfirmTradeOrder", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ConfirmTradeOrderEventResponse> responses = new ArrayList<ConfirmTradeOrderEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ConfirmTradeOrderEventResponse typedResponse = new ConfirmTradeOrderEventResponse();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hosters = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ConfirmTradeOrderEventResponse> confirmTradeOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ConfirmTradeOrder", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ConfirmTradeOrderEventResponse>() {
            @Override
            public ConfirmTradeOrderEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ConfirmTradeOrderEventResponse typedResponse = new ConfirmTradeOrderEventResponse();
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.hosters = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<UploadSecretEventResponse> getUploadSecretEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("UploadSecret", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<UploadSecretEventResponse> responses = new ArrayList<UploadSecretEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            UploadSecretEventResponse typedResponse = new UploadSecretEventResponse();
            typedResponse.uploader = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.secrets = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UploadSecretEventResponse> uploadSecretEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("UploadSecret", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, UploadSecretEventResponse>() {
            @Override
            public UploadSecretEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                UploadSecretEventResponse typedResponse = new UploadSecretEventResponse();
                typedResponse.uploader = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.secrets = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<FinishOrderEventResponse> getFinishOrderEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("FinishOrder", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<FinishOrderEventResponse> responses = new ArrayList<FinishOrderEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            FinishOrderEventResponse typedResponse = new FinishOrderEventResponse();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<FinishOrderEventResponse> finishOrderEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("FinishOrder", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, FinishOrderEventResponse>() {
            @Override
            public FinishOrderEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                FinishOrderEventResponse typedResponse = new FinishOrderEventResponse();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<ArbitrateEventResponse> getArbitrateEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Arbitrate", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ArbitrateEventResponse> responses = new ArrayList<ArbitrateEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ArbitrateEventResponse typedResponse = new ArbitrateEventResponse();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ArbitrateEventResponse> arbitrateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Arbitrate", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ArbitrateEventResponse>() {
            @Override
            public ArbitrateEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ArbitrateEventResponse typedResponse = new ArbitrateEventResponse();
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<JudgeEventResponse> getJudgeEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Judge", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<JudgeEventResponse> responses = new ArrayList<JudgeEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            JudgeEventResponse typedResponse = new JudgeEventResponse();
            typedResponse.winner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.judge = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<JudgeEventResponse> judgeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Judge", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, JudgeEventResponse>() {
            @Override
            public JudgeEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                JudgeEventResponse typedResponse = new JudgeEventResponse();
                typedResponse.winner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.judge = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.orderID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<AddArbitratorEventResponse> getAddArbitratorEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("AddArbitrator", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<AddArbitratorEventResponse> responses = new ArrayList<AddArbitratorEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            AddArbitratorEventResponse typedResponse = new AddArbitratorEventResponse();
            typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AddArbitratorEventResponse> addArbitratorEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("AddArbitrator", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, AddArbitratorEventResponse>() {
            @Override
            public AddArbitratorEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                AddArbitratorEventResponse typedResponse = new AddArbitratorEventResponse();
                typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<RemoveArbitratorEventResponse> getRemoveArbitratorEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("RemoveArbitrator", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RemoveArbitratorEventResponse> responses = new ArrayList<RemoveArbitratorEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RemoveArbitratorEventResponse typedResponse = new RemoveArbitratorEventResponse();
            typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RemoveArbitratorEventResponse> removeArbitratorEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("RemoveArbitrator", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RemoveArbitratorEventResponse>() {
            @Override
            public RemoveArbitratorEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RemoveArbitratorEventResponse typedResponse = new RemoveArbitratorEventResponse();
                typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
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

    public RemoteCall<TransactionReceipt> updateDefaultHosterNumber(BigInteger _hosterNumber) {
        Function function = new Function(
                "updateDefaultHosterNumber", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hosterNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addArbitrator(String who) {
        Function function = new Function(
                "addArbitrator", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(who)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> cancelTrade(BigInteger orderID) {
        Function function = new Function(
                "cancelTrade", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> hosterNumber() {
        Function function = new Function("hosterNumber", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isOrderHoster(BigInteger orderID, String user) {
        Function function = new Function("isOrderHoster", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.Address(user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> getWinner(BigInteger orderID) {
        Function function = new Function("getWinner", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> arbitrate(BigInteger orderID) {
        Function function = new Function(
                "arbitrate", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> createNewTradeOrder(BigInteger orderID, BigInteger userType, BigInteger weiValue) {
        Function function = new Function(
                "createNewTradeOrder", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.generated.Uint8(userType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        Function function = new Function(
                "renounceOwnership", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> judge(BigInteger orderID, String winner) {
        Function function = new Function(
                "judge", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.Address(winner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> confirmTradeOrder(BigInteger orderID, BigInteger weiValue) {
        Function function = new Function(
                "confirmTradeOrder", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> getOrderSeller(BigInteger orderID) {
        Function function = new Function("getOrderSeller", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> removeArbitrator(String who) {
        Function function = new Function(
                "removeArbitrator", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(who)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getRequester(BigInteger orderID) {
        Function function = new Function("getRequester", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getSecret(BigInteger orderID, String hosterID, BigInteger userType) {
        Function function = new Function("getSecret", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.Address(hosterID), 
                new org.web3j.abi.datatypes.generated.Uint8(userType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isArbitrator(String who) {
        Function function = new Function("isArbitrator", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<List<String>> getOrderHosters(BigInteger orderID) {
        Function function = new Function("getOrderHosters", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function, List<String>.class);
    }

    public RemoteCall<TransactionReceipt> finishOrder(BigInteger orderID) {
        Function function = new Function(
                "finishOrder", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getOrderBuyer(BigInteger orderID) {
        Function function = new Function("getOrderBuyer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID)), 
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

    public RemoteCall<TransactionReceipt> updateHosterContract(String _hoster) {
        Function function = new Function(
                "updateHosterContract", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_hoster)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> uploadSecret(BigInteger orderID, String secrets) {
        Function function = new Function(
                "uploadSecret", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(orderID), 
                new org.web3j.abi.datatypes.Utf8String(secrets)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> hosterContract() {
        Function function = new Function("hosterContract", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Trade> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Trade.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Trade> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Trade.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Trade load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trade(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Trade load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trade(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class UpdateDefaultHosterNumberEventResponse {
        public BigInteger newNumber;
    }

    public static class UpdateHosterContractEventResponse {
        public String newAddress;
    }

    public static class CreateOrderEventResponse {
        public String user;

        public BigInteger orderID;

        public BigInteger userType;
    }

    public static class CancelTradeEventResponse {
        public String creator;

        public BigInteger orderID;
    }

    public static class ConfirmTradeOrderEventResponse {
        public String user;

        public BigInteger orderID;

        public List<String> hosters;
    }

    public static class UploadSecretEventResponse {
        public String uploader;

        public BigInteger orderID;

        public String secrets;
    }

    public static class FinishOrderEventResponse {
        public BigInteger orderID;
    }

    public static class ArbitrateEventResponse {
        public String user;

        public BigInteger orderID;
    }

    public static class JudgeEventResponse {
        public String winner;

        public String judge;

        public BigInteger orderID;
    }

    public static class AddArbitratorEventResponse {
        public String who;
    }

    public static class RemoveArbitratorEventResponse {
        public String who;
    }

    public static class OwnershipRenouncedEventResponse {
        public String previousOwner;
    }

    public static class OwnershipTransferredEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
