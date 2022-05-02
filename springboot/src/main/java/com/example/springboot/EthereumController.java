package com.example.springboot;

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

    @Value("${ethereum.contractAddress}")
    String contractAddress;

    @GetMapping("ethereum/contract-address")
    public String getContractAddress() {
        return contractAddress;
    }

    @GetMapping("ethereum/register-event-listener")
    public String registerEventListener() {
        logger.debug("Receiving app name");

        web3jService = new Web3jService("test", web3j);

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
            web3jService.registerEventListener(contractAddress, BigInteger.valueOf(12000000L),
                    BigInteger.valueOf(12216838L),
                    contract);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("ethereum/get-apps")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getApps(@RequestParam String query)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        web3jService = new Web3jService("test", web3j);

        String apps = web3jService.getApps(query);
        return apps;
    }
}
