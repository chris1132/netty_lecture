package com.chovy.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by wangchaohui on 2018/7/31.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();
        /**ws  即  websocket协议
         * ws://localhost:8899/'ws'   WebSocketServerProtocolHandler的ws是指请求地址里的第二个ws
         * */
        cp.addLast(new HttpServerCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(8192))
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                .addLast(new MyServerHandler());


    }
}
