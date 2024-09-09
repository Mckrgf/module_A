package com.yaobing.framemvpproject.mylibrary.activity

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.horizontal.SmartRefreshHorizontal
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
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

@Router(value = "recyclerviewactivity")
class RecyclerViewActivity : BaseControllerActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoAdapter = DemoAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        findViewById<View>(R.id.progress_bar).visibility = View.GONE
        recyclerView.adapter = demoAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        var data = mutableListOf<RepoData>()
        for (i in 0 until 10) {
            val repoData = RepoData(i)
            repoData.full_name = "我是名字： +$i"
            data.add(repoData)
        }
        demoAdapter.setNewData(data)

        val refreshLayout: SmartRefreshHorizontal = findViewById(R.id.srh)
        val footer = View.inflate(getContext(),R.layout.item_load_more,null)
        refreshLayout.setRefreshHeader(MaterialHeader(getContext()))
        refreshLayout.setRefreshFooter(
            RefreshFooterWrapper(footer)
        )
        refreshLayout.setEnableAutoLoadMore(false);//使上拉加载具有弹性效果
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                Log.d("zxcv","refresh")
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                Log.d("zxcv","loadMore")
                refreshLayout.closeHeaderOrFooter()
            }

        })
    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_paging_hor)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    fun dp2pxInt(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
            .toInt()
}