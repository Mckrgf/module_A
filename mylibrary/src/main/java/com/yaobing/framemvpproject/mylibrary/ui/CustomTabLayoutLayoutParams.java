package com.yaobing.framemvpproject.mylibrary.ui;

import android.widget.LinearLayout;

public class CustomTabLayoutLayoutParams extends LinearLayout.LayoutParams {

    public CustomTabLayoutLayoutParams(int width, int height) {
        super(width, height);
    }

    public void setWidth(int width) {
        this.width = width;
    }
}