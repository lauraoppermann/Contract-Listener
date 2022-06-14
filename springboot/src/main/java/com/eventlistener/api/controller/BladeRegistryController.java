package com.eventlistener.api.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

import com.bladeregisty.BladeRegistry;
import com.eventlistener.api.db.BladeApp;
import com.eventlistener.api.services.BladeRegistryService;
import com.eventlistener.api.services.MongoDBService;

@RestController
public class BladeRegistryController {
    private static final Logger logger = LoggerFactory.getLogger(BladeRegistryController.class);

    @Autowired
    MongoDBService mongoService;

    private Web3j web3j = Web3j.build(new HttpService("http://geth:8545"));

    BladeRegistryService bladeRegistryService;

    @Value("${ethereum.blade-registry.contractAddress}")
    String contractAddress;

    @GetMapping("blade/contract-address")
    public String getContractAddress() {
        return contractAddress;
    }

    @EventListener(ApplicationReadyEvent.class)
    @PostMapping("blade/marketplace/register-event-listener")
    public String registerEventListener() {
        logger.debug("Receiving app name");

        BladeRegistryService bladeRegistryService = new BladeRegistryService("test", web3j, mongoService);

        String result = "";

        try {

            EthBlock latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger gasLimit = latestBlock.getBlock().getGasLimit();

            final BigInteger gasPrice = BigInteger.valueOf(2205000); // value to be chosen
            final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            Credentials credentials = Credentials.create(ecKeyPair);

            TransactionManager tManager = new RawTransactionManager(web3j, credentials);
            final BladeRegistry contract = BladeRegistry.load(contractAddress, web3j, tManager, gasProvider);

            // find a better end and startblock value, LATEST didnt work as far as I
            // remember
            bladeRegistryService.registerEventListener(contractAddress, BigInteger.valueOf(12100000L),
                    BigInteger.valueOf(12258799L),
                    contract);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("blade/marketplace/get-all-apps")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BladeApp> getAllApps()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        List<BladeApp> apps = mongoService.findAll();
        return apps;
    }

    @GetMapping("blade/marketplace/get-apps-filtered")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BladeApp> getAppsFullTextSearch(@RequestParam String query)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        List<BladeApp> apps = mongoService.fullTextSearch(query);
        return apps;
    }

    // For evaluation purposes
    @GetMapping("create-200-dummy-apps")
    public void create200dummies() {
        for (int i = 0; i < 200; i++) {
            BladeApp app = new BladeApp("test" + UUID.randomUUID().toString(), "test" + i, "test" + i, "test" + i,
                    "test" + i);
            mongoService.addApp(app);
        }
    }

}
