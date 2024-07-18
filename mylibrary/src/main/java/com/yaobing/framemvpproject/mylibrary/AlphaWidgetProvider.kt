package com.yaobing.framemvpproject.mylibrary

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.yaobing.framemvpproject.mylibrary.network.APIService
import com.yaobing.module_middleware.network.Api
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AlphaWidgetProvider : AppWidgetProvider() {
    var appWidgetManager: AppWidgetManager? = null
    var appWidgetIds: IntArray? = null

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
        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "update了")
        appWidgetManager.updateAppWidget(appWidgetIds, views)

        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/").addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        var disposable = retrofit.create(APIService::class.java)
            .listReposRx("MCKRGF")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("zxcv", "获取到数据了 +$it")
                val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
                views.setTextViewText(R.id.tv_name, "data: $it")
                appWidgetManager.updateAppWidget(appWidgetIds, views)
            }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("zxcv", "onEnabled")
        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "onEnabled了")
    }
}