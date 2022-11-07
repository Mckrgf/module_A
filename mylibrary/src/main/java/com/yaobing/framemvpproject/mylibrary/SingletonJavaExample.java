package com.yaobing.framemvpproject.mylibrary;

/**
 * @author : yaobing
 * @date : 2022/11/07 16:03
 * @desc :
 */
public class SingletonJavaExample {
    // 2022/11/07 饿汉式：类加载就创建实例。线程安全，内存浪费

    //------------------------1,静态常量------------------------
    public static Object singleton = new Object();

    //------------------------2，静态代码块------------------------
    public static Object singletonA;

    static {
        singletonA = new Object();
    }

    public static Object getInstance() {
        return singletonA;
    }


    // 2022/11/07 懒汉式,如果不加同步，就有线程安全问题。
    //------------------------3------------------------
    public static Object singletonC;

    public synchronized Object getInstanceC() {
        if (null == singletonC) {
            singletonC = new Object();
        }
        return singletonC;
    }
}
