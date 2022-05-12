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

package com.eventlistener.api;

import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.math.BigInteger;

import com.appregistry.AppRegistry;
import com.eventlistener.api.db.BladeModule;
import com.eventlistener.api.db.ModuleRepository;
import com.eventlistener.api.db.MongoDBService;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

/**
 * A BlockchainService implementating utilising the Web3j library.
 *
 * @author Craig Williams <craig.williams@consensys.net>
 */

@EnableMongoRepositories(basePackages = "com.eventlistener.api.db")
public class Web3jService {

    // @Autowired
    @Getter
    @Setter
    private MongoDBService mongoService;

    @Getter
    private String nodeName;

    @Getter
    @Setter
    private Web3j web3j;

    public Web3jService(String nodeName,
            Web3j web3j, MongoDBService mongoService) {
        this.nodeName = nodeName;
        this.web3j = web3j;
        this.mongoService = mongoService;
    }

    public void registerEventListener(String contractAddress,
            BigInteger startBlock,
            BigInteger endBlock, AppRegistry contract
    // Optional<Runnable> onCompletion
    ) {
        System.out.println("Registering event filter for event: AppRegisteredEvent");
        String encodedEventSignature = EventEncoder.encode(AppRegistry.APPREGISTEREDEVENT_EVENT);

        final EthFilter ethFilter = new EthFilter(new DefaultBlockParameterNumber(startBlock),
                new DefaultBlockParameterNumber(endBlock), contractAddress);

        ethFilter.addSingleTopic(encodedEventSignature);

        final Disposable sub = contract.appRegisteredEventEventFlowable(ethFilter).subscribe(log -> {

            System.out.println("Event log output:");
            System.out.println(log.log.getBlockNumber());
            System.out.println("-------------------------------");
            System.out.println(log.appId);
            System.out.println(log.appType);
            System.out.println(log.name);

            // save a couple of customers

            BladeModule module = new BladeModule(log.appId, log.name, log.appType);
            System.out.println(module.toString());
            mongoService.addModule(module);

            //

        }, Throwable::printStackTrace);

        if (sub.isDisposed())

        {
            // There was an error subscribing
            // throw new BlockchainException(String.format(
            // "Failed to subcribe for filter %s. The subscription is disposed.",
            // eventFilter.getId()));
        }

        // return new FilterSubscription(eventFilter, sub, startBlock);
    }

    // public void createApps()
    // throws NoSuchFieldException, SecurityException, IllegalArgumentException,
    // IllegalAccessException {

    // BladeModule module = new BladeModule("appId", "name", "appType");

    // mongoService.addModule(module);
    // }

}
