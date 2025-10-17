package com.yaobing.framemvpproject.mylibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestDactivityBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ViewCBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ViewDBinding
import com.yaobing.module_apt.Router

@Router(value = "testd")
class TestDActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTestDactivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /**
         * 针对在约束布局下viewStub之间约束条件的设置说明
         * viewStub在inflate之后，会得到一个view，但是viewStub本身变为不可见并且会被移除。
         * 所以在设置约束条件时，不可直接在xml中对多个viewStub进行约束，这样会出现
         * “b在a下，但是a和b在inflate之后，b因为找不到a的id（a此时不可见，被移除了），所以b就处于顶部，造成显示位置异常”。
         * 解决方案：
         * 如果有多个viewStub进行依赖（比如viewStubB 设置在viewStubA下），
         * 那么就应该对A进行inflate监听，当a inflate完成之后会得到一个view，让viewStubB去设置在该view下即可
         */


        binding.vsC.setOnInflateListener { viewStub, view ->

            binding.vsD.layoutResource = R.layout.view_d
            val viewD = binding.vsD.inflate()
            ViewDBinding.bind(viewD)

            val lp = binding.vsD.layoutParams as ConstraintLayout.LayoutParams
            lp.topToBottom = view.id
            binding.vsD.layoutParams = lp

            Log.d("zxcv","vsC的可见性：" + binding.vsC.visibility)
            Log.d("zxcv","vsD的可见性：" + binding.vsD.visibility)
            Log.d("zxcv","viewC的id：" + view.id)
            Log.d("zxcv","vsC的id：" + binding.vsC.id)
            Log.d("zxcv","vsC的inflatedId：" + binding.vsC.inflatedId)
        }

        //设置默认layout
        binding.vsC.layoutResource = R.layout.view_c
        //获取viewStub view
        val viewC = binding.vsC.inflate()
        //绑定vs和view
        ViewCBinding.bind(viewC)

        ViewCompat.setTransitionName(binding.btC, "shared_element")
        binding.btC.setOnClickListener {
            finishAfterTransition()
        }

    }
}