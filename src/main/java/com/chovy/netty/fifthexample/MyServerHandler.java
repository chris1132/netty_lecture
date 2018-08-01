package com.chovy.netty.fifthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


import java.time.LocalDateTime;

import static java.lang.System.*;
/**
 * Created by wangchaohui on 2018/7/31.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        out.println("receive msg:"+msg.text());
        if(msg instanceof TextWebSocketFrame) {

//            ByteBuf con = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
//            FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, con);
//            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
//            res.headers().set(HttpHeaderNames.CONTENT_LENGTH, con.readableBytes());
            ctx.writeAndFlush(new TextWebSocketFrame("hello world:"+ LocalDateTime.now()));

            // ctx.channel().close();
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        out.println("add:"+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        out.println("remove:"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        out.println("exception");
        ctx.close();
    }
}
