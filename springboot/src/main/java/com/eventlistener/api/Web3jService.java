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
@Slf4j
@EnableMongoRepositories
@Service
public class Web3jService {
    @Autowired
    public MongoDBService mongoService;

    @Getter
    private String nodeName;

    @Getter
    @Setter
    private Web3j web3j;

    public Web3jService() {
    }

    public Web3jService(String nodeName,
            Web3j web3j) {
        this.nodeName = nodeName;
        this.web3j = web3j;
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
            System.out.println("-------------------------------");
            System.out.println(log.appId);
            System.out.println(log.appType);
            System.out.println(log.name);

            // save a couple of customers
            mongoService.addModule(new BladeModule(log.appId, log.name, log.appType));

            //

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

    public String getApps(String query)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // fetch all customers
        System.out.println("Modules found with findAll():");
        System.out.println("-------------------------------");
        for (BladeModule module : mongoService.findAll()) {
            System.out.println(module);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByAppName('Ikea'):");
        System.out.println("--------------------------------");
        for (BladeModule module : mongoService.findByModuleName("Ikea")) {
            System.out.println(module);
        }
        System.out.println("Customers found with findByAppType('education'):");
        System.out.println("--------------------------------");
        for (BladeModule module : mongoService.findByModuleType("education")) {
            System.out.println(module);
        }
        return "some apps";
    }

}
