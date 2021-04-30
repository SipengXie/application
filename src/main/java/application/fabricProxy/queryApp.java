package application.fabricProxy;

import org.hyperledger.fabric.gateway.Contract;

public class queryApp extends App {

    public queryApp(String ccp, String name, String channelName, String chaincodeName) {
        super(ccp, name, channelName, chaincodeName);
    }

    public String queryWithQueryString(String[] args) throws Exception {
        byte[] result;
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract", e);
        }
        try {
            result = contract.submitTransaction("queryWithQueryString", args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction", e);
        }
        super.closeGateway();
        System.out.println("Successfully Evaluate a Transaction: query data records by query string:" + args[0]);
        return new String(result);
    }

    public String queryDataRecordByObject(String[] args) throws Exception {
        byte[] result;
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract", e);
        }
        try {
            result = contract.evaluateTransaction("queryDataRecordByObject", args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction", e);
        }
        super.closeGateway();
        System.out.println("Successfully Evaluate a Transaction: query data records by object:" + args[0]);
        return new String(result);
    }

    public String queryDataRecordByUser(String[] args) throws Exception {
        byte[] result;
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract", e);
        }
        try {
            result = contract.evaluateTransaction("queryDataRecordByUser", args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction", e);
        }
        super.closeGateway();
        System.out.println("Successfully Evaluate a Transaction: query data records by user:" + args[0] + args[1]);
        return new String(result);
    }

    public String queryDataRecordById(String[] args) throws Exception {
        byte[] result;
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract", e);
        }
        try {
            result = contract.evaluateTransaction("queryDataRecordById", args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction", e);
        }
        super.closeGateway();
        System.out.println("Successfully Evaluate a Transaction: query data records by id:" + args[0]);
        return new String(result);
    }

    public String queryUserRecordByDept(String[] args) throws Exception {
        byte[] result;
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract", e);
        }
        try {
            result = contract.evaluateTransaction("queryUserRecordByDept", args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction", e);
        }
        super.closeGateway();
        System.out.println("Succes/sfully Evaluate a Transaction: query user records by department:" + args[0]);
        return new String(result);
    }

    public String queryUserRecordByAddr(String[] args) throws Exception {
        byte[] result;
        Contract contract;
        try {
            contract = super.connectAndGetContract();
        } catch (Exception e) {
            throw new Exception("Fail to get connect and get contract", e);
        }
        try {
            result = contract.evaluateTransaction("queryUserRecordByAddr", args);
        } catch (Exception e) {
            throw new Exception("Fail to evaluate transaction", e);
        }
        super.closeGateway();
        System.out.println("Successfully Evaluate a Transaction: query user records by address" + args[0]);
        return new String(result);
    }
}
