package com.chovy.nio;

import io.netty.buffer.ByteBuf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangchaohui on 2018/8/23.
 */
public class NioClient {
    public static void main(String[] args) throws Exception{
        try{
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));

            while(true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(SelectionKey selectionKey: selectionKeys){
                    if(selectionKey.isConnectable()){
                        SocketChannel client = (SocketChannel)selectionKey.channel();
                        if(client.isConnectionPending()){
                            //连接真正建立好了
                            client.finishConnect();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.put((LocalDateTime.now()+"connect success").getBytes());
                            buffer.flip();
                            client.write(buffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit(()->{
                                while(true){
                                    try{
                                        buffer.clear();
                                        InputStreamReader input = new InputStreamReader(System.in);

                                        BufferedReader br = new BufferedReader(input);
                                        String inputMes = br.readLine();

                                        buffer.put(inputMes.getBytes());
                                        buffer.flip();
                                        client.write(buffer);
                                    }catch(Exception e){e.printStackTrace();}
                                }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                        int count = client.read(readBuffer);
                        readBuffer.flip();
                        if (count>0){
                            String receimes = new String(readBuffer.array(),0,count);
                            System.out.println(receimes);
                        }

                    }

                }
                selectionKeys.clear();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
