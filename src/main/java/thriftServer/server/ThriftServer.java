package thriftServer.server;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TServerSocket;
import thriftServer.fabricRpcService.queryService;
import thriftServer.fabricRpcService.updateService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.layered.TFramedTransport;
import thriftServer.service.queryServiceImp;
import thriftServer.service.updateServiceImp;

public class ThriftServer {

    private final int port;
    // public void setPort(int p) { port = p; }

    public ThriftServer() {
        port = 25565;
    }

    public void start(String ccp, String name, String channelName, String chaincodeName, String uuid) throws Exception{

        TServerSocket serverSocket=new TServerSocket(port);
        TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverSocket);
        //TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
        //TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverTransport);
        //serverArgs.protocolFactory(new TBinaryProtocol.Factory());
        serverArgs.protocolFactory(new TCompactProtocol.Factory());
        serverArgs.transportFactory(new TFramedTransport.Factory());

        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        queryService.Processor<queryServiceImp> queryServiceImpProcessor = new queryService.Processor<>(new queryServiceImp(
                ccp, name, channelName, chaincodeName, uuid));
        updateService.Processor<updateServiceImp> updateServiceImpProcessor = new updateService.Processor<>(new updateServiceImp(
                ccp, name, channelName, chaincodeName, uuid));
        processor.registerProcessor("queryService",queryServiceImpProcessor);
        processor.registerProcessor("updateService",updateServiceImpProcessor);

        serverArgs.processor(processor);

        TServer server = new TThreadPoolServer(serverArgs);
        //TNonblockingServer server = new TNonblockingServer(serverArgs);

        System.out.printf("Thrift server stared! Port: %d \n",port);
        server.serve();
    }
}
