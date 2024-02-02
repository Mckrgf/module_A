package com.yaobing.framemvpproject.mylibrary;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.yaobing.framemvpproject.mylibrary.activity.activity.SplashActivity;
import com.yaobing.module_middleware.BaseApp;

/**
 * @author : yaobing
 * @date : 2020/10/30 15:16
 * @desc :
 */
public class MyApp extends BaseApp {
    private Activity topActivity = null;
    private int appCount;
    public boolean isRunInBackground = false;//app是否进入后台

    @Override
    public void onCreate() {
        super.onCreate();
        initActivity();
    }
    private void changeTopActivity(Activity activity) {
        topActivity = activity;
        ApplicationContextGetter.Companion.instance().setTopActivity(topActivity);
    }
    private void initActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                changeTopActivity(activity);
                Log.d("zxcv",activity.getLocalClassName() + ":onActivityCreated");
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                changeTopActivity(activity);
                appCount++;
                Log.d("zxcv",activity.getLocalClassName() + "appCount--:" + appCount);
                Log.d("zxcv",activity.getLocalClassName() + ":onActivityStarted");

                if (isRunInBackground) {
                    isRunInBackground = false;
                    //在这加个耗时,看看还会不会让上一个activity onStop了
                    try {
                        Thread.sleep(1000); // 延迟 1000 毫秒（1 秒）
                        Log.d("zxcv","延迟了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("zxcv", e.toString());
                    } //18:54:40.805开始home回到主页
                    Intent intent = new Intent(topActivity, SplashActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    topActivity.startActivity(intent);
                    Log.d("zxcv", "APP回到前台");
                }

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                changeTopActivity(activity);
                Log.d("zxcv",activity.getLocalClassName() + ":onActivityResumed");
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.d("zxcv",activity.getLocalClassName() + ":onActivityPaused");
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                appCount--;
                Log.d("zxcv",activity.getLocalClassName() + ":onActivityStopped");
                Log.d("zxcv",activity.getLocalClassName() + "appCount--:" + appCount);

                if (appCount == 0) {
                    isRunInBackground = true;
                    Log.d("zxcv", "APP进入后台");
                }


            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                Log.d("zxcv",activity.getLocalClassName() + ":onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.d("zxcv",activity.getLocalClassName() + ":onActivityDestroyed");
                if (activity == topActivity) {
                    changeTopActivity(null);
                }
//                SingleVideoView.INSTANCE.unbindCurrentVideoView(activity.hashCode());
            }
        });
    }
}
