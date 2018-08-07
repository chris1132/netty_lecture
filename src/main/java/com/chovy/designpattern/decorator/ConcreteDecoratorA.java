package com.chovy.designpattern.decorator;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void Operation() {
        super.Operation();
        this.Behavior();
    }

    public void Behavior(){
        System.out.println("method A");
    }


}
