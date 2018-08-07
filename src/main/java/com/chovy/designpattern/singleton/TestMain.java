package com.chovy.designpattern.singleton;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class TestMain {
    public static void main(String[] arg){
        Singleton singleton = Singleton.getInstance();
        singleton.method();

    }
}
