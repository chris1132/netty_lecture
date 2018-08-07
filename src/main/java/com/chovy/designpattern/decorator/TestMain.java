package com.chovy.designpattern.decorator;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class TestMain {

    public static void main(String arg[]){
        ConcreteComponent concreteComponent = new ConcreteComponent();
        concreteComponent.Operation();

        System.out.println("----------------------");

        Component component = new ConcreteDecoratorA(concreteComponent);
        component.Operation();

        System.out.println("----------------------");

        Component component1 = new ConcreteDecoratorB(concreteComponent);
        component1.Operation();

        System.out.println("----------------------");

        Component component2 = new ConcreteDecoratorA(new ConcreteDecoratorB(concreteComponent));
        component2.Operation();
    }
}
