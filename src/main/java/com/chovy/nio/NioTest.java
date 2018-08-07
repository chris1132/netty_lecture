package com.chovy.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class NioTest {

    public static void main(String arg[]){
        IntBuffer buffer = IntBuffer.allocate(10);
        for(int i =0;i<10;i++){
            int random  = new SecureRandom().nextInt(20);
            buffer.put(random);
        }
        buffer.flip();

        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
