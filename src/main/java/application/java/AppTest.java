package application.java;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.time.LocalDateTime;

public class AppTest {

    @Test
    public static void appTest () {
        /* Test environment and parameters */
        String caPemFilePath = "D:\\hyperledgerFabric\\fabric-samples\\test-network\\organizations\\peerOrganizations\\org1.example.com\\ca\\ca.org1.example.com-cert.pem";
        String caAddress = "https://localhost:7054";
        String adminPassword = "adminpw";
        String mspId = "Org1MSP";
        String userName = "Jack";
        String department = "org1.department1";
        String ccp = "D:\\hyperledgerFabric\\fabric-samples\\test-network\\organizations\\peerOrganizations\\org1.example.com\\connection-org1.yaml";
        String channelName = "mychannel";
        String chaincodeName = "record";

        System.out.println("============ Test Begins ===========");
        System.out.println("============ Test IdentityManagement Begins ===========");
        IdentityManagement IM = new IdentityManagement();

        // set basic information
        IM.setAdminPwd(adminPassword);
        IM.setCaAddress(caAddress);
        IM.setCaPemFile(caPemFilePath);
        IM.setUserName(userName);
        IM.setDepartment(department);
        IM.setMspId(mspId);

        try {
            IM.setWallet();
            IM.enrollAdmin();
            IM.registerUser();
        } catch (Exception e) {
            System.out.println("IdentityManagement failed:  " + e);
        }
        System.out.println("=========== Test IdentityManagement Ends ===========");
        System.out.println("=========== Test App Begins ===========");

        App client = new App();
        client.setChannelName(channelName);
        client.setCCP(ccp);
        client.setUserName(userName);
        client.setChaincodeName(chaincodeName);

        try {
            client.connect();
            String result;
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            LocalDateTime currentTime = LocalDateTime.now();

            client.initUserRecord(new String[]{"Department1","Jack","user1.id1","operator"});
            result = client.queryUserRecordByAddr(new String[]{"user1.id1"});
            System.out.println(result);

            client.initDataRecord(new String[]{uuid,"Department1","Jack","table1.id1","add",currentTime.toString()});
            result = client.queryDataRecordById(new String[]{uuid});
            System.out.println(result);

            client.modifyDataRecord(new String[]{uuid,"Andy",currentTime.toString(),"true","department2"});
            result = client.queryDataRecordByObject(new String[]{"table1.id1"});
            System.out.println(result);

            client.modifyDataRecord(new String[]{uuid,currentTime.toString(),"[contentHash]"});
            result = client.queryDataRecordByUser(new String[]{"Department1","Jack"});
            System.out.println(result);

            client.modifyUserRecord(new String[]{"Department1","Jack","user1.id1","manager"});
            result = client.queryUserRecordByDept(new String[]{"Department1"});
            System.out.println(result);

            result = client.queryWithQueryString(new String[]{"{\"selector\":{\"docType\":\"dataRecord\"}}"});
            System.out.println(result);

        } catch (Exception e) {
            System.err.println("App failed: " + e);
        }

        client.close();

        System.out.println("============ Test Ends ===========");
    }
    public static void main(String[] args) {
        appTest();
    }
}