package thriftServer.service;

import application.fabricProxy.UpdateApp;
import thriftServer.fabricRpcService.UpdateService;
import thriftServer.javaRpcToken.javaRpcToken;
import org.apache.thrift.TException;

import java.util.Map;

public class updateServiceImp implements UpdateService.Iface{
    private final UpdateApp proxy;
    private final String uuid;
    public updateServiceImp(String ccp, String name, String channelName, String chaincodeName, String uuid) throws Exception {
        proxy = new UpdateApp(ccp, name, channelName, chaincodeName);
        this.uuid = uuid;
    }

    boolean checkToken(String token) {
        Map<String, Object> m = javaRpcToken.parserJavaRpcToken(token);
        return (m == null) || !(m.get("uuid").equals(uuid));
    }

    @Override
    public void createOpinionRecord(String id, String department, String name, String object,
                                    String type, String opinionTime, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.submitTransaction("createOpinionRecord", new String[]{id, department, name, object, type, opinionTime});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void createDirectRecord(String id, String department, String name, String object,
                                   String type, String opinionTime, String doneTime, String content, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.submitTransaction("createOpinionRecord", new String[]{id, department, name, object, type, opinionTime, doneTime, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void createReviewRecord(String id, String department, String name, String object,
                                   String from, String reviewTime, String result, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.submitTransaction("createReviewRecord", new String[]{id, department, name, object, from, reviewTime, result});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void createUserRecord(String id, String department, String name, String role, String content, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.submitTransaction("createUserRecord", new String[]{id, department, name, role, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void modifyOpinionRecord(String id, String doneTime, String content, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.submitTransaction("modifyOpinionRecord", new String[]{id, doneTime, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void modifyUserRecord(String id, String department, String name, String role, String content, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.submitTransaction("modifyUserRecord", new String[]{id, department, name, role, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
