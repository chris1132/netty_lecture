package com.chovy.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import static java.lang.System.*;

/**
 * Created by wangchaohui on 2018/7/27.
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * GlobalEventExecutor是单线程单例实例，
     * */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch->{
            out.println(channel.remoteAddress()+"发消息："+msg+"\n");
            if(ch != channel){
                ch.writeAndFlush(channel.remoteAddress()+"发消息："+msg+"\n");
            }else{
                ch.writeAndFlush("[自己]:"+msg+"\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客服端]"+channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        out.println(ctx.channel().remoteAddress()+"上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        out.println(ctx.channel().remoteAddress()+"下线");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客服端]"+channel.remoteAddress()+"离开\n");
        out.println(channelGroup.size());
        /**
         * 通道断了后，netty会自动寻找channelGroup，吧断掉的通道移除
         * */
        //channelGroup.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
