package application.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class App {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private Path networkConfigPath = null;
    private String userName = null;
    private String channelName = null;
    private String chaincodeName = null;
    private  Gateway gateway = null;

    private Contract getContract() {
        Network network = gateway.getNetwork(channelName);
        return network.getContract(chaincodeName);
    }

    public void setCCP(String ccp) {
        networkConfigPath  = Paths.get(ccp);
    }

    public void setUserName(String name) { userName = name; }

    public void setChannelName(String cn) {
        channelName = cn;
    }

    public void setChaincodeName(String ccn) {
        chaincodeName = ccn;
    }

    // Errors during connecting to gateway should be processed by upper layer
    public void connect() throws Exception {
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
        gateway =  builder.connect();
    }

    public void initDataRecord(String[] args) throws Exception {
        Contract contract = getContract();
        System.out.println("Submit Transaction: create a data record");
        contract.submitTransaction("initDataRecord", args);
    }

    public void initUserRecord(String[] args) throws Exception {
        Contract contract = getContract();
        System.out.println("Submit Transaction: create a user record");
        contract.submitTransaction("initUserRecord", args);
    }

    public void modifyDataRecord(String[] args) throws Exception {
        Contract contract = getContract();
        System.out.println("Submit Transaction: modify a data record");
        contract.submitTransaction("modifyDataRecord", args);
    }

    public void modifyUserRecord(String[] args) throws Exception {
        Contract contract = getContract();
        System.out.println("Submit Transaction: modify a user record");
        contract.submitTransaction("modifyUserRecord", args);
    }

    public String queryWithQueryString(String[] args) throws Exception {
        byte[] result;
        Contract contract = getContract();
        System.out.println("Evaluate Transaction: query data records by query string");
        result = contract.submitTransaction("queryWithQueryString", args);
        return new String(result);
    }

    public String queryDataRecordByObject(String[] args) throws Exception {
        byte[] result;
        Contract contract = getContract();
        System.out.println("Evaluate Transaction: query data records by object");
        result = contract.evaluateTransaction("queryDataRecordByObject", args);
        return new String(result);
    }

    public  String queryDataRecordByUser(String[] args) throws Exception {

        byte[] result;
        Contract contract = getContract();
        System.out.println("Evaluate Transaction: query data records by user");
        result = contract.evaluateTransaction("queryDataRecordByUser", args);
        return new String(result);

    }

    public  String queryDataRecordById(String[] args) throws Exception {
        byte[] result;
        Contract contract = getContract();
        System.out.println("Evaluate Transaction: query data records by id");
        result = contract.evaluateTransaction("queryDataRecordById", args);
        return new String(result);
    }

    public  String queryUserRecordByDept(String[] args) throws Exception {
        byte[] result;
        Contract contract = getContract();
        System.out.println("Evaluate Transaction: query user records by department");
        result = contract.evaluateTransaction("queryUserRecordByDept", args);
        return new String(result);
    }

    public  String queryUserRecordByAddr(String[] args) throws Exception {
        byte[] result;
        Contract contract = getContract();
        System.out.println("Evaluate Transaction: query user records by address");
        result = contract.evaluateTransaction("queryUserRecordByAddr", args);
        return new String(result);
    }
}
