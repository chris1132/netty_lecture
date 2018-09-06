package com.chovy.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by wangchaohui on 2018/8/28.
 */
public class NewIOServer {
    public static void main(String[] arg) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readcount = 0;

            while(-1 != readcount){
                try{
                    readcount = socketChannel.read(buffer);
                }catch (Exception e){
                    e.printStackTrace();
                }
                buffer.rewind();
            }

        }

    }
}
