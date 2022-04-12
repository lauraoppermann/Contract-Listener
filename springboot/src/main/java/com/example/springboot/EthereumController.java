package com.example.springboot;

import java.io.IOException;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.appregistry.AppRegistry;

@RestController
public class EthereumController {
    private static final Logger logger = LoggerFactory.getLogger(EthereumController.class);

    @Autowired
    private Web3j web3j = Web3j.build(new UnixIpcService("/home/lauraoppermann/.ethereum/ropsten/geth.ipc"));
    Web3jService web3jService;

    @GetMapping("ethereum/peers")
    public String peers() {
        logger.debug("Trying to get peer count");

        Integer peers = 0;
        try {
            peers = this.web3j.netPeerCount().send().getQuantity().intValue();
        } catch (IOException e) {
            logger.debug("Exception with web3j", e);
            e.printStackTrace();
        }
        return "You are connected to " + peers + " peer(s)";
    }

    @GetMapping("ethereum/app-registry/app-name")
    public String appName() {
        logger.debug("Receiving app name");

        String contractAddress = "0xBe6A024148C63C2abBE5685CbeF3603089Ab8727";
        // String contractAddress = "0xdbb7e1aa6c01f14827a19446a31f3aee882619a9";
        String result = "";
        try {

            EthBlock latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            logger.info(latestBlock.toString());
            BigInteger gasLimit = latestBlock.getBlock().getGasLimit();

            final BigInteger gasPrice = BigInteger.valueOf(2205000); // value to be chosen
            final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            Credentials credentials = Credentials.create(ecKeyPair);

            TransactionManager tManager = new RawTransactionManager(web3j, credentials);

            final AppRegistry contract = AppRegistry.load(contractAddress, web3j, tManager, gasProvider);

            result = contract.getAppID("Test").send();
        } catch (IOException e) {
            logger.debug("Exception with web3j", e);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("ethereum/register-event-listener")
    public String registerEventListener() {
        logger.debug("Receiving app name");

        web3jService = new Web3jService("test", web3j);

        String contractAddress = "0xBe6A024148C63C2abBE5685CbeF3603089Ab8727";
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

            web3jService.registerEventListener(contractAddress, BigInteger.ZERO, BigInteger.valueOf(9999L), contract);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("ethereum/get-apps")
    public String getApps() {
        web3jService = new Web3jService("test", web3j);

        String apps = web3jService.getApps();
        return apps;
    }
}
