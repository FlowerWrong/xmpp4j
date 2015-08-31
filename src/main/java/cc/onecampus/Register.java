package cc.onecampus;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;

/**
 * Created by yy on 15-8-24.
 */
public class Register {

    public static void register(AbstractXMPPConnection connection, String name, String pass) {
        AccountManager accountManager = AccountManager.getInstance(connection);
        accountManager.sensitiveOperationOverInsecureConnection(true);

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
        SmackConfiguration.DEBUG = true;
        SmackConfiguration.setDefaultPacketReplyTimeout(30 * 1000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        configBuilder.setServiceName("ejabberddemo.com");
        configBuilder.setDebuggerEnabled(true).build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        try {
            connection.connect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        register(connection, "registeruser3", "123456");
    }
}

/*
2015-08-31 13:14:47.509 [info] <0.435.0>@ejabberd_listener:accept:299 (#Port<0.6385>) Accepted connection 127.0.0.1:35705 -> 127.0.0.1:5222
2015-08-31 13:14:47.541 [debug] <0.454.0>@ejabberd_receiver:process_data:349 Received XML on stream = <<"<stream:stream xmlns='jabber:client' to='ejabberddemo.com' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>">>
2015-08-31 13:14:47.541 [debug] <0.455.0>@ejabberd_c2s:send_text:1901 Send XML on stream = <<"<?xml version='1.0'?><stream:stream xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' id='189480868' from='ejabberddemo.com' version='1.0' xml:lang='zh'>">>
2015-08-31 13:14:47.542 [debug] <0.455.0>@ejabberd_c2s:send_text:1901 Send XML on stream = <<"<stream:features><c xmlns='http://jabber.org/protocol/caps' hash='sha-1' node='http://www.process-one.net/en/ejabberd/' ver='Kyn00yB1iXiJLUJ0gVvn7tZREMg='/><register xmlns='http://jabber.org/features/iq-register'/><mechanisms xmlns='urn:ietf:params:xml:ns:xmpp-sasl'><mechanism>PLAIN</mechanism><mechanism>DIGEST-MD5</mechanism><mechanism>SCRAM-SHA-1</mechanism></mechanisms></stream:features>">>
2015-08-31 13:14:47.575 [debug] <0.454.0>@ejabberd_receiver:process_data:349 Received XML on stream = <<"<iq to='ejabberddemo.com' id='EINf0-3' type='get'><query xmlns='jabber:iq:register'></query></iq>">>
2015-08-31 13:14:47.575 [debug] <0.454.0>@shaper:update:120 State: {maxrate,1000,0.0,1440998087542307}, Size=97
M=48.5, I=33.049
2015-08-31 13:14:47.575 [debug] <0.455.0>@ejabberd_c2s:send_text:1901 Send XML on stream = <<"<iq from='ejabberddemo.com' id='EINf0-3' type='result'><query xmlns='jabber:iq:register'><instructions>Choose a username and password to register with this server</instructions><username></username><password/></query></iq>">>
2015-08-31 13:14:47.592 [debug] <0.454.0>@ejabberd_receiver:process_data:349 Received XML on stream = <<"<iq to='ejabberddemo.com' id='EINf0-5' type='set'><query xmlns='jabber:iq:register'><username>registeruser3</username><password>123456</password></query></iq>">>
2015-08-31 13:14:47.592 [debug] <0.454.0>@shaper:update:120 State: {maxrate,1000,987.6995764092538,1440998087591411}, Size=158
M=156.08014806469782, I=0.756
2015-08-31 13:14:47.592 [debug] <0.311.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"select password from users where username='">>,<<"registeruser3">>,<<"';">>]
2015-08-31 13:14:47.592 [debug] <0.306.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"insert into users(username, password) values ('">>,<<"registeruser3">>,<<"', '">>,<<"123456">>,<<"');">>]
2015-08-31 13:14:47.596 [debug] <0.309.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"select grp from sr_user where jid='">>,<<"registeruser3@ejabberddemo.com">>,<<"';">>]
2015-08-31 13:14:47.596 [debug] <0.306.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"select name from sr_group;">>]
2015-08-31 13:14:47.596 [debug] <0.455.0>@ejabberd_router:do_route:323 route
	from {jid,<<>>,<<"ejabberddemo.com">>,<<>>,<<>>,<<"ejabberddemo.com">>,<<>>}
	to {jid,<<"registeruser3">>,<<"ejabberddemo.com">>,<<>>,<<"registeruser3">>,<<"ejabberddemo.com">>,<<>>}
	packet {xmlel,<<"message">>,[{<<"type">>,<<"normal">>}],[{xmlel,<<"subject">>,[],[{xmlcdata,<<"Welcome!">>}]},{xmlel,<<"body">>,[],[{xmlcdata,<<"Hi.\nWelcome to this XMPP server.">>}]}]}
2015-08-31 13:14:47.596 [debug] <0.455.0>@ejabberd_local:do_route:296 local route
	from {jid,<<>>,<<"ejabberddemo.com">>,<<>>,<<>>,<<"ejabberddemo.com">>,<<>>}
	to {jid,<<"registeruser3">>,<<"ejabberddemo.com">>,<<>>,<<"registeruser3">>,<<"ejabberddemo.com">>,<<>>}
	packet {xmlel,<<"message">>,[{<<"type">>,<<"norm"...>>}],[{xmlel,<<...>>,...},{xmlel,...}]}
2015-08-31 13:14:47.596 [debug] <0.455.0>@ejabberd_sm:do_route:448 session manager
	from {jid,<<>>,<<"ejabberddemo.com">>,<<>>,<<>>,<<"ejabberddemo.com">>,<<>>}
	to {jid,<<"registeruser3">>,<<"ejabberddemo.com">>,<<>>,<<"registeruser3">>,<<"ejabberddemo.com">>,<<>>}
	packet {xmlel,<<"message">>,[{<<"type">>,<<"norm"...>>}],[{xmlel,<<...>>,...},{xmlel,...}]}
2015-08-31 13:14:47.596 [debug] <0.311.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"select usec, pid, username, ">>,<<"resource, priority, info from sm where ">>,<<"username='">>,<<"registeruser3">>,<<"'">>]
2015-08-31 13:14:47.596 [debug] <0.305.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"select password from users where username='">>,<<"registeruser3">>,<<"';">>]
2015-08-31 13:14:47.597 [debug] <0.455.0>@ejabberd_c2s:send_text:1901 Send XML on stream = <<"<iq from='ejabberddemo.com' id='EINf0-5' type='result'/>">>
2015-08-31 13:14:47.597 [debug] <0.311.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"select count(*) from ">>,<<"spool">>,<<" ">>,<<"where username='registeruser3'">>,<<";">>]
2015-08-31 13:14:47.597 [debug] <0.308.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
<<"begin;">>
2015-08-31 13:14:47.597 [debug] <0.308.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
[<<"insert into spool(username, xml) values ('">>,<<"registeruser3">>,<<"', '">>,<<"<message from=''ejabberddemo.com'' to=''registeruser3@ejabberddemo.com'' type=''normal''><subject>Welcome!</subject><body>Hi.\\nWelcome to this XMPP server.</body><delay xmlns=''urn:xmpp:delay'' from=''ejabberddemo.com'' stamp=''2015-08-31T05:14:47.597Z''>Offline Storage</delay></message>">>,<<"');">>]
2015-08-31 13:14:47.597 [debug] <0.308.0>@ejabberd_odbc:sql_query_internal:473 MySQL, Send query
<<"commit;">>
 */
