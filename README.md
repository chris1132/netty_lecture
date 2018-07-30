2018-7-20
系统学习netty
完成服务端部分

2018-7-24
netty本身不是按照servlet规则
http是请求响应模式的无状态的协议，对于netty是监听tcp的端口号，对于他们底层来说仍是serversocket
对于springmvc程序来说，运行在jetty、Tomcat这些servlet容器上，这些容器保证连接关闭，对于netty，连接可以自己控制如keeplive时间

2018-7-26
SimpleChannelInboundHandler<T> T泛型，要处理的消息的 Java 类型;
ChannelHandler,ChannelHandlerContext,ChannelPipeline这三者的关系很特别，相辅相成，一个ChannelPipeline中可以有多个ChannelHandler实例，而每一个ChannelHandler实例与ChannelPipeline之间的桥梁就是ChannelHandlerContext实例
ChannelHandlerContext用于获得上下文信息，如获得远程地址
一个channelPipeline中有多个channelHandler时，且这些channelHandler中有同样的方法时，例如channelActive方法，只会调用处在第一个的channelHandler中的channelActive方法，如果想要调用后续的channelHandler的同名的方法就需要调用以“fire”为开头的方法,如ctx.fireExceptionCaught(cause);

EventLoopGroup boss = new NioEventLoopGroup();服务器端 bootstrap.childHandler
EventLoopGroup worker = new NioEventLoopGroup();客户端：bootstrap.handler
handler针对的是boss发挥作用,处理boss相关信息，如连接来了后，处理相关日志输出；
childhandler针对worker，boss把连接交给worker后，由childhandler里面对象对worker里面nio线程发挥作用

2018-7-27
netty里handler的使用很重要
DelimiterBasedFrameDecoder,netty内置解码器，根据分隔符对bytebuf的消息进行解析  如ABC\nDEF\n
****实现多客户端连接，
需求1：A、B、C-》Server，1、A连接，2、B连接，S打印B连接，S告诉A，B上线，3、C上线，S打印C上线，广播给AB，通知C上线
需求2：ABC都建立连接，A发消息，ABC都受到消息，A显示自己发的消息，BC显示消息来自A

****netty读写检测机制与长连接要素------心跳
手机端和服务器端建立长连接，客户端没退出应用，然后客户端开了飞行模式，则服务器端与客户端无法感知连接已断，相应方法如handlerRemoved方法不会调用。
通过心跳，app向服务器端发心跳包，服务器端收到后，再向app发送ack。如果客户端长时间无法收到反馈消息，则断开
IdleStateHandler用来检测空闲状态，设置读写操作有效时间，
userEventTriggered 出发某个事件被触发后调用该方法，将事件转发给管道pipeline的下一个handler对象

2018-7-30
netty对websocket的支持
websocket解决http存在的问题，
1、http是无状态的，客户端发送两次请求，单从协议本身无法识别两次关系、存了哪些信息，像cookie、session用来解决这些问题，
2、基于请求响应模式的协议，http1.0中请求与响应之前先建立连接，请求发起方是浏览器，响应完成后断开连接，如此反复。HTTP1.1，增加keep-alive，客户端和服务端保持一个短时间的持续连接，这个时间内，客户端与服务端只会建立一个链接，之后的请求复用该连接
假长连接，运用轮询的结束，客户端定时发请求，http包含两部分，header和body，header必带，头信息占据的大小远大于内容。
websocket实现浏览器与服务器之间的长连接，双方是对等的实体，可以互发信息。长时间在初次建立的时候，客户端向服务端发送请求（包括头信息），之后不用包括头信息，真正发送数据本身
websocket本身是构建在http协议上的升级版协议，客户端向向服务器端发请求建立连接（http连接），header里携带了websocket相关的参数，根据这些参数，服务端把http连接升级成websocket
websocket是html5规范的一部分