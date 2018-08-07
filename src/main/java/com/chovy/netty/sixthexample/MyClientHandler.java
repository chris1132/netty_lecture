package com.chovy.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;


/**
 * Created by wangchaohui on 2018/8/3.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<StuffInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StuffInfo.MyMessage message) throws Exception {

        System.out.println("-------client print---------");
        System.out.println(message.getResponseMessage());

        StuffInfo.MyMessage res = StuffInfo.MyMessage.newBuilder()
                .setDataType(StuffInfo.MyMessage.DataType.ResponseType)
                .setResponseMessage("Thinks server,receive your message \n                 ---from client").build();

        // System.out.println(teacher.getName()+","+teacher.getAge()+","+teacher.getClass_());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);

        StuffInfo.MyMessage message;

        if(0 == random){
            message = StuffInfo.MyMessage.newBuilder()
                    .setDataType(StuffInfo.MyMessage.DataType.StudentType)
                    .setStudent(StuffInfo.Student.newBuilder().setName("汪朝晖").setAddress("嘉兴").setAge(21).build())
                    .build();
        }else if(1 == random){
            message = StuffInfo.MyMessage.newBuilder()
                    .setDataType(StuffInfo.MyMessage.DataType.TeacherType)
                    .setTeacher(StuffInfo.Teacher.newBuilder().setName("汪朝晖").setClass_("嘉兴").setAge(21).build())
                    .build();
        }else{
            message = StuffInfo.MyMessage.newBuilder()
                    .setDataType(StuffInfo.MyMessage.DataType.AuntieType)
                    .setAuntie(StuffInfo.Auntie.newBuilder().setName("汪朝晖").setAddress("嘉兴").build())
                    .build();
        }
        ctx.writeAndFlush(message);
    }
}
