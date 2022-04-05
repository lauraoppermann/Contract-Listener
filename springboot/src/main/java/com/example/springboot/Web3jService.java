/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.springboot;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.core.methods.response.EthLog.LogResult;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.appregistry.AppRegistry;
import com.appregistry.AppRegistry.AppRegisteredEventEventResponse;

/**
 * A BlockchainService implementating utilising the Web3j library.
 *
 * @author Craig Williams <craig.williams@consensys.net>
 */
@Slf4j
public class Web3jService {

    // private static final String EVENT_EXECUTOR_NAME = "EVENT";
    @Getter
    private String nodeName;

    @Getter
    @Setter
    private Web3j web3j;
    private static final Logger logger = LoggerFactory.getLogger(EthereumController.class);

    public Web3jService(String nodeName,
            Web3j web3j) {
        this.nodeName = nodeName;
        this.web3j = web3j;
    }

    public List<LogResult> retrieveEvents(String contractAddress,
            BigInteger startBlock,
            BigInteger endBlock) throws Exception {
        String encodedEventSignature = EventEncoder.encode(AppRegistry.APPREGISTEREDEVENT_EVENT);

        final EthFilter ethFilter = new EthFilter(new DefaultBlockParameterNumber(startBlock),
                new DefaultBlockParameterNumber(endBlock), contractAddress);

        ethFilter.addSingleTopic(encodedEventSignature);

        try {
            final EthLog logs = web3j.ethGetLogs(ethFilter).send();
            return logs.getLogs();

        } catch (IOException e) {
            throw new Exception("Error obtaining logs", e);
        }

    }

    public void registerEventListener(String contractAddress,
            BigInteger startBlock,
            BigInteger endBlock, AppRegistry contract
    // Optional<Runnable> onCompletion
    ) {
        System.out.println("Registering event filter for event: {}");
        String encodedEventSignature = EventEncoder.encode(AppRegistry.APPREGISTEREDEVENT_EVENT);

        final EthFilter ethFilter = new EthFilter(new DefaultBlockParameterNumber(startBlock),
                new DefaultBlockParameterNumber(endBlock), contractAddress);

        ethFilter.addSingleTopic(encodedEventSignature);

        final Flowable<Log> flowable = web3j
                .ethLogFlowable(ethFilter)
                .doOnComplete(() -> {
                    System.out.println("doOnComplete");
                    // if (onCompletion.isPresent()) {
                    // onCompletion.get().run();
                    // }
                });
        final Disposable sub = contract.appRegisteredEventEventFlowable(ethFilter).subscribe(log -> {

            System.out.println(log.appId);
            System.out.println(log.appType);
            System.out.println(log.name);

            // write into DB

        });

        if (sub.isDisposed())

        {
            // There was an error subscribing
            // throw new BlockchainException(String.format(
            // "Failed to subcribe for filter %s. The subscription is disposed.",
            // eventFilter.getId()));
        }

        // return new FilterSubscription(eventFilter, sub, startBlock);
    }

}
