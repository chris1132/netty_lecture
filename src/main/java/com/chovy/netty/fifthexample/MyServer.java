package com.chovy.netty.fifthexample;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;


/**
 * Created by wangchaohui on 2018/7/31.
 */
public class MyServer {

    public static void main(String arg[]){
        EventLoopGroup work = new NioEventLoopGroup();
        EventLoopGroup boss = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MyServerInitializer());

            ChannelFuture cf = bootstrap.bind(new InetSocketAddress(8899)).sync();
            cf.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
