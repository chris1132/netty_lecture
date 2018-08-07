package com.chovy.designpattern.decorator;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class Decorator implements Component {

    private Component component;

    public Decorator(Component component){
        this.component = component;
    }

    @Override
    public void Operation() {
        component.Operation();
    }



}
