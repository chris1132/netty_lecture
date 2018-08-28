package com.chovy.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by wangchaohui on 2018/8/28.
 */
public class OldClient {
    public static void main(String[] arg) throws Exception{
        Socket socket = new Socket("localhost",8899);

        String fileName = "F:\\11.pdf";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long count = 0;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while((count=inputStream.read(buffer))>=0){
            total += count;
            dataOutputStream.write(buffer);
        }

        System.out.println("send byte number:"+total+" spend time:"+(System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();

    }
}
