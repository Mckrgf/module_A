package com.yaobing.framemvpproject.mylibrary.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.data.RepoData


/**
 * 零居页俱乐部-活动adapter
 */
class DemoAdapter() : BaseQuickAdapter<RepoData, BaseViewHolder>(R.layout.item_repo_hor) {

    override fun convert(helper: BaseViewHolder, item: RepoData) {
        val cardView = helper.getView<TextView>(R.id.tv_name)
        cardView.text = item.full_name
    }



}