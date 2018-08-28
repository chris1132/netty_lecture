package com.chovy.netty.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by wangchaohui on 2018/7/27.
 */

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            String eventType = null;
            switch(event.state()){
                case READER_IDLE:
                    eventType = "read relx";
                    break;
                case WRITER_IDLE:
                    eventType = "write relx";
                    break;
                case ALL_IDLE:
                    eventType = "read and write relx";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "over time event:"+eventType);
            ctx.channel().close();
        }
    }
}
