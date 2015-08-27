package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by yy on 15-8-24.
 */
public class LoginAndVcard {
    public static void main(String[] args) {
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(10 * 1000);

        // Create the configuration for this new connection
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setUsernameAndPassword("registeruser", "12345678");
        // configBuilder.setResource("");
        configBuilder.setServiceName("ejabberddemo.com");
        configBuilder.setDebuggerEnabled(true).build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        try {
            // Connect to the server
            connection.connect().login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        // To save VCard:
        // https://www.igniterealtime.org/builds/smack/docs/latest/javadoc/org/jivesoftware/smackx/vcardtemp/packet/VCard.html
//        VCard vCard = new VCard();
//        vCard.setFirstName("registeruservcard");
//        vCard.setLastName("registeruservcard");
//        vCard.setEmailHome("sysuyangkang@gmail.com");
//        // vCard.setJabberId("jabber@id.org");
//        vCard.setOrganization("onecampus");
//        vCard.setNickName("king");
//
//        vCard.setField("TITLE", "Mr");
//        vCard.setAddressFieldHome("STREET", "Some street");
//        vCard.setAddressFieldWork("CTRY", "US");
//        vCard.setPhoneWork("FAX", "3443233");
//
//        try {
//            vCard.save(connection);
//        } catch (SmackException.NoResponseException e) {
//            e.printStackTrace();
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // To load VCard:
//        try {
//            vCard.load(connection); // load own VCard
//            System.out.println(vCard.getFirstName());
//            System.out.println(vCard.getLastName());
//            System.out.println(vCard.getAvatar());
//        } catch (SmackException.NoResponseException e) {
//            e.printStackTrace();
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }
        // vCard.load(conn, "joe@foo.bar"); // load someone's VCard+

        connection.disconnect();
    }
}
