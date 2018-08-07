package com.chovy.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class NioTest2 {

    public static void main(String[] arg) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("netty_lecture\\src\\main\\java\\com\\chovy\\nio\\Niotest.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuf = ByteBuffer.allocate(512);
        fileChannel.read(byteBuf);

        byteBuf.flip();

        while(byteBuf.hasRemaining()){
            System.out.print((char)byteBuf.get());
        }
        fileInputStream.close();
    }
}
