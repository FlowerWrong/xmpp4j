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
import org.jivesoftware.smackx.muc.*;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
        try {
            // Get the MultiUserChatManager
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);

            // Get a MultiUserChat using MultiUserChatManager
            MultiUserChat muc = manager.getMultiUserChat("demoroom@conference.ejabberddemo.com");
            muc.create("demoroom");

            // User1 (which is the room owner) configures the room as a moderated room
            Form form = muc.getConfigurationForm();
            Form answerForm = form.createAnswerForm();
            //向提交的表单添加默认答复,获取房间的默认设置菜单
            for(FormField field : form.getFields() ){
                if(!FormField.Type.hidden.name().equals(field.getType()) && field.getVariable() != null) {
                    answerForm.setDefaultAnswer(field.getVariable());
                }
            }

            //muc#
            //房间名称
            answerForm.setAnswer(FormField.FORM_TYPE, "http://jabber.org/protocol/muc#roomconfig");
            //设置房间名称
            answerForm.setAnswer("muc#roomconfig_roomname", "demoroom");
            //设置房间描述
            answerForm.setAnswer("muc#roomconfig_roomdesc", "google demo room");
            //是否允许修改主题
            answerForm.setAnswer("muc#roomconfig_changesubject", true);

            //设置房间最大用户数
            List<String> maxusers = new ArrayList<String>();
            maxusers.add("100");
            answerForm.setAnswer("muc#roomconfig_maxusers", maxusers);

//            List<String> cast_values = new ArrayList<String>();
//            cast_values.add("moderator");
//            cast_values.add("participant");
//            cast_values.add("visitor");
//            answerForm.setAnswer("muc#roomconfig_presencebroadcast", cast_values);
            //设置为公共房间
            answerForm.setAnswer("muc#roomconfig_publicroom", true);
            //设置为永久房间
            answerForm.setAnswer("muc#roomconfig_persistentroom", true);
            //允许修改昵称
            // answerForm.setAnswer("x-muc#roomconfig_canchangenick", true);
            //允许用户登录注册房间
            // answerForm.setAnswer("x-muc#roomconfig_registration", true);

            // Sets the new owner of the room
            List owners = new ArrayList();
            owners.add("yang@ejabberddemo.com");
            answerForm.setAnswer("muc#roomconfig_roomowners", owners);

            muc.sendConfigurationForm(answerForm);
            muc.join("demoroom");
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        // http://blog.csdn.net/liuhongwei123888/article/details/6618408
    }

    public static void roomInfo(AbstractXMPPConnection connection) {
        // Get the MultiUserChatManager
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);

        RoomInfo info = null;
        try {
            info = manager.getRoomInfo("demoroom@conference.ejabberddemo.com");
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
            List<String> joinedRooms = manager.getJoinedRooms("test4@ejabberddemo.com");
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

    public static void invite(AbstractXMPPConnection connection, String jid) {
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);

        // Get a MultiUserChat using MultiUserChatManager
        MultiUserChat muc = manager.getMultiUserChat("demoroom@conference.ejabberddemo.com");
        muc.addInvitationRejectionListener(new InvitationRejectionListener() {
            public void invitationDeclined(String invitee, String reason) {
                System.out.print("invitationDeclined");
                System.out.print(invitee);
                System.out.print(reason);
            }
        });

        try {
            muc.invite(jid, "Meet me in this excellent room");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        MultiUserChatManager.getInstanceFor(connection).addInvitationListener(new InvitationListener() {
            @Override
            public void invitationReceived(XMPPConnection xmppConnection, MultiUserChat multiUserChat, String s, String s1, String s2, Message message) {
                // MultiUserChat.decline(xmppConnection, multiUserChat, inviter, "I'm busy right now");
                System.out.print("invitationReceived");
            }
        });

    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // debug: http://www.igniterealtime.org/builds/smack/docs/latest/documentation/debugging.html
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(10 * 1000);

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
        // CreateRoom(connection);

        invite(connection, "yang@ejabberddemo.com");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getRoster(connection);
        roomInfo(connection);
        // joinedRooms(connection);

        while(true);  // 死循环，维持该连接不中断

        //退出登陆
        //conn.disconnect();
    }
}
