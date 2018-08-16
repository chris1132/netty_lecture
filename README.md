博客地址：https://blog.csdn.net/u010530712/article/details/81187059

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

##netty对websocket的支持
websocket解决http存在的问题， 1、http是无状态的，客户端发送两次请求，单从协议本身无法识别两次关系、存了哪些信息，像cookie、session用来解决这些问题， 2、基于请求响应模式的协议，http1.0中请求与响应之前先建立连接，请求发起方是浏览器，响应完成后断开连接，如此反复。HTTP1.1，增加keep-alive，客户端和服务端保持一个短时间的持续连接，这个时间内，客户端与服务端只会建立一个链接，之后的请求复用该连接
假长连接，运用轮询的结束，客户端定时发请求，http包含两部分，header和body，header必带，头信息占据的大小远大于内容。
websocket实现浏览器与服务器之间的长连接，双方是对等的实体，可以互发信息。长连接在初次建立的时候，客户端向服务端发送请求（包括头信息），之后不用包括头信息，真正发送数据本身
websocket本身是构建在http协议上的升级版协议，客户端向向服务器端发请求建立连接（http连接），header里携带了websocket相关的参数，根据这些参数，服务端把http连接升级成websocket
websocket是html5规范的一部分

2018-7-31
netty 对于处理请求是分块或分段方式，客户端向服务端发送请求，长度1000，netty可能会分成10段，每段会走一个完整的流程，我们自己处理器掉channelread0，只读到一段
HttpObjectAggregator（len）把分段的请求（响应）聚合成一个完整的请求（响应），len以字节的方式来聚合内容的最大长度，如果聚合的内容超过了长度，调用handleOversizedMessage
WebSocketServerProtocolHandler 会处理关于websocket繁重的工作，负责连接握手、以及处理心跳相关的内容
WebSocketServerProtocolHandler("/ws")  /ws,websocket uri的地址   如ws://localhost:8899/'ws'   WebSocketServerProtocolHandler的ws是指请求地址里的第二个ws

2018-8-1
##netty实现服务端和客户端的长连接通信
需求，实现网页通过websocket发送数据，服务端收到后反馈消息
在浏览器的ws:/localhost:8899/ws  header中，Status Code:101 Switching Protocols,转换协议  http->ws
在请求头里Request Headers里Upgrade:websocket，虽然访问ws这个请求，但是需要http先去建立连接，建立完成后，再upgrade（升级）到websocket，所以说websocket是在http之上的协议

##google protobuf使用方式  https://developers.google.cn/protocol-buffers/
protobuf 进行rpc数据传输，用来自定协议，可以更好的、体积更小的对数据编码解码，即序列化反序列化（编码、解码）过程。rpc开发中常用的库
rmi：remote method invocation，远程方法调用。（只针对java，client和server都必须是java） 客户端：stub，服务端：skeleton
通过网络传输，A->（调用的对象、方法、参数）序列化成字节码-》网络传输-》B-》接收字节码，反序列化转换成，调用特定对象、方法。
RPC：remote procedure call 远程过程调用  用socket传输
RPC比RMI的优势，跨语言调用，如客户端用python，服务端用java，客户端可以调用自己的一个方法，触发服务端的方法
RPC编写模式，：
1、定义接口说明文件（idl）：描述对象、对象成员、接口方法等一系列信息。
2、通过rpc框架所提供的编译器，将接口说明文件编译成具体文件
3、在客户端与服务器端分别引入RPC编译器所生成的文件，即可像调用本地方法一样调用远程方法
决定rpc框架效率看编解码效率
在公司内网中更推荐用rpc方式进行，减少基于http通信带来的损耗。

2018-8-2
**使用protocol-buffer构建对象，实现编解码
需求，客户端A,服务器B，A构造对象，发给服务器端，B收到后，打印对象信息，把另一个对象发给A。

2018-8-3
接昨天，已完成昨天需求，待解决问题
        pipeline.addLast(new ProtobufDecoder(StuffInfo.Teacher.getDefaultInstance()))   //StuffInfo.Teacher编码对象写死，不灵活，
2018-8-6
解决方案，在发送消息外再包一层，见StuffInfo.proto，通过枚举列举各消息类型，再通过一个字段标识本次消息传递的是哪个消息类型
netty基于protocolbuffer这种数据传递过程，对于具体用哪种方法或数据类型来处理请求，存在判断繁杂问题，因为，一段向另一段发送数据时候，接收方要能判断对方发送哪种数据类型，通过if-else去寻找匹配的数据类型
而springmvc和netty相比比，springmvc路径路由很清晰直观，如: @RequestMapping(value="../xx/",method=RequestMethod.GET),下面对于一个处理方法。因为springmvc 有dispatcherServlet(控制器)，c向s发起的所有请求，
都经过dispatcherServlet，再分发给不同的controller，springmvc在启动的时候，找到url和方法对应关系，把对应关系保存（如map），s收到请求后，通过url匹配具体方法。

