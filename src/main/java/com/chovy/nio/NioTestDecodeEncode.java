package com.chovy.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by wangchaohui on 2018/8/24.
 */
public class NioTestDecodeEncode {

    public static void main(String[] arg )throws Exception{

        String inputFile = "netty_lecture\\src\\main\\java\\com\\chovy\\nio\\text\\NioTest_in.txt";
        String outputFile = "netty_lecture\\src\\main\\java\\com\\chovy\\nio\\text\\NioTest_out.txt";

        RandomAccessFile inputAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputAccessFile = new RandomAccessFile(outputFile,"rw");

        long inputLength = new File(inputFile).length();

        FileChannel inputChannel = inputAccessFile.getChannel();
        FileChannel outputChannel = outputAccessFile.getChannel();

        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY,0,inputLength);

        System.out.println("==============");

        Charset.availableCharsets().forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        System.out.println("==============");

        Charset charset = Charset.forName("iso-8859-1");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharsetEncoder charsetEncoder = charset.newEncoder();

        CharBuffer charBuffer = charsetDecoder.decode(inputData);
        ByteBuffer outputData = charsetEncoder.encode(charBuffer);

        outputChannel.write(outputData);

        inputAccessFile.close();
        outputAccessFile.close();
    }
}
