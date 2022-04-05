package com.appregistry;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class AppRegistry extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506103fe806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80636900f1b31461003b57806373e717d21461006b575b600080fd5b61004e610049366004610251565b61008e565b6040516001600160a01b0390911681526020015b60405180910390f35b61007e610079366004610293565b6100c2565b6040519015158152602001610062565b60008083836040516100a1929190610322565b908152604051908190036020019020546001600160a01b0316905092915050565b6001600160a01b03851660009081526001602052604081206100e590848461016f565b5085600086866040516100f9929190610322565b90815260405190819003602001812080546001600160a01b039384166001600160a01b0319909116179055908716907f7f8d10f671b9e10e0c6d7614c84cbc8d4029e7c1554617b2a4bb4d47d76d35df9061015b90889088908890889061035b565b60405180910390a250600195945050505050565b82805461017b9061038d565b90600052602060002090601f01602090048101928261019d57600085556101e3565b82601f106101b65782800160ff198235161785556101e3565b828001600101855582156101e3579182015b828111156101e35782358255916020019190600101906101c8565b506101ef9291506101f3565b5090565b5b808211156101ef57600081556001016101f4565b60008083601f84011261021a57600080fd5b50813567ffffffffffffffff81111561023257600080fd5b60208301915083602082850101111561024a57600080fd5b9250929050565b6000806020838503121561026457600080fd5b823567ffffffffffffffff81111561027b57600080fd5b61028785828601610208565b90969095509350505050565b6000806000806000606086880312156102ab57600080fd5b85356001600160a01b03811681146102c257600080fd5b9450602086013567ffffffffffffffff808211156102df57600080fd5b6102eb89838a01610208565b9096509450604088013591508082111561030457600080fd5b5061031188828901610208565b969995985093965092949392505050565b8183823760009101908152919050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b60408152600061036f604083018688610332565b8281036020840152610382818587610332565b979650505050505050565b600181811c908216806103a157607f821691505b602082108114156103c257634e487b7160e01b600052602260045260246000fd5b5091905056fea2646970667358221220683c22efa5e0b10cd9eb9902af3214ef678bb9e91cfd38671d09a44c307e64fa64736f6c634300080b0033";

    public static final String FUNC_CREATEAPP = "createApp";

    public static final String FUNC_GETAPPID = "getAppID";

    public static final Event APPREGISTEREDEVENT_EVENT = new Event("AppRegisteredEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected AppRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AppRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AppRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AppRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AppRegisteredEventEventResponse> getAppRegisteredEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPREGISTEREDEVENT_EVENT, transactionReceipt);
        ArrayList<AppRegisteredEventEventResponse> responses = new ArrayList<AppRegisteredEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AppRegisteredEventEventResponse typedResponse = new AppRegisteredEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.appId = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.appType = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AppRegisteredEventEventResponse> appRegisteredEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AppRegisteredEventEventResponse>() {
            @Override
            public AppRegisteredEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPREGISTEREDEVENT_EVENT, log);
                AppRegisteredEventEventResponse typedResponse = new AppRegisteredEventEventResponse();
                typedResponse.log = log;
                typedResponse.appId = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.appType = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AppRegisteredEventEventResponse> appRegisteredEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPREGISTEREDEVENT_EVENT));
        return appRegisteredEventEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createApp(String appId, String name, String appType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEAPP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, appId), 
                new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(appType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getAppID(String name) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static AppRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AppRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AppRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AppRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AppRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AppRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AppRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AppRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AppRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AppRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AppRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AppRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AppRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AppRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AppRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AppRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AppRegisteredEventEventResponse extends BaseEventResponse {
        public String appId;

        public String name;

        public String appType;
    }
}
