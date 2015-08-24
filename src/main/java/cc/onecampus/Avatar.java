package cc.onecampus;

import org.apache.commons.io.IOUtils;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.vcardtemp.provider.VCardProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yy on 15-8-24.
 */
public class Avatar {
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

        VCard vc = new VCard();
//        try {
//            vc.load(connection);
//        } catch (SmackException.NoResponseException e) {
//            e.printStackTrace();
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }

//        URL url = null;
//        try {
//            url = new URL("https://ruby-china-files.b0.upaiyun.com/user/large_avatar/1667.jpg");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("avatar: " + url);
//
//        java.io.InputStream stream = null;
//        try {
//            stream = url.openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte[] avatar = new byte[0];
//        try {
//            avatar = IOUtils.toByteArray(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("avatar length " + avatar.length);
//
//        vc.setAvatar(avatar, "avatar/jpg");
//
//        try {
//            vc.save(connection);
//        } catch (SmackException.NoResponseException e) {
//            e.printStackTrace();
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }

        try {
            vc.load(connection);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

        byte[] avatarBytes = vc.getAvatar();
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(avatarBytes));
            System.out.println(img);
            // ImageIO.write(img, "jpg", new File("/home/yy/dev/java/xmpp4j/avatar.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connection.disconnect();
    }
}
