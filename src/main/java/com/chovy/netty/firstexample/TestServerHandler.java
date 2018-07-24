package com.chovy.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import static java.lang.System.*;
import java.net.URI;

/**
 * Created by wangchaohui on 2018/7/20.
 */
public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx,HttpObject msg)throws Exception{
        if(msg instanceof HttpRequest){
            out.println("remote address:"+ctx.channel().remoteAddress());
            URI url = new URI(((HttpRequest) msg).uri());
            if(url.getPath().equals("/favicon.ico")){
                   out.println("from brows " );
                   out.println(((HttpRequest) msg).method());
            }
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
                    , HttpResponseStatus.OK,content);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            res.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            ctx.writeAndFlush(res);
            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{
        out.println("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx)throws Exception{
        out.println("channel registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        out.println("handler added");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)throws Exception{
        out.println("channel inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }

}
