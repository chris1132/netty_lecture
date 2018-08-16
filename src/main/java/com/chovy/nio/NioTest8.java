package com.chovy.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wangchaohui on 2018/8/16.
 */
public class NioTest8 {

    public static void main(String arg[]) throws Exception{
        RandomAccessFile randomFile = new RandomAccessFile("NioTest9","rw");
        FileChannel fc = randomFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte)'A');
        mappedByteBuffer.put(4,(byte)'b');

        randomFile.close();

    }
}
