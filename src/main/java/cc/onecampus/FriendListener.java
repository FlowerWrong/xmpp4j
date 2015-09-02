package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by yy on 15-9-2.
 */
public class FriendListener {

    public static void sendSubscribe(AbstractXMPPConnection connection, String jid) {
        Presence subscribe = new Presence(Presence.Type.subscribe);
        subscribe.setTo(jid);
        try {
            connection.sendStanza(subscribe);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public static void sendSubscribed(AbstractXMPPConnection connection, String jid) {
        Presence subscribed = new Presence(Presence.Type.subscribed);
        subscribed.setTo(jid);
        try {
            connection.sendStanza(subscribed);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(10 * 1000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setUsernameAndPassword("registeruser3", "123456");

        configBuilder.setServiceName("ejabberddemo.com");
        configBuilder.setDebuggerEnabled(true).build();

        final AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
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
        connection.addPacketListener(new StanzaListener() {
            @Override
            public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
                // Presence presence = (Presence) packet;
                // System.out.println(presence);
                if (((Presence) packet).getType() == Presence.Type.subscribe) {
                    System.out.println("Presence.Type.subscribe and from " + ((Presence) packet).getType());
                    sendSubscribed(connection, "registeruser@ejabberddemo.com");
                    sendSubscribe(connection, "registeruser@ejabberddemo.com");
                } else if (((Presence) packet).getType() == Presence.Type.subscribed) {
                    System.out.println("Presence.Type.subscribed and from " + ((Presence) packet).getType());
                    // sendSubscribe(connection, "registeruser@ejabberddemo.com");
                }
            }
        }, new StanzaFilter() {
            @Override
            public boolean accept(Stanza stanza) {
                return true;
            }
        });


        System.out.println("================roster====================");
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
                System.out.println("update roster: " + addresses);
            }

            public void presenceChanged(Presence presence) {
                System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
            }
        });

        while (true);
    }
}
