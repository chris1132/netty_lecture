package com.chovy.nio;

import java.nio.ByteBuffer;

/**
 * Created by wangchaohui on 2018/8/15.
 */
public class NioTest6 {

    public static void main(String[] arg){
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for(int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }
        buffer.position(3);
        buffer.limit(8);

        /*
        * 创建一个和当前buffer共享内容块的buffer
        * */
        ByteBuffer sliceBu = buffer.slice();
        for(int i=0;i<sliceBu.capacity();i++){
            byte b = sliceBu.get(i);
            b *=2;
            sliceBu.put(i,b);
        }
//        ByteBuffer readonly = buffer.asReadOnlyBuffer();
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }
}
