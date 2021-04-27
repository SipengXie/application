package thriftServer.server;

import application.fabricService.AppService;
import application.fabricService.ImService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.layered.TFramedTransport;
import thriftServer.service.AppServiceImp;
import thriftServer.service.ImServiceImp;

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
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        AppService.Processor<AppServiceImp> appServiceImpProcessor = new AppService.Processor<>(new AppServiceImp());
        ImService.Processor<ImServiceImp> imServiceImpProcessor = new ImService.Processor<>(new ImServiceImp());
        processor.registerProcessor("AppService",appServiceImpProcessor);
        processor.registerProcessor("ImService",imServiceImpProcessor);
        serverArgs.processor(processor);
        TNonblockingServer server = new TNonblockingServer(serverArgs);

        System.out.printf("Thrift server stared! Port: %d \n",port);
        server.serve();
    }
}
