package com.yaobing.framemvpproject.mylibrary.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import com.yaobing.framemvpproject.mylibrary.activity.TestDActivity
import com.yaobing.framemvpproject.mylibrary.databinding.FragmentKotlinFuncBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KotlinFuncFragment : FuncFragment() {
    private lateinit var bindingRoot : FragmentKotlinFuncBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRoot = FragmentKotlinFuncBinding.inflate(layoutInflater)
        bindingRoot.btCor.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                netRequest()
                Log.d("zxcv","模拟请求彻底结束，线程是：" + Thread.currentThread().name)

                withContext(Dispatchers.Main) {
                    Log.d("zxcv","主线程更新UI，线程："  + Thread.currentThread().name)
                }
            }
        }
        bindingRoot.btAnimiation.setOnClickListener {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),                // 当前 Activity
                bindingRoot.btAnimiation,          // 源 Activity 中的共享视图
                "shared_element"     // 过渡名称，需与目标 Activity 匹配
            )
            val bundleA = options.toBundle()
            startActivity(Intent(requireActivity(), TestDActivity::class.java), bundleA)
        }
    }
    private suspend fun netRequest() {
        Log.d("zxcv","模拟请求开始，线程："  + Thread.currentThread().name)
        delay(5000)
        Log.d("zxcv","模拟请求结束，线程："  + Thread.currentThread().name)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingRoot.root
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KotlinFuncFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}