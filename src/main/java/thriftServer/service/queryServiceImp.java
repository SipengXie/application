package thriftServer.service;

import application.fabricProxy.QueryApp;
import thriftServer.fabricRpcService.QueryService;
import thriftServer.javaRpcToken.javaRpcToken;
import org.apache.thrift.TException;

import java.util.Map;

public class queryServiceImp implements QueryService.Iface {

    private final QueryApp proxy;
    private final String uuid;
    public queryServiceImp(String ccp, String name, String channelName, String chaincodeName, String uuid) throws Exception{
        proxy = new QueryApp(ccp, name, channelName, chaincodeName);
        this.uuid = uuid;
    }

    boolean checkToken(String token) {
        Map<String, Object> m = javaRpcToken.parserJavaRpcToken(token);
        return (m == null) || !(m.get("uuid").equals(uuid));
    }

    @Override
    public String queryById(String id, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordById", new String[]{id});
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
            return proxy.evaluateTransaction("queryWithQueryString", new String[]{queryString});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryOpinion(String objectId, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByObject", new String[]{"opinionRecord",objectId});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryReview(String opinionId, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByObject", new String[]{"reviewRecord",opinionId});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryOpinion_ByUser(String department, String name, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByUser", new String[]{"opinionRecord",department, name});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryReview_ByUser(String department, String name, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByUser", new String[]{"reviewRecord",department, name});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryUserRecord(String department, String name, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByUser", new String[]{"userRecord",department, name});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
