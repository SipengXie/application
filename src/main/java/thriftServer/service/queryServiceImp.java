package thriftServer.service;

import application.fabricProxy.QueryApp;
import thriftServer.fabricRpcService.QueryService;
import thriftServer.javaRpcToken.javaRpcToken;
import org.apache.thrift.TException;

import java.util.Map;
/*
 实际查询服务类，实现了Thrift自动生成的接口
 */
public class queryServiceImp implements QueryService.Iface {

    private final QueryApp proxy;
    private final String uuid;
    public queryServiceImp(String ncp, String name, String channelName, String chaincodeName, String uuid) throws Exception{
        proxy = new QueryApp(ncp, name, channelName, chaincodeName);
        this.uuid = uuid;
    }
// 检查RPC Token
    boolean checkToken(String token) {
        Map<String, Object> m = javaRpcToken.parserJavaRpcToken(token);
        return (m == null) || !(m.get("uuid").equals(uuid));
    }
/*
 实际实现通过查询串进行查询的功能
 */
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
/*
 实际实现通过目标ID查询意见信息的功能
 */
    @Override
    public String queryOpinion_ById(long objectId, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByObject", new String[]{"opinionRecord", String.valueOf(objectId)});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
/*
 实际实现通过目标ID查询审批信息的功能
 */
    @Override
    public String queryReview_ById(long opinionId, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByObject", new String[]{"reviewRecord", String.valueOf(opinionId)});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
/*
 实际实现通过用户信息查询意见信息的功能
 */
    @Override
    public String queryOpinion_ByUser(long department, long name, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByUser", new String[]{"opinionRecord", String.valueOf(department), String.valueOf(name)});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
/*
 实际实现通过用户信息查询审批信息的功能
 */
    @Override
    public String queryReview_ByUser(long department, long name, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            return proxy.evaluateTransaction("queryRecordByUser", new String[]{"reviewRecord", String.valueOf(department), String.valueOf(name)});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

}
