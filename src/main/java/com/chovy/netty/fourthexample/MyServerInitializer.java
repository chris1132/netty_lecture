package com.chovy.netty.fourthexample;

import com.chovy.netty.thirdexample.MyChatServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangchaohui on 2018/7/27.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();

        cp.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS))
                .addLast(new MyServerHandler());
    }
}
