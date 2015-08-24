package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yy on 15-8-24.
 */
public class CreateRoom {
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

        // https://github.com/rtreffer/smack/blob/master/test/org/jivesoftware/smackx/muc/MultiUserChatTest.java
        try {
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            MultiUserChat muc = manager.getMultiUserChat("demoroom5@conference.ejabberddemo.com");
            muc.create("demoroom5");

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
            answerForm.setAnswer("muc#roomconfig_roomname", "demoroom5");
            //设置房间描述
            answerForm.setAnswer("muc#roomconfig_roomdesc", "google demoroom5 room");
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
/*            //允许修改昵称
            answerForm.setAnswer("x-muc#roomconfig_canchangenick", true);
            //允许用户登录注册房间
            answerForm.setAnswer("x-muc#roomconfig_registration", true);*/


            // Sets the new owner of the room
            // FIXME
//            List owners = new ArrayList();
//            owners.add("registeruser2@ejabberddemo.com");
//            answerForm.setAnswer("muc#roomconfig_roomowners", owners);


            muc.sendConfigurationForm(answerForm);
            muc.join("nickname");

            RoomInfo info = null;
            try {
                info = manager.getRoomInfo("demoroom5@conference.ejabberddemo.com");
            } catch (SmackException.NoResponseException e) {
                e.printStackTrace();
            } catch (XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            System.out.println(info);
            System.out.println("Number of occupants:" + info.getOccupantsCount());
            System.out.println("Number of occupants:" + info.getContactJids());
            System.out.println("Room Subject:" + info.getSubject());

            List<String> listUser = new ArrayList<String>();
            Iterator<String> it = (Iterator<String>) muc.getOccupants();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

            try {
                List<String> joinedRooms = manager.getJoinedRooms("registeruser@ejabberddemo.com");
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
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }
}
