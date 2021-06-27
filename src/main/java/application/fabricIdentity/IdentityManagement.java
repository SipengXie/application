package application.fabricIdentity;

import java.util.Formatter;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

/*
 该类为身份分发的实际服务类，提供与身份有关的所有服务，构造函数将用于读取配置文件信息
 */
public class IdentityManagement {
    public IdentityManagement(String caPemFile, String caAddress, String adminPwd,
                              String mspId, String userName, String department) {
        this.caPemFile = caPemFile;
        this.caAddress = caAddress;
        this.adminPwd = adminPwd;
        this.mspId = mspId;
        this.userName = userName;
        this.department = department;
    }

    private String caPemFile = null;
    private String caAddress = null;
    private String adminPwd = null;
    private String mspId = null;
    private String userName = null;
    private String department = null;

    private Wallet wallet = null;
    private HFCAClient caClient = null;
/*
 创建钱包文件夹，钱包文件夹将用于创造身份，同时也创造了和Fabric CA server相沟通的caClient
 */
    public void createWallet() throws Exception{
        Properties props = new Properties();
        props.put("pemFile", caPemFile);
        props.put("allowAllHostNames", "true");
        caClient = HFCAClient.createNewInstance(caAddress, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
    }
/*
 将管理员身份分发注册进去，管理员身份已经在CA中配置完毕，此处通过传入密码的方式将管理员身份置入钱包中
 钱包中的管理员身份将用于分发后续终端用户的身份
 */
    public void enrollAdmin() throws Exception {
        if (caPemFile == null || caAddress == null || adminPwd == null || mspId == null)
            throw new Exception("Configuration incomplete");

        if (wallet.get("admin") != null) {
            throw new Exception("An identity for the admin user \"admin\" already exists in the wallet");
        }
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", adminPwd, enrollmentRequestTLS);

        Identity admin = Identities.newX509Identity(mspId, enrollment);
        wallet.put("admin", admin);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }
/*
 分发终端用户的身份，需要用到管理员身份
 */
    public void registerUser() throws Exception {
        if (caPemFile == null || caAddress == null || userName == null || department == null)
            throw new Exception("Configuration incomplete");

        if (wallet.get(userName) != null) {
            Formatter fmt = new Formatter();
            fmt.format("An identity for the user \"%s\" already exists in the wallet\n", userName);
            throw new Exception(fmt.toString());
        }

        X509Identity adminIdentity = (X509Identity)wallet.get("admin");
        if (adminIdentity == null) {
            throw new Exception("\"admin\" needs to be enrolled and added to the wallet first");
        }

        User admin = new User() {
            @Override
            public String getName() {
                return "admin";
            }

            @Override
            public Set<String> getRoles() {
                return null;
            }

            @Override
            public String getAccount() {
                return null;
            }

            @Override
            public String getAffiliation() {
                return department;
            }

            @Override
            public Enrollment getEnrollment() {
                return new Enrollment() {

                    @Override
                    public PrivateKey getKey() {
                        return adminIdentity.getPrivateKey();
                    }

                    @Override
                    public String getCert() {
                        return Identities.toPemString(adminIdentity.getCertificate());
                    }
                };
            }

            @Override
            public String getMspId() {
                return adminIdentity.getMspId();
            }

        };

        RegistrationRequest registrationRequest = new RegistrationRequest(userName);
        registrationRequest.setAffiliation(department);
        registrationRequest.setEnrollmentID(userName);
        String enrollmentSecret = caClient.register(registrationRequest, admin);
        Enrollment enrollment = caClient.enroll(userName, enrollmentSecret);
        Identity user = Identities.newX509Identity(adminIdentity.getMspId(), enrollment);
        wallet.put(userName, user);
        System.out.printf("Successfully enrolled user \"%s\" and imported it into the wallet\n", userName);
    }
}
