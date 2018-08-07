package com.chovy.designpattern.singleton;

/**
 * Created by wangchaohui on 2018/8/7.
 */
public class Singleton {

    /**
     * 登记式/静态内部类
     * 是否 Lazy 初始化：是
     *是否多线程安全：是
     * 这种方式同样利用了 classloader 机制来保证初始化 instance 时只有一个线程， Singleton 类被装载了，instance 不一定被初始化。因为 SingletonHolder 类没有被主动使用，
     * 只有通过显式调用 getInstance 方法时，才会显式装载 SingletonHolder 类，从而实例化 instance。想象一下，如果实例化 instance 很消耗资源，所以想让它延迟加载，
     * 另外一方面，又不希望在 Singleton 类加载时就实例化，因为不能确保 Singleton 类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化 instance 显然是不合适的
     *
     * */

    private static class SingletonHandler{
        private static final Singleton instance = new Singleton();
    }

    public Singleton() {
    }

    public static final Singleton getInstance(){
        return SingletonHandler.instance;
    }

    public void method(){
        System.out.println("Singleton");
    }
}
