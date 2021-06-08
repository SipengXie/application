package application.fabricProxy;

import org.hyperledger.fabric.gateway.Contract;

import java.util.Arrays;

public class UpdateApp extends App{

    Contract contract;
    public UpdateApp(String ccp, String name, String channelName, String chaincodeName) throws Exception {
        super(ccp, name, channelName, chaincodeName);
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract in updateApp:"+ e);
        }
    }

    public void submitTransaction(String funcName, String[] args) throws Exception {
        try {
            contract.submitTransaction(funcName, args);
        } catch (Exception e) {
            throw new Exception("Fail to submit transaction: " + funcName + " " + e);
        }
    }
}
