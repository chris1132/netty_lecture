package com.chovy.nio;

import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by wangchaohui on 2018/8/23.
 */
public class NioServer {

    private  static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            try{
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey->{
                    final SocketChannel client;
                    try{
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);

                            String key = "[" + UUID.randomUUID().toString() +"]";

                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(64);
                            client = (SocketChannel) selectionKey.channel();
                            int readcount = client.read(buffer);
                            if(readcount > 0){
                                buffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String recieveMes = String.valueOf(charset.decode(buffer).array());
                                System.out.println(client + ":" + recieveMes);
                                String senderKey = null;
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    if(client == entry.getValue()){
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    SocketChannel val = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((senderKey +":"+recieveMes).getBytes());
                                    writeBuffer.flip();
                                    val.write(writeBuffer);
                                }
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
