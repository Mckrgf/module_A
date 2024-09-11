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
        val customFooterView = View.inflate(getContext(),R.layout.item_load_more,null)
        val footer : HorizontalFooter= HorizontalFooter(customFooterView)
        footer.gravity = Gravity.CENTER_VERTICAL
        footer.setBackgroundColor(resources.getColor(R.color.greenPlayAll,null))
        refreshLayout.setRefreshHeader(MaterialHeader(getContext()))
        refreshLayout.setRefreshFooter(
            RefreshFooterWrapper(footer)
        )

        //动画
        val animator = ObjectAnimator.ofFloat(customFooterView, "rotation", 0f, 180f)
        animator.setDuration(200)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            animator.animatedValue//获取进度
        }


        refreshLayout.setEnableAutoLoadMore(false);//使上拉加载具有弹性效果
        refreshLayout.setOnMultiListener(object : OnMultiListener {

            override fun onFooterMoving(
                footer: RefreshFooter?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                footerHeight: Int,
                maxDragHeight: Int
            ) {
                Log.d("zxcv","footer move \n percent: $percent \n offset: $offset \n" +
                        " footerHeight: $footerHeight\n" +
                        " maxDragHeight: $maxDragHeight ")
                if (percent < 1) {


                    animator.start()
                }else {

                }
            }
            override fun onRefresh(refreshLayout: RefreshLayout) {
                Log.d("zxcv","refresh")
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                Log.d("zxcv","loadMore")
                refreshLayout.closeHeaderOrFooter()
            }

            override fun onStateChanged(
                refreshLayout: RefreshLayout,
                oldState: RefreshState,
                newState: RefreshState
            ) {
                Log.d("zxcv","onStateChanged")
            }


            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onHeaderReleased(
                header: RefreshHeader?,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onHeaderStartAnimator(
                header: RefreshHeader?,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {

            }



            override fun onFooterReleased(
                footer: RefreshFooter?,
                footerHeight: Int,
                maxDragHeight: Int
            ) {
                Log.d("zxcv","onFooterReleased")
            }

            override fun onFooterStartAnimator(
                footer: RefreshFooter?,
                footerHeight: Int,
                maxDragHeight: Int
            ) {
                Log.d("zxcv","onFooterStartAnimator")
            }

            override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {
                Log.d("zxcv","onFooterFinish")
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