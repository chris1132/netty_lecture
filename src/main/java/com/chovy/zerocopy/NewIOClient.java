package com.chovy.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wangchaohui on 2018/8/29.
 */
public class NewIOClient {
    public static void main(String[] arg) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress("localhost",8899));

        String fileName =  "F:\\11.pdf";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        long transCount = fileChannel.transferTo(0,fileChannel.size(),socketChannel);

        System.out.println("send byte:"+ transCount + "spend time:"+(System.currentTimeMillis()-startTime));

        fileChannel.close();

    }
}
