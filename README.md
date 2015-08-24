## xmpp4j

### Dependencies

* [gradle 2.6](http://gradle.org/)
* [smack 4.1.3 xmpp client](http://www.igniterealtime.org/projects/smack/index.jsp)
* [spark for gui test](http://www.igniterealtime.org/projects/spark/index.jsp)
* [ejabberd 15.07 xmpp server](https://www.process-one.net/en/ejabberd/)
* [xmpp rfc](http://wiki.jabbercn.org)

### Usage

```ruby
gradle build
java -jar build/libs/xmpp4j-1.0.jar
```

### Links

* [smack-4-1-processmessage-method-does-not-called](http://stackoverflow.com/questions/28295783/smack-4-1-processmessage-method-does-not-called)
* [Smack multi user chat list of online users](http://stackoverflow.com/questions/15973194/smack-multi-user-chat-list-of-online-users)
* [Unable to create persistent chat room smack 4.1.1](https://community.igniterealtime.org/thread/55940)
* [MultiUserChatTest](https://github.com/rtreffer/smack/blob/master/test/org/jivesoftware/smackx/muc/MultiUserChatTest.java)

### Register allow all ipaddress

```ruby
    ##
    ## Only clients in the server machine can register accounts
    ##
    ip_access: all  # trusted_network Note here

  ## In-band registration allows registration of any possible username.
  ## To disable in-band registration, replace 'allow' with 'deny'.
  register:
    all: allow
  ## Only allow to register from localhost, Disable comment this two line
  ##trusted_network:
  ##  loopback: allow
```

### Issue

* [Users are not allowed to register accounts so quickly `registration_timeout: infinity`](https://www.ejabberd.im/node/3553)
* [Create Multi User Group with Prosody Server on Android (aSmack) failes with “Missing acknowledge of room creation”](http://stackoverflow.com/questions/25679706/create-multi-user-group-with-prosody-server-on-android-asmack-failes-with-mis)
* [asmack-openfire-how-do-i-keep-a-user-permanently-in-groupchat-room](http://stackoverflow.com/questions/19653877/asmack-openfire-how-do-i-keep-a-user-permanently-in-groupchat-room)
* [OpenFire - Permanent Group Chat using PubSub](http://stackoverflow.com/questions/19782876/openfire-permanent-group-chat-using-pubsub)
* [基于ejabberd简单实现xmpp群聊离线消息](http://my.oschina.net/csq/blog/342212)
* [聊天服务器：Ejabberd 安装与离线转发模块的实现](http://tianmaying.com/blog/8ab3eda84dfb8f90014e01e20fcb010a)
* [Persistent XMPP MUC (XEP-45), like WhatsApp groupchats](http://stackoverflow.com/questions/25982426/persistent-xmpp-muc-xep-45-like-whatsapp-groupchats)
* [delivering-messages-to-offline-users-in-a-multi-user-chat-ejabberd](http://stackoverflow.com/questions/22352864/delivering-messages-to-offline-users-in-a-multi-user-chat-ejabberd)
* [implement-group-chat-using-ejabberd](http://stackoverflow.com/questions/30393938/implement-group-chat-using-ejabberd)
* [In group chat occupants is removed from group when he is offline](https://community.igniterealtime.org/thread/53374)
* [ejabberd 15.06: Message archive, configuration checker and new packages XEP-313](https://blog.process-one.net/ejabberd-15-06/)