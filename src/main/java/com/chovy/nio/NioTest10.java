package com.chovy.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by wangchaohui on 2018/8/16.
 * 关于scattering和gathering
 */
public class NioTest10 {
    public static void main(String arg[])throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));

        int mesLength = 9;

        ByteBuffer b1 = ByteBuffer.allocate(2);
        ByteBuffer b2 = ByteBuffer.allocate(3);
        ByteBuffer b3 = ByteBuffer.allocate(4);

        ByteBuffer[] buffers = {b1,b2,b3};

        SocketChannel socketChannel = serverSocketChannel.accept();

        while(true){
            int byteRead = 0;
            while(byteRead < mesLength){
                long r = socketChannel.read(buffers);
                byteRead += r;

                System.out.println("byteRead: "+ byteRead);
                Arrays.asList(buffers).stream()
                        .map(e -> "position:"+e.position()+"limit:"+e.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(e -> e.flip());

            long byteWrite = 0;
            while(byteWrite<mesLength){
                long r = socketChannel.write(buffers);
                byteWrite +=r;
            }
            Arrays.asList(buffers).forEach(e -> e.clear());

            System.out.println("byteRead:"+byteRead+",byteWrite:"+byteWrite);
        }

    }
}
