package com.yaobing.framemvpproject.mylibrary.adapter

import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.ui.DashedLineHorView
import com.yaobing.framemvpproject.mylibrary.ui.DashedLineVerticalView


/**
 * 零居页俱乐部-活动adapter
 */
class TimeLineAdapter() : BaseQuickAdapter<RepoData, BaseViewHolder>(R.layout.item_dash_shop) {
    var map = HashMap<String,Boolean>()
    var mapPosition = HashMap<String,Int>()
    fun dp2pxInt(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
            .toInt()
    override fun convert(helper: BaseViewHolder, item: RepoData) {

        setYearView(item, helper)
        setDashLine(helper)
        helper.setText(R.id.tvYear,item.year)
        map[item.year.toString()] = true
        helper.setText(R.id.tv_name,item.full_name)
        helper.setText(R.id.tv_star,"" + item.i + "")
        helper.itemView.findViewById<TextView>(R.id.tv_coupon_content_b).post {
            val x = helper.itemView.width // view距离 屏幕左边的距离（即x轴方向）
            val x1 = helper.itemView.findViewById<TextView>(R.id.tv_coupon_content_b).right
            Log.d("zxcv","itemView y：" + x)
            Log.d("zxcv","tag y：" + x1)
            if (x1 > x) {
                helper.itemView.findViewById<TextView>(R.id.tv_coupon_content_b).visibility = View.GONE
                helper.itemView.findViewById<TextView>(R.id.tv_coupon_b).visibility = View.GONE
            }else {
                helper.itemView.findViewById<TextView>(R.id.tv_coupon_content_b).visibility = View.VISIBLE
                helper.itemView.findViewById<TextView>(R.id.tv_coupon_b).visibility = View.VISIBLE
            }
        }

    }

    private fun setYearView(item: RepoData, helper: BaseViewHolder) {
        var hasShowYear = false
        hasShowYear = if (null == map[item.year.toString()]) {
            false
        } else {
            map[item.year.toString()]!!
        }
        if (hasShowYear && mapPosition[item.year] != helper.layoutPosition) {
            helper.itemView.findViewById<View>(R.id.tvYear).visibility = View.GONE
            helper.itemView.findViewById<View>(R.id.iv_dot_year).visibility = View.GONE
            helper.itemView.findViewById<View>(R.id.tv_star).visibility = View.GONE
        } else if (!TextUtils.isEmpty(item.year)) {
            helper.itemView.findViewById<View>(R.id.tvYear).visibility = View.VISIBLE
            helper.itemView.findViewById<View>(R.id.iv_dot_year).visibility = View.VISIBLE
            helper.itemView.findViewById<View>(R.id.tv_star).visibility = View.GONE
            mapPosition[item.year] = helper.layoutPosition
        }
    }

    private fun setDashLine(helper: BaseViewHolder) {
        val dashLine = helper.itemView.findViewById<DashedLineVerticalView>(R.id.dashLine)
        val layoutParams = dashLine.layoutParams as ConstraintLayout.LayoutParams
        if (helper.layoutPosition == 1) {
            layoutParams.topToTop = R.id.iv_dot_year
            layoutParams.topMargin = dp2pxInt(2f)
        } else {
            layoutParams.topToTop = 0
            layoutParams.topMargin = dp2pxInt(0f)
        }
        dashLine.layoutParams = layoutParams
    }


}