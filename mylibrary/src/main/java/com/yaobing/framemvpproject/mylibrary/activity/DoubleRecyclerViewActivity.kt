package com.yaobing.framemvpproject.mylibrary.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
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

    private val demoAdapterA by lazy {
        DemoAdapter()
    }
    private val demoAdapterB  by lazy {
        DemoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_recyclerview)
        StatusBarUtil.setAppStatusBar(this)
        initLocation()
        initShopData()
        initSortData()
    }

    private fun initLocation() {
    }

    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    fun isLocServiceEnable(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    private fun initSortData() {
        val tvSort = findViewById<TextView>(R.id.tvSort)
        val tvType = findViewById<TextView>(R.id.tvType)

        val types = mutableListOf<String>()
        types.add("全部类型")
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
//        popupMenu.animationStyle = R.style.pop_option_animation

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
        val sortAdapter = PopWindowOptionAdapter(sorts)
        val typeAdapter = PopWindowOptionAdapter(types)

        tvSort.setOnClickListener {
            if (popupMenu.isShowing) {
                popupMenu.dismiss()
            } else {
                val rv = popupMenu.contentView.findViewById<RecyclerView>(R.id.rv)
                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = sortAdapter
                sortAdapter.setNewData(sorts)
                sortAdapter.emptyView = View.inflate(this@DoubleRecyclerViewActivity,R.layout.no_shop_view,null)
                popupMenu.showAsDropDown(tvSort)
                popupMenu.isFocusable = true
                sortAdapter.setOnItemClickListener { adapter, view, position ->
                    if (position == 0) {
                        tvSort.setTextColor(Color.parseColor("#333333"))
                    }else {
                        tvSort.setTextColor(resources.getColor(R.color.tabLineA))
                    }
                    popupMenu.dismiss()
                    tvSort.text = sorts[position]
                    sortAdapter.currentType = sorts[position]
                    //顺便展示下空数据
                    demoAdapterA.setNewData(null)

                }
                vMask.visibility = View.VISIBLE
            }
        }
        tvType.setOnClickListener {
            if (popupMenu.isShowing) {
                popupMenu.dismiss()
            } else {

                vMask.visibility = View.VISIBLE
                val rv = popupMenu.contentView.findViewById<RecyclerView>(R.id.rv)
                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = typeAdapter
                typeAdapter.setNewData(types)
                popupMenu.showAsDropDown(tvSort)
                popupMenu.isFocusable = true
                typeAdapter.setOnItemClickListener { adapter, view, position ->
                    if (position == 0) {
                        tvType.setTextColor(Color.parseColor("#333333"))
                    }else {
                        tvType.setTextColor(resources.getColor(R.color.tabLineA))
                    }
                    popupMenu.dismiss()
                    tvType.text = types[position]
                    typeAdapter.currentType = types[position]
                    //顺便展示下空数据
                    demoAdapterB.setNewData(null)
                }
            }
        }
    }
    private fun initShopData() {
        demoAdapterA.emptyView = View.inflate(this@DoubleRecyclerViewActivity,R.layout.no_shop_view,null)
        demoAdapterB.emptyView = View.inflate(this@DoubleRecyclerViewActivity,R.layout.no_shop_view,null)


        rvA.adapter = demoAdapterA
        rvB.adapter = demoAdapterB
        rvA.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvB.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val data = mutableListOf<RepoData>()
        for (i in 0 until 3) {
            val repoData = RepoData(i)
            repoData.full_name = "店铺名字： +$i"
            repoData.i = Random.nextInt(3,5)
            data.add(repoData)
        }
        demoAdapterA.setNewData(data)
        demoAdapterB.setNewData(data)
    }
}