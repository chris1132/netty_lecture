package com.chovy.nio;

import java.nio.ByteBuffer;

/**
 * Created by wangchaohui on 2018/8/15.
 * 类型化的get put方法
 */
public class NioTest5 {

    public static void main(String[] arg){
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(10);
        buffer.putInt('A');
        buffer.putChar('好');
        buffer.putDouble(5.3);
        buffer.putLong(46798754L);

        buffer.flip();

            System.out.println(buffer.getInt());
            System.out.println(buffer.getInt());

    }
}




