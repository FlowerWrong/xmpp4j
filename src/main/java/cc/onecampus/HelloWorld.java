package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jivesoftware.smackx.xdata.packet.DataForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    public static void sendSubscribe(AbstractXMPPConnection connection, String jid) {
        Presence subscribe = new Presence(Presence.Type.subscribe);
        subscribe.setTo(jid);
        try {
            connection.sendPacket(subscribe);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public static void sendSubscribed(AbstractXMPPConnection connection, String jid) {
        Presence subscribed = new Presence(Presence.Type.subscribed);
        subscribed.setTo(jid);
        try {
            connection.sendPacket(subscribed);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public static void addRoster(AbstractXMPPConnection connection, String jid, String name, String[] groups) {
        Roster roster = Roster.getInstanceFor(connection);

        Presence subscribed = new Presence(Presence.Type.subscribed);

        try {
            roster.createEntry(jid, name, groups);
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
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

    public static void createRoom(AbstractXMPPConnection connection) {
        // Get the MultiUserChatManager
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);

        // Get a MultiUserChat using MultiUserChatManager
        MultiUserChat muc = manager.getMultiUserChat("test_room@conference.ejabberddemo.com");

        // Create the room
        try {
            muc.create("test_room");
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }

        // Send an empty room configuration form which indicates that we want an instant room
        try {
            muc.sendConfigurationForm(new Form(DataForm.Type.submit));
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        try {
            // 最重要一句:直到用户调用join方法的时候聊天室才会被创建
            muc.join("yang", "123456");
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        try {
            muc.invite("test4@ejabberddemo.com", "大家来谈谈人生");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        // http://blog.csdn.net/liuhongwei123888/article/details/6618408
    }

    public static void roomInfo(AbstractXMPPConnection connection) {
        // Get the MultiUserChatManager
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);

        RoomInfo info = null;
        try {
            info = manager.getRoomInfo("test_room@conference.ejabberddemo.com");
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        System.out.println(info);
        System.out.println("Number of occupants:" + info.getOccupantsCount());
        System.out.println("Room Subject:" + info.getSubject());
    }

    public static void joinedRooms(AbstractXMPPConnection connection) {
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
        // Get the rooms where user3@host.org has joined
        try {
            List<String> joinedRooms = manager.getJoinedRooms("test5@ejabberddemo.com/Smack");
            System.out.print(joinedRooms);
            for (int i = 0; i < joinedRooms.size(); i++) {
                System.out.print(joinedRooms.get(i));
            }
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
        configBuilder.setUsernameAndPassword("test5", "123456");
        // configBuilder.setResource("");
        configBuilder.setServiceName("ejabberddemo.com");
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
//        Chat chat = ChatManager.getInstanceFor(connection).createChat("test4@ejabberddemo.com");
//        try {
//            chat.sendMessage("Howdy!");
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }

        // register
        //register(connection, "test5", "123456");

        // Add roster
        //sendSubscribe(connection, "test4@ejabberddemo.com");
        // sendSubscribed(connection, "test5@ejabberddemo.com");
        //sendSubscribe(connection, "yang@ejabberddemo.com");
//        addRoster(connection, "test5@ejabberddemo.com", "iamtest5", null);
//        sendSubscribed(connection, "test5@ejabberddemo.com");


        // room
        createRoom(connection);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getRoster(connection);
        roomInfo(connection);
        joinedRooms(connection);

        while(true);  // 死循环，维持该连接不中断

        //退出登陆
        //conn.disconnect();
    }
}
