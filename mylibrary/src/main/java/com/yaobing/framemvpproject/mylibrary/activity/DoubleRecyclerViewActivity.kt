package com.yaobing.framemvpproject.mylibrary.activity

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.horizontal.HorizontalFooter
import com.scwang.smart.refresh.horizontal.SmartRefreshHorizontal
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import com.scwang.smart.refresh.layout.wrapper.RefreshFooterWrapper
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.adapter.DemoAdapter
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.activity.BaseControllerActivity


//
/**
 * 结合viewModel+liveData+paging+协程+retrofit，具体结构如下
 *
 * 1.定义接口service用来保存retrofit请求方法
 * 本来是用call/single/observable等作为返回参数，在用suspend关键字的前提下，用返回数据的类型即可
 * 再定义一个create方法用来实例化该retrofit的service
 * 2.定义一个Repository单例（通过object关键字），定义一个fun，传入Pager的构造函数（config+pagingResourceFactory），同时返回flow对象
 * 3。2缺一个pagingSource，做一个。
 * 4.
 */

@Router(value = "doubleRecyclerviewactivity")
class DoubleRecyclerViewActivity : BaseControllerActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoAdapterA = DemoAdapter()
        val demoAdapterB = DemoAdapter()
        val rvA = findViewById<RecyclerView>(R.id.rv_a)
        val rvB = findViewById<RecyclerView>(R.id.rv_b)
        rvA.adapter = demoAdapterA
        rvB.adapter = demoAdapterB
        rvA.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvB.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        var data = mutableListOf<RepoData>()
        for (i in 0 until 20) {
            val repoData = RepoData(i)
            repoData.full_name = "我是名字： +$i"
            data.add(repoData)
        }
        demoAdapterA.setNewData(data)
        demoAdapterB.setNewData(data)

    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_double_recyclerview)
    }


}