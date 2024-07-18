package com.yaobing.framemvpproject.mylibrary

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews

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
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("zxcv", "onEnabled")
        val views = RemoteViews(context.packageName, R.layout.alpha_widget_layout)
        views.setTextViewText(R.id.tv_name, "onEnabled了")
    }
}