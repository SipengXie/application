package application.fabricProxy;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
/*
 服务代理的总体父类，集成了连接Fabric网关和获取合约的操作
 */
public class App {
    // 本地服务类，是测试网络采用的属性
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    private Path networkConfigPath = null;
    private String name = null;
    private String channelName = null;
    private String chaincodeName = null;
    private Gateway gateway = null;

    public App(String ncp, String name, String channelName, String chaincodeName) {
        networkConfigPath = Paths.get(ncp);
        this.name = name;
        this.channelName = channelName;
        this.chaincodeName = chaincodeName;
    }
/*
 连接至网关并获取合约
 */
    public Contract connectAndGetContract() throws Exception {
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, name).networkConfig(networkConfigPath).discovery(true);
        gateway =  builder.connect();
        Network network = gateway.getNetwork(channelName);
        return network.getContract(chaincodeName);
    }

    public void closeGateway() {
        gateway.close();
    }

}
