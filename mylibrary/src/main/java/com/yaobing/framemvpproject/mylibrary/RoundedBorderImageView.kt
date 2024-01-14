package com.yaobing.framemvpproject.mylibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class RoundedBorderImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var borderWidth = 10


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制边框
        // 绘制边框
        val rect = RectF(0f, 0f,width.toFloat(), height.toFloat())
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        canvas.drawRoundRect(rect, 10f, 10f, paint)
    }
}