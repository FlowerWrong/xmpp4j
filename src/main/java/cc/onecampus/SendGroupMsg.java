package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.InvitationRejectionListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;

import java.io.IOException;
import java.util.Set;

/**
 * Created by yy on 15-8-25.
 */
public class SendGroupMsg {
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
            connection.connect();
            connection.login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
        MultiUserChat muc = manager.getMultiUserChat("demoroom1@conference.ejabberddemo.com");

        try {
            muc.join("registeruser2");
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        muc.addInvitationRejectionListener(new InvitationRejectionListener() {
            public void invitationDeclined(String invitee, String reason) {
                System.out.println("invitationDeclined");
                System.out.println(invitee);
                System.out.println(reason);
            }
        });

        RoomInfo info = null;
        try {
            info = manager.getRoomInfo("demoroom1@conference.ejabberddemo.com");
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        System.out.println(info);
        System.out.println(info.getRoom());

        System.out.println("============================Number of occupants:" + info.getOccupantsCount());
        System.out.println("============================Room Subject:" + info.getName());

        // sudo ejabberdctl get_room_occupants demoroom6 conference.ejabberddemo.com
        Set<String> joinedRooms = manager.getJoinedRooms();
        System.out.println("=================joinedrooms==============");
        System.out.println(joinedRooms);

        muc.addMessageListener(new MessageListener() {
            @Override
            public void processMessage(Message message) {
                System.out.println("=================addMessageListener==============");
                System.out.println(message);
                System.out.println(message.getFrom());
            }
        });

        try {
            muc.sendMessage("hello everyone");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        while (true);
    }
}
