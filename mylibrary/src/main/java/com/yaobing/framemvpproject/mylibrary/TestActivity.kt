package com.yaobing.framemvpproject.mylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yaobing.module_apt.Router

@Router("asdf")
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.bt_a).setOnClickListener {
            IntentRouter.go(this,"qwer")
        }
    }
}