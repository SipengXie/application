package application.fabricProxy;

import org.hyperledger.fabric.gateway.Contract;

public class updateApp extends App{

    public updateApp(String ccp, String name, String channelName, String chaincodeName) {
        super(ccp, name, channelName, chaincodeName);
    }

    public void initDataRecord(String[] args) throws Exception {
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
          throw new Exception("Fail to get connect and get contract");
        }
        try {
            contract.submitTransaction("initDataRecord", args);
        } catch (Exception e) {
            throw new Exception("Fail to submit transaction");
        }
        super.closeGateway();
        System.out.println("Successfully Submit a Transaction: create a data record");
    }

    public void initUserRecord(String[] args) throws Exception {
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract");
        }
        try {
            contract.submitTransaction("initUserRecord", args);
        } catch (Exception e) {
            throw new Exception("Fail to submit transaction");
        }
        super.closeGateway();
        System.out.println("Successfully Submit a Transaction: create a user record");
    }

    public void modifyDataRecord(String[] args) throws Exception {
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract");
        }
        try {
            contract.submitTransaction("modifyDataRecord", args);
        } catch (Exception e) {
            throw new Exception("Fail to submit transaction");
        }
        super.closeGateway();
        System.out.println("Successfully Submit a Transaction: modify a data record");
    }

    public void modifyUserRecord(String[] args) throws Exception {
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract");
        }
        try {
            contract.submitTransaction("modifyUserRecord", args);
        } catch (Exception e) {
            throw new Exception("Fail to submit transaction");
        }
        super.closeGateway();
        System.out.println("Successfully Submit a Transaction: modify a user record");
    }
}
