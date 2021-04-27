package thriftServer.service;

import application.fabricService.AppService;
import application.java.App;
import org.apache.thrift.TException;

public class AppServiceImp implements AppService.Iface {
    private final App appClient;
    public AppServiceImp() {
        appClient = new App();
    }

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
    public void initOpinionRecord(String uuid, String department, String userName,
                                  String object, String type, String opinionTime) throws TException {
        try {
            appClient.initDataRecord(new String[]{uuid, department, userName, object, type, opinionTime});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void initDirectRecord(String uuid, String department, String userName,
                                 String object, String type, String operateTime, String content) throws TException {
        try {
            appClient.initDataRecord(new String[]{uuid, department, userName, object, type, operateTime, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void initUserRecord(String department, String userName, String userAddress, String role) throws TException {
        try {
            appClient.initUserRecord(new String[]{department, userName, userAddress, role});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void reviewRecord(String uuid, String reviewer, String reviewTime,
                             String reviewResult, String reviewDepartment) throws TException {
        try {
            appClient.modifyDataRecord(new String[]{uuid, reviewer, reviewTime, reviewResult, reviewDepartment});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void operateRecord(String uuid, String operateTime, String content) throws TException {
        try {
            appClient.modifyDataRecord(new String[]{uuid, operateTime, content});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void modifyUser(String department, String userName, String userAddress, String role) throws TException {
        try {
            appClient.modifyUserRecord(new String[]{department, userName, userAddress, role});
        } catch (Exception e) {
            throw new TException(e);
        }
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

    @Override
    public String queryDataRecordById(String uuid) throws TException {
        try {
            return appClient.queryDataRecordById(new String[]{uuid});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryUserRecordByAddr(String address) throws TException {
        try {
            return appClient.queryUserRecordByAddr(new String[]{address});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryUserRecordByDept(String department) throws TException {
        try {
            return appClient.queryUserRecordByDept(new String[]{department});
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public String queryWithQueryString(String queryString) throws TException {
        try {
            return appClient.queryWithQueryString(new String[]{queryString});
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
