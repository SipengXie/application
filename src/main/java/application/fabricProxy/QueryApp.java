package application.fabricProxy;

import org.hyperledger.fabric.gateway.Contract;
/*
 查询服务类，继承服务主类
 */
public class QueryApp extends App {

    Contract contract;
    public QueryApp(String ncp, String name, String channelName, String chaincodeName) throws Exception{
        super(ncp, name, channelName, chaincodeName);
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract in queryApp:"+ e);
        }
    }
/*
 发送评估交易请求
 */
    public String evaluateTransaction(String funcName, String[] args) throws Exception {
        byte[] result;
        try {
            result = contract.evaluateTransaction(funcName, args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction: " + funcName + " " + e);
        }
        return new String(result);
    }
}
