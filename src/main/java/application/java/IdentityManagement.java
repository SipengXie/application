package application.java;

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


public class IdentityManagement {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    private String caPemFile = null;
    private String caAddress = null;
    private String adminPwd = null;
    private String mspId = null;
    private String userName = null;
    private String department = null;
    private Wallet wallet = null;
    private HFCAClient caClient = null;

    public void setCaPemFile(String pemFile) {
        caPemFile = pemFile;
    }

    public void setCaAddress(String address) {
        caAddress = address;
    }

    public void setAdminPwd(String password) {
        adminPwd = password;
    }

    public void setMspId(String Id) {
        mspId = Id;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public void setDepartment(String dept) {
        department = dept;
    }

    public void setWallet() throws Exception{
        Properties props = new Properties();
        props.put("pemFile", caPemFile);
        props.put("allowAllHostNames", "true");
        caClient = HFCAClient.createNewInstance(caAddress, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
    }

    public void enrollAdmin() throws Exception {
        if (caPemFile == null || caAddress == null || adminPwd == null || mspId == null)
            throw new Exception("Configuration incomplete");

        if (wallet.get("admin") != null) {
            throw new Exception("An identity for the admin user \"admin\" already exists in the wallet");
        }
        // TODO：we can use the org's own TLS cert, thus may need more parameters.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", adminPwd, enrollmentRequestTLS);

        Identity admin = Identities.newX509Identity(mspId, enrollment);
        wallet.put("admin", admin);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }

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
