package thriftServer.server;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import thriftServer.fabricRpcService.QueryService;
import thriftServer.fabricRpcService.UpdateService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.transport.layered.TFramedTransport;
import thriftServer.service.queryServiceImp;
import thriftServer.service.updateServiceImp;
/*
 Thrift RPC的服务端，使用的TCompact编码协议，TFramed传输协议，支持多线程服务
 */
public class ThriftServer {

    private final int port;
    private TServer server;

    public ThriftServer() {
        port = 25565;
    }

    public void start(String ncp, String name, String channelName, String chaincodeName, String uuid) throws Exception{

        TServerSocket serverSocket=new TServerSocket(port);
        TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverSocket);
        serverArgs.protocolFactory(new TCompactProtocol.Factory());
        serverArgs.transportFactory(new TFramedTransport.Factory());

        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        QueryService.Processor<queryServiceImp> queryServiceImpProcessor = new QueryService.Processor<>(new queryServiceImp(
                ncp, name, channelName, chaincodeName, uuid));
        UpdateService.Processor<updateServiceImp> updateServiceImpProcessor = new UpdateService.Processor<>(new updateServiceImp(
                ncp, name, channelName, chaincodeName, uuid));
        processor.registerProcessor("queryService",queryServiceImpProcessor);
        processor.registerProcessor("updateService",updateServiceImpProcessor);
        serverArgs.processor(processor);

        server = new TThreadPoolServer(serverArgs);
        System.out.printf("Thrift server stared! Port: %d \n",port);
        server.serve();
    }

    public void stop() {
        server.stop();
    }
}
