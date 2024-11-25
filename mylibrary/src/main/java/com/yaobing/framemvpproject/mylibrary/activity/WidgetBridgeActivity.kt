package com.yaobing.framemvpproject.mylibrary.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.SPUtils
import com.yaobing.framemvpproject.mylibrary.TestActivity
import com.yaobing.framemvpproject.mylibrary.activity.activity.SplashActivity

class WidgetBridgeActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SPUtils.getInstance().getBoolean("splash", false)) {
            Log.d("zxcv", "home running")
            val intent1 = Intent(this, TestActivity::class.java)
            startActivity(intent1)
        } else {
            Log.d("zxcv", "home not running")
            val intent1 = Intent(this, SplashActivity::class.java)
            startActivity(intent1)
        }
        finish()
    }
}