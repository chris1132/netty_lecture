package com.chovy.designpattern.decorator;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public void Operation() {
        super.Operation();
        this.Function();
    }

    public void Function(){
        System.out.println("method B");
    }
}
