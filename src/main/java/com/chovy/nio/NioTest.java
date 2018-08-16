package com.chovy.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class NioTest {

    public static void main(String arg[]){
        IntBuffer buffer = IntBuffer.allocate(10);
        for(int i =0;i<5;i++){
            int random  = new SecureRandom().nextInt(20);
            buffer.put(random);
        }
        System.out.println("before flip"+buffer.limit());  //10

        buffer.flip();
        System.out.println("after flip"+buffer.limit()); //5

        while(buffer.hasRemaining()){
            System.out.println("capacity "+buffer.capacity()); //10
            System.out.println("limit "+buffer.limit()); //5
            System.out.println("position "+buffer.position()); //0 1 2 3 4

            System.out.println(buffer.get());
        }
    }
}
