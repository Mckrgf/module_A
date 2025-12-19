package com.yaobing.framemvpproject.mylibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class MaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private var holeRect = Rect()

    private val cornerRadius = dpToPx(10f) // 圆角半径

    fun setHoleRect(rect: Rect) {
        holeRect.set(rect)
        invalidate()
    }

    // 转换为px的边距
    private val marginHorizontal = dpToPx(25f)
    private val marginTop = dpToPx(120f) + dpToPx(60f) // iv_close bottom + marginTop
    private val holeHeight = dpToPx(200f) // 假设iv_card_front高度，实际可根据图片调整

    private fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制全屏黑色背景
        paint.color = Color.BLACK
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // 清除中间洞（对应iv_card_front位置）
        if (holeRect.isEmpty.not()) {
            canvas.drawRoundRect(holeRect.left.toFloat(), holeRect.top.toFloat(), holeRect.right.toFloat(), holeRect.bottom.toFloat(), cornerRadius, cornerRadius, clearPaint)
        }
    }
}
