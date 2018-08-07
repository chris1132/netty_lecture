package com.chovy.netty.sixthexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by wangchaohui on 2018/8/2.
 */
public class MyServer {

    public static void main(String arg[]){

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerIniatializer());

            ChannelFuture cf = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
            cf.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

}
