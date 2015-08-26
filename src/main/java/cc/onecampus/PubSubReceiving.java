package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import java.io.IOException;
import java.util.List;

/**
 * Created by yy on 15-8-25.
 */
public class PubSubReceiving {
    public static void main(String[] args) {
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(10 * 1000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setUsernameAndPassword("registeruser2", "123456");

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
        LeafNode node = null;
        try {
            node = mgr.getNode("demoroom6");
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        node.addItemDeleteListener(new ItemDeleteListener() {
            @Override
            public void handleDeletedItems(ItemDeleteEvent items) {
                System.out.println("======================handleDeletedItems==============================");
                System.out.println(items);
            }

            @Override
            public void handlePurge() {
                System.out.println("======================handlePurge==============================");
            }
        });


        node.addItemEventListener(new ItemEventListener() {
            @Override
            public void handlePublishedItems(ItemPublishEvent items) {
                System.out.println("======================handlePublishedItems==============================");
                System.out.println(items);
            }
        });


        // 此处可处理小组离线消息
        try {
            List<Item> items = node.getItems();
            System.out.println("==========getitems==========");
            for (Item item : items) {
                System.out.println(item.getElementName());
                System.out.println(item.getId());
                System.out.println(item.getNamespace());
                System.out.println(item.getNode());
            }
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        try {
            // The bare jid portion of this one must match the jid for the connection.
            node.subscribe("registeruser2@ejabberddemo.com");
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        while (true);
    }
}
