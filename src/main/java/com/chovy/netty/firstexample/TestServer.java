package com.chovy.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wangchaohui on 2018/7/20.
 */
public class TestServer {

    public static void main(String[] arg)throws Exception{
        //NioEventLoopGroup 死循环
        //netty的服务器会启动两个服务器，boss来接收连接，worker用来处理连接及响应。
        //本质上都是nio的异步处理的事件循环对象
        EventLoopGroup bossG = new NioEventLoopGroup();
        EventLoopGroup workerG = new NioEventLoopGroup();
        try {
            //启动服务器，ServerBootstrap辅助启动封装类
            //ServerBootstrap里面封装了netty的相关属性和方法，简化netty服务器的创建工作
            ServerBootstrap bootstrap = new ServerBootstrap();
            //方法链形式（返回对象），装配启动类属性
            bootstrap.group(bossG, workerG)
                    .channel(NioServerSocketChannel.class)//netty里对nio的ServerSocketChannel进行了封装
                    .childHandler(new TestServerInitializer());//childHandler针对worker，用来处理交给woeker的连接
            //服务器真正启动，
            //sync（阻塞方法）用来等待和同步连接
            //返回ChannelFuture集成Future，调用异步方法后直接返回Future，里面方法可判断异步操作是否结束
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            //netty关闭操作
            channelFuture.channel().closeFuture().sync();
        }finally{
            //netty优雅关闭？
            //如果关闭的时候有连接还没处理，在可控的时间里，把连接处理完，并不接受新的连接
            bossG.shutdownGracefully();
            workerG.shutdownGracefully();
        }
    }
}
