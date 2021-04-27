package thriftServer.service;

import application.fabricService.ImService;
import application.java.IdentityManagement;
import org.apache.thrift.TException;

public class ImServiceImp implements ImService.Iface {
    private final IdentityManagement IM;
    public ImServiceImp(){IM = new IdentityManagement();}

    @Override
    public void setUserName(String UserName) {
        IM.setUserName(UserName);
    }

    @Override
    public void setAdminPwd(String password) {
        IM.setAdminPwd(password);
    }

    @Override
    public void setCaAddress(String address) {
        IM.setCaAddress(address);
    }

    @Override
    public void setCaPemFile(String pemFile) {
        IM.setCaPemFile(pemFile);
    }

    @Override
    public void setDepartment(String department) {
        IM.setDepartment(department);
    }

    @Override
    public void setMspId(String id) {
        IM.setMspId(id);
    }

    @Override
    public void setWallet() throws TException {
        try {
            IM.setWallet();
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void enrollAdmin() throws TException {
        try {
            IM.enrollAdmin();
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public void registerUser() throws TException {
        try {
            IM.registerUser();
        } catch (Exception e) {
            throw new TException(e);
        }
    }
}
