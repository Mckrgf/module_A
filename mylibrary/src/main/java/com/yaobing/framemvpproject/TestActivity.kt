package com.yaobing.framemvpproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yaobing.module_apt.Router

@Router("asdf")
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}