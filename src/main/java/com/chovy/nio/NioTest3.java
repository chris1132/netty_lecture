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

        System.out.println("initialize:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());

        byte[] byteary = "hello world \n  my name is chovy , nice to meet you".getBytes();
        for(byte b:byteary) {
            byteBuffer.put(b);
        }
        System.out.println("first read:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());
        byteBuffer.flip();
        System.out.println("first flip:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());
        fc.write(byteBuffer);
        System.out.println("first write:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());

        byteBuffer.flip();
        System.out.println("second flip:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());

        byte[] byteary2 = "hello world \n  my name is chovy".getBytes();
        for(byte b:byteary2) {
            byteBuffer.put(b);
        }
        System.out.println("second read:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());
        byteBuffer.rewind();
        System.out.println(" rewind:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());
        byteBuffer.put("sd".getBytes());
        System.out.println(" rewind put:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());
        byteBuffer.flip();
        System.out.println("third flip:"+byteBuffer.capacity()+","+byteBuffer.limit()+","+byteBuffer.position());
        fc.write(byteBuffer);

        fileOutputStream.close();
    }

}
