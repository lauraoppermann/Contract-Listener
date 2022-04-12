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

import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.math.BigInteger;
import java.security.KeyPair;
import java.util.Map;
import java.util.TreeMap;

import com.appregistry.AppRegistry;
import com.bigchaindb.model.MetaData;
import com.bigchaindb.util.Base58;

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
    // private static final Logger logger =
    // LoggerFactory.getLogger(EthereumController.class);

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
        System.out.println("Registering event filter for event: {}");
        String encodedEventSignature = EventEncoder.encode(AppRegistry.APPREGISTEREDEVENT_EVENT);

        final EthFilter ethFilter = new EthFilter(new DefaultBlockParameterNumber(startBlock),
                new DefaultBlockParameterNumber(endBlock), contractAddress);

        ethFilter.addSingleTopic(encodedEventSignature);

        final Disposable sub = contract.appRegisteredEventEventFlowable(ethFilter).subscribe(log -> {

            System.out.println(log.appId);
            System.out.println(log.appType);
            System.out.println(log.name);

            // write into DB
            BigchainDB examples = new BigchainDB();

            // set configuration
            BigchainDB.setConfig();

            // generate Keys
            KeyPair keys = BigchainDB.getKeys();

            System.out.println(Base58.encode(keys.getPublic().getEncoded()));
            System.out.println(Base58.encode(keys.getPrivate().getEncoded()));

            // create New asset
            // asset data describes the "object", can not be changed later on
            Map<String, String> assetData = new TreeMap<String, String>() {
                {
                    put("id", log.appId);
                    put("name", log.name);
                    put("type", log.appType);
                }
            };
            System.out.println("(*) Assets Prepared..");

            // create metadata
            // can be changed later
            MetaData metaData = new MetaData();
            metaData.setMetaData("description", "");
            System.out.println("(*) Metadata Prepared..");

            // execute CREATE transaction
            String txId = examples.doCreate(assetData, metaData, keys);

            // create transfer metadata
            // this is an update of metadata
            // MetaData transferMetadata = new MetaData();
            // transferMetadata.setMetaData("description", "some other description");
            // System.out.println("(*) Transfer Metadata Prepared..");

            // // let the transaction commit in block
            // Thread.sleep(5000);

            // // execute TRANSFER transaction on the CREATED asset
            // examples.doTransfer(txId, transferMetadata, keys);

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

    public String getApps() {
        // write into DB
        BigchainDB examples = new BigchainDB();

        // set configuration
        BigchainDB.setConfig();

        // generate Keys
        KeyPair keys = BigchainDB.getKeys();

        System.out.println(Base58.encode(keys.getPublic().getEncoded()));
        System.out.println(Base58.encode(keys.getPrivate().getEncoded()));

        String apps = examples.queryAssets();
        return apps;
    }

}
