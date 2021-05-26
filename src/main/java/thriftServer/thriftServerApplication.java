package thriftServer;

import thriftServer.javaRpcToken.javaRpcToken;
import thriftServer.server.ThriftServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class thriftServerApplication {

    static void createTokenFile(String uuid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        String token = javaRpcToken.createJavaRpcToken(map);
        BufferedWriter out = new BufferedWriter(new FileWriter("D:\\RpcToken"));
        out.write(token);
        out.close();
        System.out.println("Successfully create token file at: D:\\RpcToken");
    }

    public static void main(String[] args) {
        ThriftServer thriftServer = new ThriftServer();
        String ccp = "D:\\hyperledgerFabric\\fabric-samples\\test-network\\organizations\\peerOrganizations\\org1.example.com\\connection-org1.yaml";
        String channelName = "mychannel";
        String chaincodeName = "record";
        String name = "RpcServer";
        UUID uuid = UUID.randomUUID();
        try {
            createTokenFile(uuid.toString());
            thriftServer.start(ccp, name, channelName, chaincodeName, uuid.toString());
        } catch (Exception e) {
            System.err.println("Cannot start thrift server: " + e);
        }
    }
}
