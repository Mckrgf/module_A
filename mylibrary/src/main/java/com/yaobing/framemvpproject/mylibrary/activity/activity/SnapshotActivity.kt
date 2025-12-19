package com.yaobing.framemvpproject.mylibrary.activity.activity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnDrawListener
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.yaobing.framemvpproject.mylibrary.R


class SnapshotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snapshot)
        val v = findViewById<ScrollView>(R.id.scrollView)

        v.viewTreeObserver.addOnDrawListener {
            val h = v.height
            val w = v.width
            val bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            v.draw(canvas)
            Log.d("zxcv","下一步需要处理bitmap看看保存")
            v.viewTreeObserver.removeOnDrawListener {

            }
        }
    }


}