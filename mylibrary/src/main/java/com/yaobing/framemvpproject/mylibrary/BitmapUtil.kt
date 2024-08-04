package com.yaobing.framemvpproject.mylibrary

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import kotlin.math.min

class BitmapUtil {

    /**
     * 获取圆角图片，且居中展示
     */
    fun getRoundedCornerBitmapWithoutScaling(bitmap: Bitmap, cornerRadius: Float): Bitmap? {
        // 获取最小边长
        val width = bitmap.width
        val height = bitmap.height
        val size = min(width, height)

        // 计算裁剪起始坐标
        val x = (width - size) / 2
        val y = (height - size) / 2

        // 创建新的Bitmap
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        // 创建画笔
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.WHITE // 设置背景色为白色，可根据需要修改
        }

        // 绘制圆角矩形
        val rect = Rect(0, 0, size, size)
        val rectF = RectF(rect)
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

        // 设置Xfermode为SRC_IN
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        // 绘制裁剪后的原图
        canvas.drawBitmap(bitmap, Rect(x, y, x + size, y + size), rect, paint)

        // 还原画笔
        paint.xfermode = null

        return output
    }


    fun getRoundedBitmap(originalBitmap: Bitmap, cornerRadius: Float): Bitmap {
        val roundedBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(roundedBitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        val path = Path()
        val cornerRadiusPx = cornerRadius.dpToPx()
        path.addRoundRect(0f, 0f, roundedBitmap.width.toFloat(), roundedBitmap.height.toFloat(), cornerRadiusPx, cornerRadiusPx,
            Path.Direction.CW)
        canvas.drawPath(path, paint)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)
        return roundedBitmap
    }

    fun Float.dpToPx(): Float {
        val density = Resources.getSystem().displayMetrics.density
        return this * density
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap, cornerRadius: Float): Bitmap? {
        // 获取最小边长
        val width = bitmap.width
        val height = bitmap.height
        val size = min(width, height)

        // 创建新的Bitmap
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        // 创建画笔
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.WHITE // 设置背景色为白色，可根据需要修改
        }

        // 绘制圆角矩形
        val rect = Rect(0, 0, size, size)
        val rectF = RectF(rect)
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

        // 设置Xfermode为SRC_IN
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        // 绘制原图
        canvas.drawBitmap(bitmap, null, rect, paint)

        // 还原画笔
        paint.xfermode = null

        return output
    }

    fun getRoundBitmapByShader(bitmap: Bitmap?, size: Int, radius: Int, margin: Int): Bitmap? {
        var bitmap = bitmap ?: return null
//        if (margin > 0) {
//            bitmap = Bitmap.createBitmap(bitmap.width + 2 * margin, bitmap.height + 2 * margin, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(bitmap)
//            canvas.drawBitmap(bitmap, margin.toFloat(), margin.toFloat(), null)
//        }
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = bitmapShader
//        val roundBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val roundBitmap = Bitmap.createBitmap(bitmap,0,0,size,size)
        val canvas = Canvas(roundBitmap)
        val rectF = RectF(0f, 0f, size.toFloat(), size.toFloat())
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)
//        canvas.drawRoundRect(rectF, 200f.dpToPx() , 100f.dpToPx() , paint)
        return roundBitmap
    }

    fun Float.dpToPx(context: Context): Int {
        return context.resources.displayMetrics.density.toInt()
    }
}