##io体系架构回顾
io的输入流和输出流是两种
流的分类：
1、节点流：从特定的地方读写的流类，和目标或目的地直接接触交互的，例如：磁盘或一块内存区域
2、过滤流：使用节点流作为输出或输出，在节点流基础上的包装。过滤流是使用一个已经存在的输入或输出流连接创建的
I/O流的连接:
input：文件-》从文件中取输入字节（FileInputStream）-》增加缓存（BufferedInputStream）-》增加读取java基本数据类型的功能（DataInputStream）（用户使用）-》数据
output：数据-》往输出流中写入java基本数据类型（DataInputStream）-》提供数据写入到缓冲区的功能（BufferedInputStream）-》将数据写入文件（FileInputStream）-》文件
节点流：类A  过滤流：B、C    ->   new C(new B(new A())) ;
java的I/O库提供了一个称作连接的机制，可以将一个流与另一个流首尾相接，形成一个流管道连接。Decorator（装饰）模式。通过流的连接，可以动态增加流的功能，而这种功能的增加是通过组合一些流的基本功能而动态获取的。
I/O体系由很多类，通过装饰模式，不影响基础功能上，控制类的数量。

volatile 作用：1、内存可见性，A线程修改了自己工作内存的变量值，会修改主内存里变量的值，并刷新到B线程工作内存的值
2、防止指令重排序，编译后的字节码和编程语言的顺序不一致，加上volatile能防止。

##Nio体系

源码 https://github.com/chris1132/netty_lecture/src/main/java/com/chovy/nio
1、java.io最核心的概念是流（stream），IO编程就是面向流的编程，java中，一个流要么是输出流，要么是输出流。
2、java.nio中拥有三个核心概念：Selector，Channel和Buffer。在java.nio中，是面向块（block）或是缓冲区（buffer）编程的。
3、Buffer本身就是一块内存，底层实现上，实际上是个数组。数据的读、写都是通过Buffer来实现的。
4、io编程中，数据是从stream中直接读取到程序里，nio中，数据从channel读到buffer中，从buffer数组里读到程序。
nio中，buffer既可以写入数据，也可以从buffer读取，但当进行读、写切换时，需要调用buffer.flip()方法来反转。
5、除了数组之外，Buffer还提供了对于数组的结构化访问方式，并且可以追踪到系统读写过程
---》读和写在底层通过相应标识来判断读、写到什么位置，这些在buffer底层都封装好，无论读、写系统都能定位到当前读写的位置，以及flip反转后从哪读，读到什么地方。
6、java中的7种原生数据类型（byte、short、int、long、float、double、char）都有各自对应的buffer类型，如IntBuffer，LongBuffer等。boolean数据类型，没有buffer。
7、channel值得是可以向其写入数据或是从中读取数据的对象，类似于java.io中的stream，所有数据的读写都是通过buffer进行
与stream不同的是，channel是双向的，一个流只能是inputstream或是outputstream，channel打开后，则可以读取、写入、读写
由于channel是双向的，因此它能更好地反映出底层操作系统的真是情况，如在linux中，底层操作系统的通道是双向的，
8、关于NIO Buffer中的几个属性
capacity：buffer包含的元素的个数，分配好后永远不会变化，如：IntBuffer b = IntBuffer.allocate（10），元素的数量就是10。
limit：第一个不能再读或不能再写的元素的索引，值小于等于capacity。
position：下一个将要被读或写的元素的索引，值小于等于limit。
mark，reset：如position读取到5是，使用mark，进行标记，然后继续往下读，然后可以通过调用reset，重新从5开始读。0<=mark<=position<=limit<=capacity；
clear：将limit设为capacity，position设为0，将buffer设为初始化状态；
flip：将limit设为当前position，将position设为0，重头开始读写；
rewind：limit不变，position设为0，buffer重新读取。

绝对方法和相对方法
相对方法：limit和position的值，在操作时需考虑到，由netty底层方法进行更改，如flip方法。
绝对方法：limit和position的值不会更改，根据buffer的索引直接get和put。

9、ByteBuffer buffer = ByteBuffer.allocate(10); ByteBuffer sliceBuffer = buffer.slice();
slice方法是创建一个和当前buffer共享内容块的新buffer，sliceBuffer更改内容buffer里也能看到，因此，新的buffer其实是索引

10、如果用HeapByteBuffer，操作系统不是直接处理java堆上的HeapByteBuffer里面的字节数组，而是拷贝一份到java堆外开辟的内存空间，再从堆外空间取数据和IO设备交互。

如果使用DirectByteBuffer，IO设备直接和堆外内存做交互。（ZeroCopy 零拷贝）
