package com.yaobing.framemvpproject.mylibrary.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yaobing.framemvpproject.mylibrary.R

class PopWindowOptionAdapter(carType: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_option_popwindow) {
    var currentType = carType?.get(0)
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setTextColor(R.id.tv, if (item== currentType) mContext.getColor(R.color.tabLineA) else  mContext.getColor(R.color.color_333333))
        helper.setText(R.id.tv, item ?: "")
    }
}