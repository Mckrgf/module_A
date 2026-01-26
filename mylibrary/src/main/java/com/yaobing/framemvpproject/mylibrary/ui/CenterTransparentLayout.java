package com.yaobing.framemvpproject.mylibrary.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yaobing.framemvpproject.mylibrary.R;


public class CenterTransparentLayout extends FrameLayout {

//    private Context mContext;
    private CenterTransparentDrawable background;

    public CenterTransparentLayout(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public CenterTransparentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        this.mContext=context;
        initView(context, attrs, 0);
    }

    public CenterTransparentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        background = new CenterTransparentDrawable(context,getBackground());
        setBackground(background);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resetBackgroundHoleArea();
    }

    @SuppressLint("NewApi")
    private void resetBackgroundHoleArea() {
        Path path = null;
        // 以子View为范围构造需要透明显示的区域
        View view = findViewById(R.id.viewfinder_content);
        if (view != null) {
            path = new Path();
//            Resources res = MyApp.getMyApp().getResources();
//            float leftRightPadding = res.getDimension(R.dimen.dp_15);
//            float topBottomPadding = res.getDimension(R.dimen.dp_110);
            //矩形透明区域
            path.addRoundRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), 0, 0, Path.Direction.CW);
        }
        if (path != null) {
            background.setSrcPath(path);
        }
    }

//    public int dp2Px(Context context, float dp) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dp * scale + 0.5f);
//    }
}
