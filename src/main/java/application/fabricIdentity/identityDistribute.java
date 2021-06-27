package application.fabricIdentity;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
/*
 身份分发功能的主类，将调用服务类IdentityManagement，生成wallet文件夹并发放对应证书
 */
public class identityDistribute {
/*
 获取服务类
 */
    public static IdentityManagement getIm(String path) {
        Yaml yaml = new Yaml();
        InputStream in = null;
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully load the config file");
        Map<String, Object> map = yaml.loadAs(in, Map.class);
        String caPemFilePath = map.get("caPemFilePath").toString();
        String caAddress = map.get("caAddress").toString();
        String adminPassword = map.get("adminPassword").toString();
        String mspId = map.get("mspId").toString();
        String userName = map.get("userName").toString();
        String department = map.get("department").toString();
        return new IdentityManagement(caPemFilePath, caAddress, adminPassword, mspId, userName, department);
    }
/*
 分发身份
 */
    public static void Distribute (IdentityManagement IM) {
        try {
            IM.createWallet();
            IM.enrollAdmin();
            IM.registerUser();
        } catch (Exception e) {
            System.out.println("IdentityManagement failed:  " + e);
        }

    }
    public static void main(String[] args) {
        IdentityManagement im = getIm("identityConfig.yaml");
        //IdentityManagement im = getIm(args[0]);
        Distribute(im);
    }
}