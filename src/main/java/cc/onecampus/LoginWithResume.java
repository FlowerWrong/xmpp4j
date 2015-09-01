package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by yy on 15-9-1.
 */
public class LoginWithResume {
    public static void main(String[] args) {
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(1000);

        XMPPTCPConnection.setUseStreamManagementDefault(true);
        // XMPPTCPConnection.setUseStreamManagementResumptiodDefault(true);

        // Create the configuration for this new connection
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        configBuilder.setUsernameAndPassword("registeruser2", "123456");
        // configBuilder.setResource("");
        configBuilder.setServiceName("ejabberddemo.com");
        configBuilder.setDebuggerEnabled(true).build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());

//        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(connection);
//        reconnectionManager.enableAutomaticReconnection();

        try {
            // Connect to the server
            connection.connect().login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        // connection.disconnect();

        while (true) ;
    }
}
