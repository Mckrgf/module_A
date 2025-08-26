package com.yaobing.framemvpproject.mylibrary.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.yaobing.framemvpproject.mylibrary.databinding.LayoutMyClubMomentBinding

class ClubMomentCardView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private val binding =
        LayoutMyClubMomentBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        LayoutMyClubMomentBinding.inflate(LayoutInflater.from(context), this, true)
    }

}