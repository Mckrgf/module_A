package com.yaobing.framemvpproject.mylibrary;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class BetaWidgetProvider extends AppWidgetProvider {
    AppWidgetManager appWidgetManager = null;
    int[] appWidgetIds = null;
    public BetaWidgetProvider() {
        super();
        Log.d("zxcv","BetaWidgetProvider BetaWidgetProvider");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d("zxcv","BetaWidgetProvider onAppWidgetOptionsChanged");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("zxcv","BetaWidgetProvider onDeleted");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("zxcv","BetaWidgetProvider onDisabled");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d("zxcv","BetaWidgetProvider onRestored");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("zxcv","BetaWidgetProvider onReceive");
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.alpha_widget_layout);
//        views.setTextViewText(R.id.tv_name,"onReceive了");
//        appWidgetManager.updateAppWidget(appWidgetIds,views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
        Log.d("zxcv","BetaWidgetProvider onUpdate");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.alpha_widget_layout);
        views.setTextViewText(R.id.tv_name,"update了");
        appWidgetManager.updateAppWidget(appWidgetIds,views);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("zxcv","onEnabled");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.alpha_widget_layout);
        views.setTextViewText(R.id.tv_name,"onEnabled了");
    }
}
