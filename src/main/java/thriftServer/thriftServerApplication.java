package thriftServer;

import thriftServer.server.ThriftServer;

public class thriftServerApplication {
    public static void main(String[] args) {
        ThriftServer thriftServer = new ThriftServer();
        try {
            thriftServer.start();
        } catch (Exception e) {
            System.err.println("Cannot start thrift server: " + e);
        }
    }
}
