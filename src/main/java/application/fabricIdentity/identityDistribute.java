package application.fabricIdentity;

public class identityDistribute {

    @org.junit.jupiter.api.Test
    public static void appTest () {
        /* Test environment and parameters */
        String caPemFilePath = "D:\\hyperledgerFabric\\fabric-samples\\test-network\\organizations\\peerOrganizations\\org1.example.com\\ca\\ca.org1.example.com-cert.pem";
        String caAddress = "https://localhost:7054";
        String adminPassword = "adminpw";
        String mspId = "Org1MSP";
        String userName = "RpcServer";
        String department = "org1.department1";

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

        System.out.println("============ Test Ends ===========");

    }
    public static void main(String[] args) {
        appTest();
    }
}