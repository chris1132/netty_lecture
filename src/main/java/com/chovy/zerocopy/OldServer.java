package com.chovy.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by wangchaohui on 2018/8/28.
 */
public class OldServer {
    public static void main(String[] arg) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        while(true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try{
                byte[] byteAry = new byte[4096];

                while(true){
                    int readcount = dataInputStream.read(byteAry,0,byteAry.length);
                    if(-1 == readcount){
                        break;
                    }
                }

            }catch (Exception e){}
        }
    }
}
