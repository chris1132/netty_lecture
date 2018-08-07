package com.chovy.designpattern.singleton;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class Singleton2 {

    /**
     * 饿汉式
     * 是否 Lazy 初始化：否
     *是否多线程安全：是
     *优点：没有加锁，执行效率会提高。
     *缺点：类加载时就初始化，浪费内存。
     *
     * */

    private static Singleton2 instance = new Singleton2();

    private Singleton2(){}

    public static Singleton2 getInstance(){
        return instance;
    }
}
