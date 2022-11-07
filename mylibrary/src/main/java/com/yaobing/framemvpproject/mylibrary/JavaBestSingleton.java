package com.yaobing.framemvpproject.mylibrary;

import android.util.Log;

import com.yaobing.module_middleware.Utils.LogUtil;
import com.yaobing.module_middleware.Utils.ToastUtils;

public class JavaBestSingleton {
    //通过静态内部类实现单例模式,完美解决懒汉式的线程安全问题和饿汉式的内存问题

    /**
     * 如何解决内存问题?
     * 由于类的加载机制,HolderClass在getInstance方法被调用之前是不会加载的,故HolderClass的静态变量也不会加载,节省了内存
     *
     * 如何解决线程安全问题?
     * 假如有多个线程同时调用getInstance,jvm对于每个类或者是接口都有一个初始化锁LC,保证多线程的情况下也能线程安全的加载类. 只有一个线程完成加载后其它线程才能停止阻塞
     * 原理:每个线程初始化的时候都会获取一次LC锁,初始化之后设置一个标记位证明这个类已经被初始化,其他线程再获取LC锁的时候就不用初始化直接释放锁.
     */

    private static class HolderClass {
        private static JavaBestSingleton singleton = new JavaBestSingleton();
    }

    public static JavaBestSingleton getInstance() {
        return HolderClass.singleton;
    }
}
