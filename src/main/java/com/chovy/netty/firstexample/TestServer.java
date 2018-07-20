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
        EventLoopGroup bossG = new NioEventLoopGroup();
        EventLoopGroup workerG = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossG, workerG)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally{
            bossG.shutdownGracefully();
            workerG.shutdownGracefully();
        }
    }
}
