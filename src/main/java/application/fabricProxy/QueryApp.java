package application.fabricProxy;

import org.hyperledger.fabric.gateway.Contract;

public class QueryApp extends App {

    Contract contract;
    public QueryApp(String ccp, String name, String channelName, String chaincodeName) throws Exception{
        super(ccp, name, channelName, chaincodeName);
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract in queryApp:"+ e);
        }
    }

    public String evaluateTransaction(String funcName, String[] args) throws Exception {
        byte[] result;
        try {
            result = contract.submitTransaction(funcName, args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction: " + funcName + " " + e);
        }
        return new String(result);
    }
}
