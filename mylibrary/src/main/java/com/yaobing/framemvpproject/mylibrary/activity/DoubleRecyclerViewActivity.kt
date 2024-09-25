package com.yaobing.framemvpproject.mylibrary.activity

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import com.yaobing.framemvpproject.mylibrary.adapter.PopWindowOptionAdapter
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.data.ShopData
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
class DoubleRecyclerViewActivity : AppCompatActivity() {

    private val popupMenu by lazy {
        PopupWindow(
            View.inflate(
                this,
                R.layout.popwindow_option,
                null
            ), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_recyclerview)
        val demoAdapterA = DemoAdapter()
        val demoAdapterB = DemoAdapter()
        val rvA = findViewById<RecyclerView>(R.id.rv_a)
        val rvB = findViewById<RecyclerView>(R.id.rv_b)
        val tvSort = findViewById<TextView>(R.id.tvSort)
        val tvType = findViewById<TextView>(R.id.tvType)
        val rv = popupMenu.contentView.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rvA.adapter = demoAdapterA
        rvB.adapter = demoAdapterB
        rvA.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvB.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var data = mutableListOf<RepoData>()
        for (i in 0 until 20) {
            val repoData = RepoData(i)
            repoData.full_name = "店铺名字： +$i"
            data.add(repoData)
        }
        demoAdapterA.setNewData(data)
        demoAdapterB.setNewData(data)

        val types = mutableListOf<String>()
        types.add("美食")
        types.add("住宿")
        types.add("体验")
        val sorts = mutableListOf<String>()
        sorts.add("综合排序")
        sorts.add("距离优先")
        sorts.add("好评优先")
        sorts.add("人气优先")
        sorts.add("福利优先")
        tvSort.setOnClickListener {
            Log.d("zxcv", "pop sort")

            val carTypeAdapter = PopWindowOptionAdapter(sorts)
            rv.adapter = carTypeAdapter
            // TODO: pop要show 
//            carTypeAdapter.setNewData(sorts)
        }

        tvType.setOnClickListener {
            Log.d("zxcv", "pop type")
        }


//
//
//        rv.adapter = carTypeAdapter


        // TODO: 通过点击排序/分类，给列表设置不同的数据，用同一个rv展示

    }


}