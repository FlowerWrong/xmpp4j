package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;
import java.util.Collection;

public class HelloWorld {
    public static void getRoster(AbstractXMPPConnection connection) {
        System.out.println("roster");
        Roster roster = Roster.getInstanceFor(connection);
        Collection<RosterEntry> entries = roster.getEntries();
        System.out.println(entries);
        for (RosterEntry entry : entries) {
            System.out.println(entry.getName());
        }

        roster.addRosterListener(new RosterListener() {
            public void entriesAdded(Collection<String> addresses) {
                System.out.println("add roster: " + addresses);
            }
            public void entriesDeleted(Collection<String> addresses) {
                System.out.println("delete roster: " + addresses);
            }

            public void entriesUpdated(Collection<String> addresses) {
                // update roster: [test1@localhost]
                System.out.println("update roster: " + addresses);
            }

            public void presenceChanged(Presence presence) {
                System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
            }
        });
    }

    public static void register(AbstractXMPPConnection connection, String name, String pass) {
        AccountManager accountManager = AccountManager.getInstance(connection);
        try {
            accountManager.createAccount(name, pass);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // debug: http://www.igniterealtime.org/builds/smack/docs/latest/documentation/debugging.html
        SmackConfiguration.DEBUG = true;

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
//        Chat chat = ChatManager.getInstanceFor(connection).createChat("test1@localhost");
//        try {
//            chat.sendMessage("Howdy!");
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }

        // rosters
        getRoster(connection);

        // register
        register(connection, "test4", "123456");

        while(true);  // 死循环，维持该连接不中断

        //退出登陆
        //conn.disconnect();
    }
}
