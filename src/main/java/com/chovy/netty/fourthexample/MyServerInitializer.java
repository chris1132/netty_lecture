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
        //空闲检测：IdleStateHandler  处理读空闲、写空闲、读写空闲 （多长时间没有读写，处理些事情）
        //应用到心跳检测，如：两台机器建立连接后，网断了，C和S都感知不到断网，因此，有一方经过一段时间，发心跳包，
        // 对方收到后证明连接在，若在事先约定的时间内没收到，则连接断，close之前连接，重连
        cp.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS))
                .addLast(new MyServerHandler());
    }
}
