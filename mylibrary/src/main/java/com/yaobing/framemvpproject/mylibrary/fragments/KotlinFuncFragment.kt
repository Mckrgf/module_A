package com.yaobing.framemvpproject.mylibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.databinding.FragmentFuncBinding
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FuncFragment.
         */
        // TODO: Rename and change types and number of parameters
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