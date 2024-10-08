package com.yaobing.framemvpproject.mylibrary.activity

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.adapter.TimeLineAdapter
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.Utils.AnimationUtil
import com.yaobing.module_middleware.Utils.UiTools
import kotlin.random.Random

@Router(value = "timeLineRecyclerviewactivity")
class TimeLineViewActivity : AppCompatActivity() {



    private val rvTimeLine by lazy {
        findViewById<RecyclerView>(R.id.rv_time_line)
    }

    private val timeLineAdapter by lazy {
        TimeLineAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line_recyclerview)
        UiTools.setStatusBarTransparent(this)
        initListener()
        initShopData()
        val header = findViewById<RelativeLayout>(R.id.ll_mine_header)

    }
    fun dp2px(dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

    private fun initListener() {
        var scrollY = 0
        var hasShownTitle = false
        rvTimeLine.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var a = recyclerView.canScrollVertically(-1)
                scrollY += dy
                if (recyclerView.canScrollVertically(-1) && scrollY >= dp2px(18f)) {
                    if (!hasShownTitle) {
                        hasShownTitle = true
                        Log.d("zxcv","----1dddd")
                        fadeAttentionView(true)
                    }
                } else {
                    if (hasShownTitle) {
                        Log.d("zxcv","----1qqqq")
                        hasShownTitle = false
                        fadeAttentionView(false)
                    }
                }
            }
        })
    }
    fun fadeAttentionView(toVisible: Boolean) {
        val mAnimationUtil = AnimationUtil()
        val view = findViewById<View>(R.id.v_bg)
        if (toVisible) {
            mAnimationUtil.fadeInToVisible(view)
        } else {
            mAnimationUtil.fadeOutToInvisible(view)
        }
    }
    private fun rotationArrow(img : ImageView, rotate:Float) {
        img.animate().setDuration(0).rotation(rotate)
    }

    private fun initShopData() {
        timeLineAdapter.emptyView = View.inflate(this@TimeLineViewActivity,R.layout.no_shop_view,null)
        timeLineAdapter.setHeaderView(View.inflate(this@TimeLineViewActivity,R.layout.rv_header,null))

        rvTimeLine.adapter = timeLineAdapter
        rvTimeLine.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val data = mutableListOf<RepoData>()
        for (i in 0 until 13) {
            val repoData = RepoData(i)
            repoData.full_name = "店铺名字： +$i"
            repoData.i = Random.nextInt(3,5)
            data.add(repoData)
        }
        timeLineAdapter.setNewData(data)
    }
}