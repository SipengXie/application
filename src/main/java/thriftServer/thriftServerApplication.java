package thriftServer;

import application.javaRpcToken.javaRpcToken;
import thriftServer.server.ThriftServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class thriftServerApplication {

    static void createTokenFile(String name) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", name);
        String token = javaRpcToken.createJavaRpcToken(map);
        BufferedWriter out = new BufferedWriter(new FileWriter("token"));
        out.write(token);
        out.close();
        System.out.println("Successfully create token file at:" + Paths.get("token"));
    }

    public static void main(String[] args) {
        ThriftServer thriftServer = new ThriftServer();
        String ccp = "D:\\hyperledgerFabric\\fabric-samples\\test-network\\organizations\\peerOrganizations\\org1.example.com\\connection-org1.yaml";
        String channelName = "mychannel";
        String chaincodeName = "record";
        String name = "RpcServer";
        try {
            createTokenFile(name);
            thriftServer.start(ccp, name, channelName, chaincodeName);
        } catch (Exception e) {
            System.err.println("Cannot start thrift server: " + e);
        }
    }
}
