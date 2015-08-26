package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yy on 15-8-26.
 */
public class CreateANodeWhenCreateARoom {
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
            connection.connect();
            connection.login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        String roomName = "demoroom6";

        // https://github.com/rtreffer/smack/blob/master/test/org/jivesoftware/smackx/muc/MultiUserChatTest.java
        try {
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            MultiUserChat muc = manager.getMultiUserChat(roomName + "@conference.ejabberddemo.com");
            muc.create(roomName);

            Form form = muc.getConfigurationForm();
            Form answerForm = form.createAnswerForm();
            //向提交的表单添加默认答复,获取房间的默认设置菜单
            for(FormField field : form.getFields() ){
                if(!FormField.Type.hidden.name().equals(field.getType()) && field.getVariable() != null) {
                    answerForm.setDefaultAnswer(field.getVariable());
                }
            }

            answerForm.setAnswer(FormField.FORM_TYPE, "http://jabber.org/protocol/muc#roomconfig");
            //设置房间名称
            answerForm.setAnswer("muc#roomconfig_roomname", roomName);
            //设置房间描述
            answerForm.setAnswer("muc#roomconfig_roomdesc", roomName);
            //是否允许修改主题
            answerForm.setAnswer("muc#roomconfig_changesubject", true);
            //设置房间最大用户数
            List<String> maxusers = new ArrayList<String>();
            maxusers.add("100");
            answerForm.setAnswer("muc#roomconfig_maxusers", maxusers);
            //设置为公共房间
            answerForm.setAnswer("muc#roomconfig_publicroom", true);
            //设置为永久房间
            answerForm.setAnswer("muc#roomconfig_persistentroom", true);

            muc.sendConfigurationForm(answerForm);
            muc.join("nickname");

            RoomInfo info = null;
            try {
                info = manager.getRoomInfo(roomName + "@conference.ejabberddemo.com");
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

            // sudo ejabberdctl get_room_occupants demoroom3 conference.ejabberddemo.com
            Set<String> joinedRooms = manager.getJoinedRooms();
            System.out.println("=================joinedrooms==============");
            System.out.println(joinedRooms);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }

        // Create a pubsub manager using an existing XMPPConnection
        PubSubManager mgr = new PubSubManager(connection);
        LeafNode leaf = null;
        try {
            // 创建一个node, nodename就是room name 用户加入后就订阅这个node
            leaf = mgr.createNode(roomName);
            System.out.println(leaf);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String msg = "room pubsub test";

        SimplePayload payload = new SimplePayload("message", "pubsub:test:message", "<message xmlns='pubsub:test:message'><body>" + msg + "</body></message>");
        PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>("5", payload);

        try {
            leaf.publish(item);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        System.out.println("-----publish-----------");


        leaf.addItemEventListener(new ItemEventListener() {
            @Override
            public void handlePublishedItems(ItemPublishEvent items) {
                System.out.println(items);
            }
        });
        try {
            leaf.subscribe("registeruser@ejabberddemo.com");
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        System.out.println("-----subscribe-----------");

        while (true);
    }
}
