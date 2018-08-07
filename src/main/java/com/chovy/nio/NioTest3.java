package com.chovy.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class NioTest3 {
    public static void main(String[] arg) throws Exception{
        FileOutputStream fileOutputStream = new FileOutputStream("netty_lecture\\src\\main\\java\\com\\chovy\\nio\\Niotest3.txt");
        FileChannel fc = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byte[] byteary = "hello world \n  my name is chovy".getBytes();
        for(byte b:byteary) {
            byteBuffer.put(b);
        }
        byteBuffer.flip();
        fc.write(byteBuffer);
        fileOutputStream.close();
    }

}
