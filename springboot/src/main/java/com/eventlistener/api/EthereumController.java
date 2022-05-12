package com.eventlistener.api;

import java.io.IOException;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.appregistry.AppRegistry;
import com.eventlistener.api.db.BladeModule;
import com.eventlistener.api.db.MongoDBService;

@RestController
public class EthereumController {
    private static final Logger logger = LoggerFactory.getLogger(EthereumController.class);

    @Autowired
    MongoDBService mongoService;

    private Web3j web3j = Web3j.build(new HttpService("http://geth:8545"));

    Web3jService web3jService;

    @Value("${ethereum.contractAddress}")
    String contractAddress;

    @GetMapping("ethereum/contract-address")
    public String getContractAddress() {
        return contractAddress;
    }

    @GetMapping("ethereum/peers")
    public String getPeerCount() throws IOException {
        return web3j.netPeerCount().send().getQuantity().toString();
    }

    @GetMapping("ethereum/register-event-listener")
    public String registerEventListener() {
        logger.debug("Receiving app name");

        Web3jService web3jService = new Web3jService("test", web3j, mongoService);

        String result = "";

        try {

            EthBlock latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger gasLimit = latestBlock.getBlock().getGasLimit();

            final BigInteger gasPrice = BigInteger.valueOf(2205000); // value to be chosen
            final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            Credentials credentials = Credentials.create(ecKeyPair);

            TransactionManager tManager = new RawTransactionManager(web3j, credentials);
            final AppRegistry contract = AppRegistry.load(contractAddress, web3j, tManager, gasProvider);

            // find a better end and startblock value, LATEST didnt work as far as I
            // remember
            web3jService.registerEventListener(contractAddress, BigInteger.valueOf(12100000L),
                    BigInteger.valueOf(12258799L),
                    contract);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("ethereum/get-all-apps")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getAllApps()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        String apps = mongoService.findAll().toString();
        return apps;
    }

    @GetMapping("ethereum/get-apps-by-name")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getAppsByName(@RequestParam String name)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        String apps = mongoService.findByModuleName(name).toString();
        return apps;
    }

    // @GetMapping("ethereum/create-dummy-app")
    // public void createDummyApp()
    // throws NoSuchFieldException, SecurityException, IllegalArgumentException,
    // IllegalAccessException {
    // // mongoService.addModule(new BladeModule("test", "test", "test"));
    // Web3jService web3jService = new Web3jService("test", web3j, mongoService);

    // web3jService.createApps();
    // }
}
