package thriftServer.server;

import application.fabricService.RpcService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.layered.TFramedTransport;
import thriftServer.service.RpcServiceImp;

public class ThriftServer {

    private int port;
    public void setPort(int p) { port = p; }

    public ThriftServer() {
        port = 25565;
    }

    public void start() throws Exception{
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
        TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverTransport);
        serverArgs.protocolFactory(new TBinaryProtocol.Factory());
        serverArgs.transportFactory(new TFramedTransport.Factory());
        RpcService.Processor<RpcServiceImp> processor = new RpcService.Processor<>(new RpcServiceImp());
        serverArgs.processor(processor);
        TNonblockingServer server = new TNonblockingServer(serverArgs);

        System.out.printf("Thrift server stared! Port: %d \n",port);
        server.serve();
    }
}
