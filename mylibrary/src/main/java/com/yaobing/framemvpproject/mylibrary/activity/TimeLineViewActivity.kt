package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.adapter.TimeLineAdapter
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.module_apt.Router
import kotlin.random.Random
import com.yaobing.module_middleware.Utils.StatusBarUtil

@Router(value = "timeLineRecyclerviewactivity")
class TimeLineViewActivity : AppCompatActivity() {



    private val rvTimeLine by lazy {
        findViewById<RecyclerView>(R.id.rv_time_line)
    }

    private val timeLineAdapter by lazy {
        TimeLineAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line_recyclerview)
        StatusBarUtil.setAppStatusBar(this)
        initShopData()
    }

    private fun rotationArrow(img : ImageView, rotate:Float) {
        img.animate().setDuration(0).rotation(rotate)
    }

    private fun initShopData() {
        timeLineAdapter.emptyView = View.inflate(this@TimeLineViewActivity,R.layout.no_shop_view,null)


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