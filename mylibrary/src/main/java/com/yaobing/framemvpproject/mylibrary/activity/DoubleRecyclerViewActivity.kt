package com.yaobing.framemvpproject.mylibrary.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.adapter.DemoAdapter
import com.yaobing.framemvpproject.mylibrary.adapter.PopWindowOptionAdapter
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.module_apt.Router
import kotlin.random.Random
import com.yaobing.module_middleware.Utils.StatusBarUtil

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

    private val rvA by lazy {
        findViewById<RecyclerView>(R.id.rv_a)
    }
    private val rvB by lazy {
        findViewById<RecyclerView>(R.id.rv_b)
    }
    private val vMask by lazy {
        findViewById<View>(R.id.viewMask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_recyclerview)
        StatusBarUtil.setAppStatusBar(this)
        initShopData()
        initSortData()
    }

    private fun initSortData() {
        val tvSort = findViewById<TextView>(R.id.tvSort)
        val tvType = findViewById<TextView>(R.id.tvType)

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

        popupMenu.isFocusable = false
        popupMenu.isOutsideTouchable = true
        popupMenu.animationStyle = R.style.pop_option_animation

        popupMenu.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.popMask
                )
            )
        )
        popupMenu.setOnDismissListener {
            vMask.visibility = View.GONE
        }
        tvSort.setOnClickListener {
            if (popupMenu.isShowing) {

                popupMenu.dismiss()
            } else {
                vMask.visibility = View.VISIBLE
                val rv = popupMenu.contentView.findViewById<RecyclerView>(R.id.rv)
                rv.layoutManager = LinearLayoutManager(this)
                val sortAdapter = PopWindowOptionAdapter(sorts)
                rv.adapter = sortAdapter
                sortAdapter.setNewData(sorts)
                popupMenu.showAsDropDown(tvSort)
                popupMenu.isFocusable = true

            }
        }
        tvType.setOnClickListener {
            if (popupMenu.isShowing) {
                popupMenu.dismiss()
            } else {
                vMask.visibility = View.VISIBLE
                val rv = popupMenu.contentView.findViewById<RecyclerView>(R.id.rv)
                rv.layoutManager = LinearLayoutManager(this)
                val typeAdapter = PopWindowOptionAdapter(types)
                rv.adapter = typeAdapter
                typeAdapter.setNewData(types)
                popupMenu.showAsDropDown(tvSort)
                popupMenu.isFocusable = true

            }
        }
    }
    private fun initShopData() {
        val demoAdapterA = DemoAdapter()
        val demoAdapterB = DemoAdapter()

        rvA.adapter = demoAdapterA
        rvB.adapter = demoAdapterB
        rvA.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvB.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val data = mutableListOf<RepoData>()
        for (i in 0 until 10) {
            val repoData = RepoData(i)
            repoData.full_name = "店铺名字： +$i"
            repoData.i = Random.nextInt(3,5)
            data.add(repoData)
        }
        demoAdapterA.setNewData(data)
        demoAdapterB.setNewData(data)
    }
}