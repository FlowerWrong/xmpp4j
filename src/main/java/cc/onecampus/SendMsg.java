package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;


/**
 * Created by yy on 15-8-24.
 */
public class SendMsg {
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
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // receive msg
        ChatManager.getInstanceFor(connection).addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        System.out.println("========================" + message.getFrom() + " : " + message.getBody());
                        if (message.getType() == Message.Type.chat || message.getType() == Message.Type.normal) {
                            if(message.getBody() != null) {
                                System.out.println("========================" + message.getFrom() + " : " + message.getBody());
                            }
                        }
                    }
                });
            }
        });

        // send msg
        Chat chat = ChatManager.getInstanceFor(connection).createChat("registeruser2@ejabberddemo.com");
        try {
            chat.sendMessage("It is a message to test offline mam.");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        connection.disconnect();

        // while (true);
    }
}
