package com.chovy.netty.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangchaohui on 2018/7/27.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    private static Map<String, Integer> temp_map = new HashMap<String,Integer>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        //ctx.channel().writeAndFlush(Thread.currentThread().getName()+",online");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String thread_name = Thread.currentThread().getName();
//        if(temp_map.get(thread_name)==null){
//            ctx.writeAndFlush(thread_name+",online");
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
