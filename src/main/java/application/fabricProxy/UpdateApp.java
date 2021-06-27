package application.fabricProxy;

import org.hyperledger.fabric.gateway.Contract;
/*
 更新服务类，继承主类
 */

public class UpdateApp extends App{

    Contract contract;
    public UpdateApp(String ncp, String name, String channelName, String chaincodeName) throws Exception {
        super(ncp, name, channelName, chaincodeName);
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract in updateApp:"+ e);
        }
    }
/*
 发送提交交易请求
 */
    public void submitTransaction(String funcName, String[] args) throws Exception {
        try {
            contract.submitTransaction(funcName, args);
        } catch (Exception e) {
            throw new Exception("Fail to submit transaction: " + funcName + " " + e);
        }
    }
}
