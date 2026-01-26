package com.yaobing.framemvpproject.mylibrary.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yaobing.framemvpproject.mylibrary.R;


public class CenterTransparentDrawable extends Drawable {

    private Paint srcPaint;
    private Path srcPath = new Path();

    private Drawable innerDrawable;


    public CenterTransparentDrawable(Context context, Drawable innerDrawable) {

        this.innerDrawable = innerDrawable;
        Resources res = context.getResources();
        float leftRightPadding = res.getDimension(R.dimen.dp_15);
        float topBottomPadding = res.getDimension(R.dimen.dp_110);
        srcPath.addRect(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding, Path.Direction.CW);
        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        srcPaint.setColor(0xffffffff);
    }

    /**
     * 设置内部透明的部分
     *
     * @param srcPath
     */
    public void setSrcPath(Path srcPath) {
        this.srcPath = srcPath;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        innerDrawable.setBounds(getBounds());
        if (srcPath == null || srcPath.isEmpty()) {
            innerDrawable.draw(canvas);
        } else {
            //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
            int saveCount = canvas.saveLayer(0, 0, getBounds().width(), getBounds().height(), srcPaint, Canvas.ALL_SAVE_FLAG);

            //dst 绘制目标图
            innerDrawable.draw(canvas);

            //设置混合模式
            srcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //src 绘制源图
            canvas.drawPath(srcPath, srcPaint);
            //清除混合模式
            srcPaint.setXfermode(null);
            //还原画布
            canvas.restoreToCount(saveCount);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        innerDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        innerDrawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return innerDrawable.getOpacity();
    }
}
