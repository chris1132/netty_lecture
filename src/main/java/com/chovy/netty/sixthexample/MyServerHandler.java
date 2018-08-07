package com.chovy.netty.sixthexample;

import com.chovy.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

import static java.lang.System.*;

/**
 * Created by wangchaohui on 2018/8/2.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<StuffInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,   StuffInfo.MyMessage message) throws Exception {

        StuffInfo.MyMessage.DataType dataType = message.getDataType();

        if(dataType == StuffInfo.MyMessage.DataType.StudentType){
            out.println("student's name:" + message.getStudent().getName());
        }

        if(dataType == StuffInfo.MyMessage.DataType.TeacherType){
            out.println("teacher's name:" + message.getTeacher().getName());
        }

        if(dataType == StuffInfo.MyMessage.DataType.AuntieType){
            out.println("auntie's name:" + message.getAuntie().getName());
        }

        StuffInfo.MyMessage res = StuffInfo.MyMessage.newBuilder()
                .setDataType(StuffInfo.MyMessage.DataType.ResponseType)
                .setResponseMessage("Thinks client,receive your message \n                 ---from server").build();

        ctx.channel().writeAndFlush(res);
    }

}
