package com.chovy.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wangchaohui on 2018/8/20.
 */
public class NioTest11 {

    public static void main(String arg[]) throws Exception{
        int posts[] = {5000,5001,5002,5003,5004};
        Selector selector = Selector.open();

        for(int port:posts){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket socket = serverSocketChannel.socket();
            socket.bind(new InetSocketAddress(port));

//            监听不同类型事件 accept、Connect、read、write
            int interests = SelectionKey.OP_ACCEPT;
            serverSocketChannel.register(selector,interests);
            System.out.println("listenning port:"+port);
        }

        while(true){
            //selector.select(); 若key对应的channel准备好进行I/O操作,返回key的数量
            //有返回，说明事件发生了
            int numbers = selector.select();
            System.out.println("numbers:"+numbers);
            //返回这个selector的selected-key的集合1,因为监听多个通道，可能有两个以上通道返回，所以用集合。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys:"+selectionKeys);
            Iterator<SelectionKey> itr = selectionKeys.iterator();

            while(itr.hasNext()){
                SelectionKey selectionKey = itr.next();
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector,SelectionKey.OP_READ);

                    itr.remove();

                    System.out.println("socketChannel:"+socketChannel);
                }else if(selectionKey.isReadable()){
                    SocketChannel socketChannel =(SocketChannel) selectionKey.channel();
                    int byteread = 0;
                    while(true){
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        buffer.clear();
                        int read = socketChannel.read(buffer);
                        if(read<0)break;
                        buffer.flip();
                        socketChannel.write(buffer);
                        byteread += read;
                    }
                    System.out.println("byteread:"+byteread+"from:"+socketChannel);
                    itr.remove();
                 }
            }

        }
    }
}
