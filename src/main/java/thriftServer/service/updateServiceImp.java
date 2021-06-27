package thriftServer.service;

import application.fabricProxy.UpdateApp;
import thriftServer.fabricRpcService.UpdateService;
import thriftServer.javaRpcToken.javaRpcToken;
import org.apache.thrift.TException;

import java.util.Map;
/*
 实现更新服务，实现了Thrift自动生成的接口
 */
public class updateServiceImp implements UpdateService.Iface{
    private final UpdateApp proxy;
    private final String uuid;
    public updateServiceImp(String ncp, String name, String channelName, String chaincodeName, String uuid) throws Exception {
        proxy = new UpdateApp(ncp, name, channelName, chaincodeName);
        this.uuid = uuid;
    }
// 检查RPC token
    boolean checkToken(String token) {
        Map<String, Object> m = javaRpcToken.parserJavaRpcToken(token);
        return (m == null) || !(m.get("uuid").equals(uuid));
    }
/*
 实际实现创建意见记录
 */
    @Override
    public void createOpinionRecord(String uuid, long id, long department, long name, long object, long type,
                                    String opinionTime, String doneTime,
                                    String extension1, String extension2, String extension3, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            String[] args = new String[]{uuid,
                    String.valueOf(id), String.valueOf(department),
                    String.valueOf(name), String.valueOf(object),
                    String.valueOf(type),opinionTime,doneTime,
                    extension1,extension2,extension3};

            proxy.submitTransaction("createOpinionRecord", args);
        } catch (Exception e) {
            throw new TException(e);
        }

    }
/*
 实际实现创建审批记录
 */
    @Override
    public void createReviewRecord(String uuid, long department, long name, long object, long result,
                                   String reviewTime, String extension1, String extension2,
                                   String extension3, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            String[] args = new String[]{uuid,
                    String.valueOf(department),
                    String.valueOf(name), String.valueOf(object),
                    String.valueOf(result), reviewTime,
                    extension1,extension2,extension3};
            proxy.submitTransaction("createReviewRecord", args);
        } catch (Exception e) {
            throw new TException(e);
        }
    }
/*
 实际实现了修改意见记录
 */
    @Override
    public void modifyOpinionRecord(String uuid, String doneTime, String token) throws TException {
        if(checkToken(token)) {
            throw new TException("Invalid token found: " + token);
        }
        try {
            String[] args = new String[]{uuid,doneTime};
            proxy.submitTransaction("modifyOpinionRecord", args);
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
