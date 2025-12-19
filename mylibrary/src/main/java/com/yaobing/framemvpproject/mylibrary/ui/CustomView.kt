package com.yaobing.framemvpproject.mylibrary.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yaobing.framemvpproject.mylibrary.databinding.CustomView2Binding
import com.yaobing.framemvpproject.mylibrary.databinding.CustomViewBinding

class CustomView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
//    var binding = LayoutInflater.from(context).inflate(CustomViewBinding)
    //直接在自定义控件内inflate就可以设置背景了,如果设置多次，以最新的为准
    val binding1 = CustomViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun changeBackgrand() {
        val binding2 = CustomView2Binding.inflate(LayoutInflater.from(context), this, true)

    }
}