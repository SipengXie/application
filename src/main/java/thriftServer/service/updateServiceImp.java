package thriftServer.service;

import application.fabricProxy.updateApp;
import application.fabricRpcService.updateService;
import application.javaRpcToken.javaRpcToken;
import org.apache.thrift.TException;

import java.util.Map;

public class updateServiceImp implements updateService.Iface{
    private final updateApp proxy;
    private final String uuid;
    public updateServiceImp(String ccp, String name, String channelName, String chaincodeName, String uuid) {
        proxy = new updateApp(ccp, name, channelName, chaincodeName);
        this.uuid = uuid;
    }

    boolean checkToken(String token) {
        Map<String, Object> m = javaRpcToken.parserJavaRpcToken(token);
        return (m == null) || !(m.get("uuid").equals(uuid));
    }

    @Override
    public void initOpinionRecord(String uuid, String department, String userName, String object, String type, String opinionTime, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.initDataRecord(new String[]{uuid, department, userName, object, type, opinionTime});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void initDirectRecord(String uuid, String department, String userName, String object, String type, String operateTime, String content, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.initDataRecord(new String[]{uuid, department, userName, object, type, operateTime, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void initUserRecord(String department, String userName, String userAddress, String role, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.initUserRecord(new String[]{department, userName, userAddress, role});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void modifyUser(String department, String userName, String userAddress, String role, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.modifyUserRecord(new String[] {department, userName, userAddress, role});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void reviewRecord(String uuid, String reviewer, String reviewTime, String reviewResult, String reviewDepartment, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.modifyDataRecord(new String[]{uuid, reviewer, reviewTime, reviewResult, reviewDepartment});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void operateRecord(String uuid, String operateTime, String content, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            proxy.modifyDataRecord(new String[]{uuid, operateTime, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
