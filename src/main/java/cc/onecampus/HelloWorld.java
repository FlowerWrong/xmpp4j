package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import sun.rmi.runtime.Log;

import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Create the configuration for this new connection
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setUsernameAndPassword("yang", "123456");
        // configBuilder.setResource("");
        configBuilder.setServiceName("localhost");
        configBuilder.setDebuggerEnabled(true).build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        try {
            // Connect to the server
            connection.connect();
            // Log into the server
            connection.login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        // http://stackoverflow.com/questions/28295783/smack-4-1-processmessage-method-does-not-called
        // receive msg
        ChatManager.getInstanceFor(connection).addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        System.out.print(message.getFrom() + " : " + message.getBody());
                        if (message.getType() == Message.Type.chat || message.getType() == Message.Type.normal) {
                            if(message.getBody()!=null) {
                                System.out.print(message.getFrom() + " : " + message.getBody());
                            }
                        }
                    }
                });
            }
        });

        // send msg
        Chat chat = ChatManager.getInstanceFor(connection).createChat("test1@localhost");
        try {
            chat.sendMessage("Howdy!");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        while(true);  // 死循环，维持该连接不中断

        //退出登陆
        //conn.disconnect();
    }
}
