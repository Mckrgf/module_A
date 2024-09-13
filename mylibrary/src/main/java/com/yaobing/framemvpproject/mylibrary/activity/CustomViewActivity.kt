package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityCustomViewBinding
import com.yaobing.module_apt.Router

/**
 * 自定义控件的测试承载页
 */
@Router(value = "customviewactivity")
class CustomViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCustomViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btChange.setOnClickListener {
            binding.customView.changeBackgrand()
        }
    }

}