package com.chovy.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wangchaohui on 2018/8/14.
 */
public class NioTest4 {

    public static void main(String arg[]) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("netty_lecture\\src\\main\\java\\com\\chovy\\nio\\input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("netty_lecture\\src\\main\\java\\com\\chovy\\nio\\output.txt");

        FileChannel inputChannel =fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(512);

        while(true){
            bb.clear();
            int read = inputChannel.read(bb);
            System.out.println(read);
            if(-1 == read ){
                break;
            }
            bb.flip();
            outputChannel.write(bb);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

}
