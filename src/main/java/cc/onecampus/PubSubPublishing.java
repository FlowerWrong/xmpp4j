package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.pubsub.*;

import java.io.IOException;

/**
 * Created by yy on 15-8-25.
 */
public class PubSubPublishing {
    public static void main(String[] args) {
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(10 * 1000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setUsernameAndPassword("registeruser", "12345678");

        configBuilder.setServiceName("ejabberddemo.com");
        configBuilder.setDebuggerEnabled(true).build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        try {
            connection.connect().login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        // Create a pubsub manager using an existing XMPPConnection
        PubSubManager mgr = new PubSubManager(connection);
        LeafNode leaf = null;
        try {
            // bad-request - modify FIXME
            leaf = mgr.getNode("testNode");
            System.out.println(leaf);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        String msg = "publish msg test";

        SimplePayload payload = new SimplePayload("message","pubsub:test:message", "<message xmlns='pubsub:test:message'><body>" + msg + "</body></message>");
        PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>("5", payload);

        try {
            leaf.publish(item);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        System.out.println("-----publish-----------");

        while (true);
    }
}
