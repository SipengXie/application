package thriftServer.service;

import application.java.App;
import application.fabricQueryService.RpcQueryService;
import org.apache.thrift.TException;

public class RpcQueryServiceImp implements RpcQueryService.Iface {

    private App appClient = new App();
    @Override
    public void setCCP(String ccp) {
        appClient.setCCP(ccp);
    }

    @Override
    public void setUserName(String userName) {
        appClient.setUserName(userName);
    }

    @Override
    public void setChaincodeName(String chaincodeName) {
        appClient.setChaincodeName(chaincodeName);
    }

    @Override
    public void setChannelName(String channelName) {
        appClient.setChannelName(channelName);
    }

    @Override
    public void connect() throws TException {
        try {
            appClient.connect();
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void close() {
        appClient.close();
    }

    @Override
    public String queryDataRecordByObject(String object) throws TException {
        try {
            return appClient.queryDataRecordByObject(new String[]{object});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryDataRecordByUser(String department, String user) throws TException {
        try {
            return appClient.queryDataRecordByUser(new String[]{department, user});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
