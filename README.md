## xmpp4j

### Dependencies

* [gradle 2.6](http://gradle.org/)
* [smack 4.1.3 xmpp client](http://www.igniterealtime.org/projects/smack/index.jsp)
* [spark for gui test](http://www.igniterealtime.org/projects/spark/index.jsp)
* [ejabberd 15.07 xmpp server](https://www.process-one.net/en/ejabberd/)

### Usage

```ruby
gradle build
java -jar build/libs/xmpp4j-1.0.jar
```

### Links

* [smack-4-1-processmessage-method-does-not-called](http://stackoverflow.com/questions/28295783/smack-4-1-processmessage-method-does-not-called)
* [Smack multi user chat list of online users](http://stackoverflow.com/questions/15973194/smack-multi-user-chat-list-of-online-users)
* [Unable to create persistent chat room smack 4.1.1](https://community.igniterealtime.org/thread/55940)

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