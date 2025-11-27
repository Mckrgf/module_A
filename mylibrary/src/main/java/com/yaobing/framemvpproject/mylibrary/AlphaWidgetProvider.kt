package com.yaobing.framemvpproject.mylibrary

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.yaobing.framemvpproject.mylibrary.network.APIService
import com.yaobing.module_middleware.Utils.MyDateUtils
import com.yaobing.module_middleware.Utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class AlphaWidgetProvider : AppWidgetProvider() {
    var receiverCount = 0
    var appWidgetManager: AppWidgetManager? = null
    var appWidgetIds: IntArray? = null
    val REFRESH_WIDGET = "com.oitsme.REFRESH_WIDGET"

    init {
        Log.d("zxcv", "AlphaWidgetProvider AlphaWidgetProvider")
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d("zxcv", "AlphaWidgetProvider onAppWidgetOptionsChanged")
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d("zxcv", "AlphaWidgetProvider onDeleted")
        alarmManager?.cancel(pendingIntent!!)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d("zxcv", "AlphaWidgetProvider onDisabled")
    }

    override fun onRestored(context: Context, oldWidgetIds: IntArray, newWidgetIds: IntArray) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        Log.d("zxcv", "AlphaWidgetProvider onRestored")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("zxcv", "AlphaWidgetProvider onReceive")
//        if (null == appWidgetManager) {
//            return
//        }
        val appWidgetManager = AppWidgetManager.getInstance(context) // 获取 AppWidgetManager 实例

        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "onReceive了")
        appWidgetManager!!.updateAppWidget(appWidgetIds, views)


        // 更新小部件
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, javaClass))
        for (appWidgetId in appWidgetIds) {
            Log.d("zxcv", "onReceiver内更新小组件")
            appWidgetManager.updateAppWidget(appWidgetId, views)

            requestData(views, context, appWidgetManager, appWidgetIds)
        }
    }
    var intent : Intent? = null
    var pendingIntent : PendingIntent? = null
    var alarmManager : AlarmManager? = null
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        this.appWidgetManager = appWidgetManager
        this.appWidgetIds = appWidgetIds
        Log.d("zxcv", "AlphaWidgetProvider onUpdate")
        var views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "update了")
        appWidgetManager.updateAppWidget(appWidgetIds, views)

        requestData(views, context, appWidgetManager, appWidgetIds)


        // 设置警报
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        intent = Intent(context, AlphaWidgetProvider::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent!!, FLAG_MUTABLE)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            1000 * 1 * 10,
            pendingIntent!!
        )

    }

    private fun requestData(
        views: RemoteViews,
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        var views1 = views
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        Log.d("zxcv", MyDateUtils.getCurTime(MyDateUtils.date_Format) + "准备请求数据")
        var disposable = retrofit.create(APIService::class.java)
            .listReposRx("MCKRGF")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                run {
                    Log.d("zxcv", MyDateUtils.getCurTime(MyDateUtils.date_Format) + "获取到数据了")
                    views1 = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
                    views1.setTextViewText(
                        R.id.tv_name,
                        MyDateUtils.getCurTime(MyDateUtils.date_Format) + "获取到数据了"
                    )
                    val intent = Intent(context, TestActivity::class.java)
                    intent.putExtra("aa", "bb")
                    val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_MUTABLE)
                    views1.setOnClickPendingIntent(R.id.bt_1a, pendingIntent)
                    appWidgetManager.updateAppWidget(appWidgetIds, views1)
                }
            }, { it ->
                run {
                    ToastUtils.show(context, "接口粗欧文")
                }
            })
//            }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("zxcv", "onEnabled")
        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "onEnabled了")
    }
}