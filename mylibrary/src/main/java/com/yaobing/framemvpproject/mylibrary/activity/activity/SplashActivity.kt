package com.yaobing.framemvpproject.mylibrary.activity.activity

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.TestActivity

class SplashActivity : AppCompatActivity() {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("zxcv", "splash onCreate")
        setContentView(R.layout.activity_splash)
        val intent = intent
        val bt = findViewById<Button>(R.id.bt_jump)


        bt.setOnClickListener {
            var intent = Intent(this, HOmeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
            startActivity(intent)
        }
//        Thread.sleep(1000) // 延迟 1000 毫秒（1 秒）
        Log.d("zxcv", "延迟了")
        // 使用 Handler 执行延时操作
        handler.postDelayed({
            // 延时 2000 毫秒（2 秒）后执行的操作
            // 例如，更新 UI 或执行其他逻辑
            runOnUiThread {
                // 更新 UI 的代码
                var intent = Intent(this, TestActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }, 2000)

    }

    override fun onResume() {
        super.onResume()
        SPUtils.getInstance().put("splash", true)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("zxcv", "splash onNewIntent")
    }

    private fun retutnTo() {

        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0
        ) {
            Log.d("zxcv", "splash界面关闭")
            //在这加个耗时,看看还会不会让上一个activity onStop了
            try {


                finish()

            } catch (e: InterruptedException) {
                e.printStackTrace()
                Log.d("zxcv", e.toString())
//        } //18:54:40.805开始home回到主页


            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        // 在 Activity 销毁时移除所有未执行的回调
        handler.removeCallbacksAndMessages(null)
    }
}