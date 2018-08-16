package com.chovy.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;


import static java.lang.System.*;
/**
 * Created by wangchaohui on 2018/8/16.
 */
public class NioTest9 {

    public static void main(String arg[]) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest","rw");
        FileChannel fc = randomAccessFile.getChannel();

        /*
        *lock(3,6,true); 3：开始锁的位置  6：锁的长度  true：共享锁（false 排它锁）
        * */
        FileLock fl = fc.lock(3,6,true);

        out.println("vaild"+fl.isValid());
        out.println("lock type"+fl.isShared());

        fl.release();

        randomAccessFile.close();

    }
}
