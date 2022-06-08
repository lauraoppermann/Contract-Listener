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

package com.eventlistener.api.services;

import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;

import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.math.BigInteger;

import com.bladeregisty.BladeRegistry;
import com.eventlistener.api.db.BladeApp;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * A BlockchainService implementating utilising the Web3j library.
 *
 * @author Craig Williams <craig.williams@consensys.net>
 */

@EnableMongoRepositories(basePackages = "com.eventlistener.api.db")
public class BladeRegistryService {

    // @Autowired
    @Getter
    @Setter
    private MongoDBService mongoService;

    @Getter
    private String nodeName;

    @Getter
    @Setter
    private Web3j web3j;

    public BladeRegistryService(String nodeName,
            Web3j web3j, MongoDBService mongoService) {
        this.nodeName = nodeName;
        this.web3j = web3j;
        this.mongoService = mongoService;
    }

    public void registerEventListener(String contractAddress,
            BigInteger startBlock,
            BigInteger endBlock, BladeRegistry contract
    // Optional<Runnable> onCompletion
    ) {
        System.out.println("Registering event filter for event: AppRegisteredEvent");

        DefaultBlockParameter start = new DefaultBlockParameterNumber(startBlock);
        DefaultBlockParameter end = DefaultBlockParameterName.LATEST;

        final Disposable subRegisterApp = contract.appRegisteredEventEventFlowable(start,
                end).subscribe(log -> {

                    System.out.println("Register AppID:");
                    System.out.println("-------------------------------");
                    System.out.println(log.appID);

                    // save a couple of customers

                    BladeApp module = new BladeApp(log.appID);
                    mongoService.addApp(module);

                }, Throwable::printStackTrace);

        final Disposable subSetName = contract
                .appNameRegisteredEventEventFlowable(start, end)
                .subscribe(log -> {

                    System.out.println("Update AppID with name");
                    System.out.println("-------------------------------");
                    System.out.println(log.appID);
                    System.out.println(log.appName);

                    // save a couple of customers

                    mongoService.updateName(log.appID, log.appName);

                }, Throwable::printStackTrace);

        final Disposable subSetUrl = contract.appVersionURLSetEventEventFlowable(start, end)
                .subscribe(log -> {

                    System.out.println("update appID with url");
                    System.out.println("-------------------------------");
                    System.out.println(log.appID);
                    System.out.println(log.appURL);

                    // save a couple of customers

                    mongoService.updateURL(log.appID, log.appURL);

                }, Throwable::printStackTrace);

        final Disposable subSetOwner = contract.appSetOwnerEventEventFlowable(start, end)
                .subscribe(log -> {

                    System.out.println("update appID with owner");

                    System.out.println("-------------------------------");
                    System.out.println(log.appID);
                    System.out.println(log.appOwnerID);

                    // save a couple of customers

                    mongoService.updateOwner(log.appID, log.appOwnerID);

                }, Throwable::printStackTrace);

        final Disposable subRegisterVersion = contract.appVersionRegisteredEventEventFlowable(start, end)
                .subscribe(log -> {

                    System.out.println("update appID with version");
                    System.out.println("-------------------------------");
                    System.out.println(log.appID);
                    System.out.println(log.appVersionID);

                    // save a couple of customers

                    mongoService.updateVersion(log.appID, log.appVersionID);

                }, Throwable::printStackTrace);
    }

}
