package com.yaobing.framemvpproject.mylibrary.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yaobing.framemvpproject.mylibrary.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        val bt = findViewById<Button>(R.id.bt_jump)


        bt.setOnClickListener {
            var intent = Intent(this,HOmeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
            startActivity(intent)
        }
        Thread.sleep(1000) // 延迟 1000 毫秒（1 秒）
        Log.d("zxcv","延迟了")
//        retutnTo()

    }

    override fun onResume() {
        super.onResume()


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

}}