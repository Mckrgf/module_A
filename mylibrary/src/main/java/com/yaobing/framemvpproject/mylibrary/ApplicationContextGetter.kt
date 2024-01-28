package com.yaobing.framemvpproject.mylibrary

import android.app.Activity
import android.app.Application
import java.util.concurrent.*

/**
 *  对外提供当前Application，可以直接使用在只需要Application Context的地方
 */

class ApplicationContextGetter {
    private var mTopActivity:Activity? = null
    var isUserAgree = false
    private var executorService: ExecutorService? = null
    var isOfficial = false
    fun isAgree(agree:Boolean){
        isUserAgree = agree
    }
    fun setTopActivity(activity: Activity?){
        mTopActivity = activity
    }
    fun getTopActivity():Activity?{
        return mTopActivity
    }
    fun setApplicationContext(application: Application) {
        sApplication = application
    }

    fun get(): Application {
        return sApplication!!
    }

    //初始化线程池
    fun initThreadPool() {
        if(null != executorService){
            return
        }
        val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors() //核心线程池大小，2倍为最大容量
        val KEEP_ALIVE_TIME = 1 //允许线程存活时间1秒
        val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS //秒级
        val taskQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()
        executorService = ThreadPoolExecutor(
            NUMBER_OF_CORES, NUMBER_OF_CORES * 2,
            KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, taskQueue
        )
    }

    //==================================================================================================
    fun getCachedThreadPool(): ExecutorService? {
        /*可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程*/
        return executorService
    }

    companion object {
        @Volatile
        private var sInstance: ApplicationContextGetter? = null
        private var sApplication: Application? = null
        @Synchronized
        fun instance(): ApplicationContextGetter {
            if (sInstance == null) {
                sInstance = ApplicationContextGetter()
            }
            return sInstance!!
        }
    }
}