package com.yaobing.framemvpproject.mylibrary.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

public class DynamicHeightSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private int normalHeight = 4; // dp
    private int pressedHeight = 12; // dp

    public DynamicHeightSeekBar(Context context) {
        super(context);
        init();
    }

    public DynamicHeightSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicHeightSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 转换为像素
        normalHeight = dpToPx(normalHeight);
        pressedHeight = dpToPx(pressedHeight);

        // 设置默认高度
        updateProgressBarHeight(normalHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                updateProgressBarHeight(pressedHeight);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                updateProgressBarHeight(normalHeight);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void updateProgressBarHeight(int height) {
        LayerDrawable progressDrawable = (LayerDrawable) getProgressDrawable();
        if (progressDrawable != null) {
            Drawable background = progressDrawable.findDrawableByLayerId(android.R.id.background);
            Drawable progress = progressDrawable.findDrawableByLayerId(android.R.id.progress);

            background.setBounds(0, 0, background.getIntrinsicWidth(), height);
            progress.setBounds(0, 0, progress.getIntrinsicWidth(), height);

            progressDrawable.setBounds(0, 0, progressDrawable.getIntrinsicWidth(), height);
            invalidate();
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }
}
