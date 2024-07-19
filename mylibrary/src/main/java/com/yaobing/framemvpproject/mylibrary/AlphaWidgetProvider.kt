package com.yaobing.framemvpproject.mylibrary

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteResponse
import com.yaobing.framemvpproject.mylibrary.network.APIService
import com.yaobing.module_middleware.Utils.MyDateUtils
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

    override fun onAppWidgetOptionsChanged(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, newOptions: Bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d("zxcv", "AlphaWidgetProvider onAppWidgetOptionsChanged")
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d("zxcv", "AlphaWidgetProvider onDeleted")
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
        if (null == appWidgetManager) {
            return
        }
        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "onReceive了")
        appWidgetManager!!.updateAppWidget(appWidgetIds, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        this.appWidgetManager = appWidgetManager
        this.appWidgetIds = appWidgetIds
        Log.d("zxcv", "AlphaWidgetProvider onUpdate")
        var views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "update了")
        appWidgetManager.updateAppWidget(appWidgetIds, views)
        val response = RemoteResponse()

        //点击事件的代码，好像没用，还是需要用pendingIntent的方式试试点击事件的处理
//        response.addSharedElement(R.id.bt_1a,"dsfsdf")
//        response.let {
//            Log.d("zxcv","fewfwefwe")
//        }
//        views.setOnClickResponse(R.id.bt_1a, response)



        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/").addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        var disposable = retrofit.create(APIService::class.java)
            .listReposRx("MCKRGF")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("zxcv", MyDateUtils.getCurTime(MyDateUtils.date_Format) + "获取到数据了")
                views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
                views.setTextViewText(R.id.tv_name, MyDateUtils.getCurTime(MyDateUtils.date_Format) + "获取到数据了")

                val intent = Intent(context, TestActivity::class.java)
                intent.putExtra("aa","bb")
                val pendingIntent = PendingIntent.getActivity(context,0,intent,FLAG_MUTABLE)
                views.setOnClickPendingIntent(R.id.bt_1a,pendingIntent)


                appWidgetManager.updateAppWidget(appWidgetIds, views)
            }


        // 设置响应 “按钮(bt_refresh)” 的intent，这个flag是不能用的，会报错
//        val btIntent = Intent().setAction(REFRESH_WIDGET);
//        val btPendingIntent = PendingIntent.getBroadcast(context, 0, btIntent, PendingIntent.FLAG_MUTABLE);
//        views.setOnClickPendingIntent(R.id.bt_1a, btPendingIntent);
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("zxcv", "onEnabled")
        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "onEnabled了")
    }
}