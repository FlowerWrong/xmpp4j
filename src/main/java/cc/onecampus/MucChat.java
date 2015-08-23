package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 15-8-23.
 */
public class MucChat {
    public static void main(String[] args) {
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(10 * 1000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setUsernameAndPassword("yang", "123456");
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

        // receive msg
        ChatManager.getInstanceFor(connection).addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        System.out.print(message.getFrom() + " : " + message.getBody());
                    }
                });
            }
        });

        // create room
        try {
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            MultiUserChat muc = manager.getMultiUserChat("demoroom2@conference.ejabberddemo.com");
            muc.create("demoroom2");

            Form form = muc.getConfigurationForm();
            Form answerForm = form.createAnswerForm();
            //向提交的表单添加默认答复,获取房间的默认设置菜单
            for(FormField field : form.getFields() ){
                if(!FormField.Type.hidden.name().equals(field.getType()) && field.getVariable() != null) {
                    answerForm.setDefaultAnswer(field.getVariable());
                }
            }

            //房间名称
            answerForm.setAnswer(FormField.FORM_TYPE, "http://jabber.org/protocol/muc#roomconfig");
            //设置房间名称
            answerForm.setAnswer("muc#roomconfig_roomname", "demoroom2");
            //设置房间描述
            answerForm.setAnswer("muc#roomconfig_roomdesc", "demoroom2");
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

            // Sets the new owner of the room
            List owners = new ArrayList();
            owners.add("yang@ejabberddemo.com");
            answerForm.setAnswer("muc#roomconfig_roomowners", owners);

            muc.sendConfigurationForm(answerForm);
            muc.join("demoroom2");

            RoomInfo info = manager.getRoomInfo("demoroom2@conference.ejabberddemo.com");
            System.out.println(info);
            System.out.println("Number of occupants:" + info.getOccupantsCount());
            System.out.println("Room Subject:" + info.getSubject());
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 死循环，维持该连接不中断
        while(true);

        // 退出登陆
        // conn.disconnect();
    }
}
