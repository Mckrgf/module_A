package com.yaobing.framemvpproject.mylibrary.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DashedLineHorView extends View {

    private Paint mPaint;
    private Path mPath;

    public DashedLineHorView(Context context) {
        super(context);
        init();
    }

    public DashedLineHorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DashedLineHorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(0,  getHeight() / 2);
        mPath.lineTo(getWidth(), getHeight() / 2);
        canvas.drawPath(mPath, mPaint);

//        int startX = getWidth() / 2;
//        int startY = 0;
//        int endY = getHeight();
//
//        // 绘制竖向虚线
//        canvas.drawLine(startX, startY, startX, endY, mPaint);
    }
}
