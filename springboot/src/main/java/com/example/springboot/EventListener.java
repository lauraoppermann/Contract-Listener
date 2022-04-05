package com.example.springboot;

import java.io.IOException;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.ipc.UnixIpcService;

import com.example.springboot.Web3jService;

@RestController
public class EventListener {
    // https://github.com/eventeum/eventeum/blob/master/core/src/main/java/net/consensys/eventeum/chain/contract/ContractEventListener.java
    // https://ethereum.stackexchange.com/questions/16697/web3j-listening-to-events
    // https://github.com/FundRequest/Azrael/blob/develop/worker/src/main/java/io/fundrequest/azrael/worker/events/listener/FundRequestPlatformEventListener.java
    // Web3j Flowable, Filter?
    private static final Logger logger = LoggerFactory.getLogger(EthereumController.class);

    @Autowired
    private Web3j web3j = Web3j.build(new UnixIpcService("/home/lauraoppermann/.ethereum/ropsten/geth.ipc"));

    void onEvent() {

    }

    Web3jService web3jService;

    // @GetMapping("ethereum/register-event-listener")
    // public String peers() {
    // logger.debug("Receiving app name");

    // web3jService = new Web3jService("test", web3j);

    // String contractAddress = "0xBe6A024148C63C2abBE5685CbeF3603089Ab8727";

    // try {
    // // latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST,
    // // false).send();
    // web3jService.registerEventListener(contractAddress, BigInteger.ZERO,
    // BigInteger.valueOf(9999L));
    // // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // // e.printStackTrace();

    // // logger.info(latestBlock.toString());

    // // logger.debug("Exception with web3j", e);
    // // e.printStackTrace();
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return result;
    // }
}
