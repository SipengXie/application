package thriftServer;

import application.fabricIdentity.identityDistribute;
import application.fabricProxy.UpdateApp;
import org.yaml.snakeyaml.Yaml;
import thriftServer.javaRpcToken.javaRpcToken;
import thriftServer.server.ThriftServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/*
 Thrift服务主类
 */
public class thriftServerApplication {
// 创建RPC Token
    static void createTokenFile(String uuid, String tokenPath) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        String token = javaRpcToken.createJavaRpcToken(map);
        BufferedWriter out = new BufferedWriter(new FileWriter(tokenPath));
        out.write(token);
        out.close();
        System.out.println("Successfully create token file at: " + tokenPath);
    }
// 读取配置文件
    public static String[] getConfiguration(String path) {
        Yaml yaml = new Yaml();
        InputStream in = null;
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = yaml.loadAs(in, Map.class);
        String ncp = map.get("ncp").toString();
        String userName = map.get("userName").toString();
        String channelName = map.get("channelName").toString();
        String chaincodeName = map.get("chaincodeName").toString();
        String tokenPath = map.get("tokenPath").toString();
        return new String[]{ncp, userName, channelName, chaincodeName, tokenPath};
    }

    public static void main(String[] args) {
        ThriftServer thriftServer = new ThriftServer();
        //String [] config = getConfiguration(args[0]);
        String [] config = getConfiguration("Configuration.yaml");
        String uuid = UUID.randomUUID().toString();
        try {
            createTokenFile(uuid, config[4]);
            thriftServer.start(config[0],config[1],config[2],config[3],uuid);
        } catch (Exception e) {
            System.err.println("Cannot start thrift server: " + e);
        } finally {
            thriftServer.stop();
        }
    }
}
