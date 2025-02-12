package com.yaobing.framemvpproject.mylibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityLargeModeBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestDactivityBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ViewCBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ViewDBinding
import com.yaobing.module_apt.Router

@Router(value = "teste")
class LargeModeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLargeModeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}