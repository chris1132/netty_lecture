package com.chovy.designpattern.decorator;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class ConcreteComponent implements Component {

    @Override
    public void Operation() {
        System.out.println("ConcreteComponent ");
    }
}
