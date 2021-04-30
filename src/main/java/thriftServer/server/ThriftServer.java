package thriftServer.server;

import application.fabricRpcService.queryService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.layered.TFramedTransport;
import thriftServer.service.queryServiceImp;

public class ThriftServer {

    private int port;
   // public void setPort(int p) { port = p; }

    public ThriftServer() {
        port = 25565;
    }

    public void start(String ccp, String name, String channelName, String chaincodeName, String uuid) throws Exception{
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
        TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverTransport);
        serverArgs.protocolFactory(new TBinaryProtocol.Factory());
        serverArgs.transportFactory(new TFramedTransport.Factory());
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        queryService.Processor<queryServiceImp> queryServiceImpProcessor = new queryService.Processor<>(new queryServiceImp(
               ccp, name, channelName, chaincodeName, uuid));
       // ImService.Processor<ImServiceImp> imServiceImpProcessor = new ImService.Processor<>(new ImServiceImp());
        processor.registerProcessor("queryService",queryServiceImpProcessor);
       // processor.registerProcessor("ImService",imServiceImpProcessor);
        serverArgs.processor(processor);
        TNonblockingServer server = new TNonblockingServer(serverArgs);

        System.out.printf("Thrift server stared! Port: %d \n",port);
        server.serve();
    }
}
