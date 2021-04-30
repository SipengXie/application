package thriftServer.service;

import application.fabricProxy.queryApp;
import application.fabricRpcService.queryService;
import application.javaRpcToken.javaRpcToken;
import org.apache.thrift.TException;

public class queryServiceImp implements queryService.Iface {

    private final queryApp proxy;
    public queryServiceImp(String ccp, String name, String channelName, String chaincodeName) {
        proxy = new queryApp(ccp, name, channelName, chaincodeName);
    }

    boolean checkToken(String token) {
        return javaRpcToken.parserJavaRpcToken(token) == null;
    }

    @Override
    public String queryDataRecordById(String uuid, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.queryDataRecordById(new String[]{uuid});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryDataRecordByObject(String object, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.queryDataRecordByObject(new String[]{object});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryDataRecordByUser(String department, String user, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.queryDataRecordByUser(new String[]{department, user});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryUserRecordByDept(String department, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.queryUserRecordByDept(new String[]{department});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryUserRecordByAddr(String address, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.queryUserRecordByAddr(new String[]{address});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryWithQueryString(String queryString, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.queryWithQueryString(new String[]{queryString});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
