package com.yaobing.framemvpproject.mylibrary.adapter

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.data.RepoData


/**
 * 零居页俱乐部-活动adapter
 */
class TimeLineAdapter() : BaseQuickAdapter<RepoData, BaseViewHolder>(R.layout.item_dash_shop) {
    fun dp2pxInt(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
            .toInt()
    override fun convert(helper: BaseViewHolder, item: RepoData) {
//        val lp: ViewGroup.MarginLayoutParams = helper.itemView.layoutParams as ViewGroup.MarginLayoutParams
//        var marginTop = 0
//        lp.topMargin = if (helper.layoutPosition == 0) {
//            dp2pxInt(10f)
//        }else {
//            dp2pxInt(20f)
//        }
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



}