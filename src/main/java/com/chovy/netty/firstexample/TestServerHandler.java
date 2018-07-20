package com.chovy.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by wangchaohui on 2018/7/20.
 */
public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx,HttpObject msg)throws Exception{
        ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
        FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
        , HttpResponseStatus.OK,content);
        res.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        res.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

        ctx.writeAndFlush(res);
    }

}
