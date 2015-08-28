## xmpp4j

### Dependencies

* [gradle 2.6](http://gradle.org/)
* [smack 4.1.3 xmpp client](http://www.igniterealtime.org/projects/smack/index.jsp)
* [apache commons-io](http://commons.apache.org/proper/commons-io/index.html)
* [spark for gui test](http://www.igniterealtime.org/projects/spark/index.jsp)
* [ejabberd 15.07 xmpp server](https://www.process-one.net/en/ejabberd/)
* [xmpp rfc](http://wiki.jabbercn.org)
* [Conversations android xmpp client](https://github.com/siacs/Conversations)
* [social_stream rails project](https://github.com/ging/social_stream/wiki/Getting-Started-With-Social-Stream-Presence)

![social_stream](https://github.com/ging/social_stream/wiki/images/sspresence/main_scheme.jpg)

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
* [ejabberd-manual-xmpp-rules-for-leaving-rooms](http://stackoverflow.com/questions/10758295/ejabberd-manual-xmpp-rules-for-leaving-rooms)
* [ejabberd block messages from users that don't stay in my friend list](http://stackoverflow.com/questions/30734000/ejabberd-block-messages-from-users-that-dont-stay-in-my-friend-list)
* [基于ejabberd简单实现xmpp群聊离线消息](http://www.cnblogs.com/lovechengcheng/p/4083398.html)
* [In XMPP pubsub is it possible for a subscriber to retrieve the Subscriptions List or combine it with MUC?](http://stackoverflow.com/questions/10607512/in-xmpp-pubsub-is-it-possible-for-a-subscriber-to-retrieve-the-subscriptions-lis)
* [XMPP Questions Answered: Pubsub versus Multi-User Chat](http://metajack.im/2010/01/15/xmpp-pubsub-versus-multiuser-chat/)

### ejabberd分析

* [ejabberd分析(一)](http://www.cnblogs.com/yjl49/archive/2011/09/02/2371964.html)
* [ejabberd分析(二) 用户注册](http://www.cnblogs.com/yjl49/archive/2011/09/15/2371958.html)
* [ejabberd分析(三)启动流程](http://www.cnblogs.com/yjl49/archive/2011/09/08/2371961.html)
* [ejabberd分析(四) 用户登录](http://www.cnblogs.com/yjl49/archive/2011/09/15/2371957.html)
* [ejabberd分析(五)+订阅/添加好友](http://www.cnblogs.com/yjl49/archive/2011/10/13/2371953.html)
* [通过ejabberd日志分析客户端登录流程](http://my.oschina.net/hncscwc/blog/159826)
* [Ejabberd源码解析前奏--概述](http://blog.chinaunix.net/xmlrpc.php?r=blog/article&uid=22312037&id=3501682)
* [Ejabberd源码解析前奏--安装](http://blog.chinaunix.net/xmlrpc.php?r=blog/article&uid=22312037&id=3502054)
* [Ejabberd源码解析前奏--配置](http://blog.chinaunix.net/xmlrpc.php?r=blog/article&uid=22312037&id=3507236)

### Module development

* [获取用户房间列表](http://blog.zlxstar.me/blog/2013/07/21/dicorvery-user-muclist/)

### Whatsapp-like group chat

```ruby
XEP-45 was designed more then 10 years ago. Back then, the designers had something like IRC channels in mind. Everything of XEP-45 is designed based on the assumption that a user enters and leaves a room when he/she starts/terminates its client.

WhatsApp Groupchats are different: A user joins a groupchat is is able to view the (complete) history of that chat. Even if the users client is offline/unavailable, he is still considered part of the groupchat. The only extensions that providers roughly similar behavior of XEP-60 PubSub. But that again was written for a different use case.

So you have basically 3 options:

    Use XEP-45 with the mentioned workarounds in the other answers: XEP-198 and/or XEP-313
    Implement persistent groupchat based on PubSub
    Implement your own persistent groupchat XMPP extension

XEP-313 seems at first the best solution, although you likely have to implement a lot of code yourself. 2. Could be an option, but you would code against an implementation not especially designed for the use case. This could result in ugly workarounds or deviations from the standard. But Buddycloud proves that it's doable. Sometimes I think what the XMPP community needs is 3.: A new extensions written from scratch that is designed for persistent groupchats.
```

```ruby
I faced your issue as I sought to implement groupchats for my chatting app. I faced the same problem of MUC not storing offline messages for each recipient. And I did not want to retrieve MUC history which requires the user to rejoin every MUC to update his messages database. What I wanted is for the server to save offline messages by recipient, and for the recipient to get all MUC messages when he gets online (without having to join each MUC).

The way I did it is through pubsub. Using pubsub will force the server to store offline message per recipient. When the user reconnects, he gets all the offline messages including the pubsub messages which are sent as normal messages - that is it. One issue I had with pubsub over MUC though is that it is hard to get the list of subscribers. So when my app creates a groupchat, it creates a pubsub node for messages, invite all participants to subscribe (including self) to the pubsub and my app also creates a MUC and makes every participant an owner of that MUC. This way the list of the groupchat participants can be retrieved by checking the list of owners of the MUC. The only purposes of the MUC are to hold the list of participants as well as the name of the groupchat. Everything else is handled by the pubsub node.

Anything unclear please let me know.
```

```ruby
Shooting from the hip here, but it's similar to something I thought about a while ago..persistent MUC rooms.

Possible approach involving a modified mod_muc_room:

    On room creation create a Shared Roster group () named {room name}_Participants
    Whenever someone joins the room add them to the roster
    Whenever someone sends /me leaves then remove them from the roster
    Whenever a message is received for the room, send it wrapped in a 'While you were out...' style message to any user in shared roster who is not online
    When room is killed remove shared roster
```

#### Build ejabberd from source code

```ruby
git clone git@github.com:processone/ejabberd.git
git tag -l
git checkout 15.07

./autogen.sh
./configure --prefix=$HOME/softwares/ejabberd/ --enable-mysql
make clean
make
make install

rm $HOME/softwares/ejabberd/etc/ejabberd/ejabberd.yml
ln -s $HOME/dev/java/xmpp4j/src/main/resources/ejabberd.yml $HOME/softwares/ejabberd/etc/ejabberd

# setup mysql
CREATE DATABASE `ejabberdmysql` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use ejabberdmysql;
SOURCE mysql.sql;

./sbin/ejabberdctl start
./sbin/ejabberdctl status
./sbin/ejabberdctl stop
```
