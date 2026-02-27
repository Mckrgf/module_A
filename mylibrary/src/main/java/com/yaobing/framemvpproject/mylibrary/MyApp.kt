package com.yaobing.framemvpproject.mylibrary

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.yaobing.framemvpproject.mylibrary.ApplicationContextGetter.Companion.instance
import com.yaobing.module_middleware.BaseApp
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.core.SentryAndroid

/**
 * @author : yaobing
 * @date : 2020/10/30 15:16
 * @desc :
 */
class MyApp : BaseApp() {
    private var topActivity: Activity? = null
    private var appCount = 0
    var isRunInBackground: Boolean = false //app是否进入后台

    override fun onCreate() {
        super.onCreate()
        val a = 10
        val b = 7
        val c: Any = 444 / 555
        val d = a / b.toFloat()
        val f = Math.round(d)
        val e: Any = 444 / 555
        initSentry()
        initActivity()
    }

    private fun initSentry() {

        SentryAndroid.init(this) { options ->
            // Required: set your sentry.io project identifier (DSN)
            options.dsn = "https://0747d390c864af702661f681923579a4@o4510792310521856.ingest.us.sentry.io/4510955555979264"
            // Add data like request headers, user ip address and device name, see https://docs.sentry.io/platforms/android/data-management/data-collected/ for more info
            options.isSendDefaultPii = true
            // enable automatic traces for user interactions (clicks, swipes, scrolls)
            options.isEnableUserInteractionTracing = true
            // enable screenshot for crashes
            options.isAttachScreenshot = true
            // enable view hierarchy for crashes
            options.isAttachViewHierarchy = true
            // enable the performance API by setting a sample-rate, adjust in production env
            options.tracesSampleRate = 1.0
            // enable UI profiling, adjust in production env. This is evaluated only once per session
//            options.profileSessionSampleRate = 1.0
            // set profiling mode. For more info see https://docs.sentry.io/platforms/android/profiling/#enabling-ui-profiling
//            options.profileLifecycle = ProfileLifecycle.TRACE
            // enable profiling on app start. The app start profile will be stopped automatically when the app start root span finishes
//            options.isStartProfilerOnAppStart = true
            // record session replays for 100% of errors and 10% of sessions
            options.sessionReplay.sessionSampleRate = 0.1
            options.sessionReplay.onErrorSampleRate = 1.0

            // If your application has strict PII requirements we recommend using the Canvas screenshot strategy.
            // See the Android Session Replay documentation for details: https://docs.sentry.io/platforms/android/session-replay/#screenshot-strategy
            // options.sessionReplay.screenshotStrategy = ScreenshotStrategyType.CANVAS

            // enable logs to be sent to Sentry. Use Sentry.logger() to capture logs or check the available integrations that capture logs automatically: https://docs.sentry.io/platforms/android/logs/#integrations
//            options.logs.isEnabled = true;

            // enable tombstone support for richer Native crashes context. See more at https://docs.sentry.io/platforms/android/configuration/tombstones/
//            options.isTombstoneEnabled = true;

            // Add a callback that will be used before the event is sent to Sentry.
            // With this callback, you can modify the event or, when returning null, also discard the event.
            options.beforeSend =
                SentryOptions.BeforeSendCallback { event, hint ->
                    if (SentryLevel.DEBUG == event.level) {
                        null
                    } else {
                        event
                    }
                }
        }
    }

    private fun changeTopActivity(activity: Activity?) {
        topActivity = activity
        instance().setTopActivity(topActivity)
    }

    private fun initActivity() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                changeTopActivity(activity)
                Log.d("zxcv", activity.getLocalClassName() + ":onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                changeTopActivity(activity)
                appCount++
                Log.d("zxcv", activity.getLocalClassName() + "appCount--:" + appCount)
                Log.d("zxcv", activity.getLocalClassName() + ":onActivityStarted")

                if (isRunInBackground) {
                    isRunInBackground = false
                    //在这加个耗时,看看还会不会让上一个activity onStop了
                    try {
                        Thread.sleep(1000) // 延迟 1000 毫秒（1 秒）
                        Log.d("zxcv", "延迟了")
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        Log.d("zxcv", e.toString())
                    } //18:54:40.805开始home回到主页

                    //                    Intent intent = new Intent(topActivity, SplashActivity.class);
//                    intent.addFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                    topActivity.startActivity(intent);
//                    Log.d("zxcv", "APP回到前台");
                }
            }

            override fun onActivityResumed(activity: Activity) {
                changeTopActivity(activity)
                Log.d("zxcv", activity.getLocalClassName() + ":onActivityResumed")
            }

            override fun onActivityPaused(activity: Activity) {
                Log.d("zxcv", activity.getLocalClassName() + ":onActivityPaused")
            }

            override fun onActivityStopped(activity: Activity) {
                appCount--
                Log.d("zxcv", activity.getLocalClassName() + ":onActivityStopped")
                Log.d("zxcv", activity.getLocalClassName() + "appCount--:" + appCount)

                if (appCount == 0) {
                    isRunInBackground = true
                    Log.d("zxcv", "APP进入后台")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d("zxcv", activity.getLocalClassName() + ":onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d("zxcv", activity.getLocalClassName() + ":onActivityDestroyed")
                if (activity === topActivity) {
                    changeTopActivity(null)
                }
                //                SingleVideoView.INSTANCE.unbindCurrentVideoView(activity.hashCode());
            }
        })
    }
}
