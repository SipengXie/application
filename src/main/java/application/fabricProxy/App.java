package application.fabricProxy;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class App {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private Path networkConfigPath = null;
    private String userName = null;
    private String channelName = null;
    private String chaincodeName = null;
    private Gateway gateway = null;

    public App(String ccp, String name, String channelName, String chaincodeName) {
        networkConfigPath = Paths.get(ccp);
        userName = name;
        this.channelName = channelName;
        this.chaincodeName = chaincodeName;
    }

    // Errors during connecting to gateway should be processed by upper layer
    public Contract connectAndGetContract() throws Exception {
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
        gateway =  builder.connect();
        Network network = gateway.getNetwork(channelName);
        return network.getContract(chaincodeName);
    }

    public void closeGateway() {
        gateway.close();
    }


}
