package com.yaobing.framemvpproject.mylibrary.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.yaobing.framemvpproject.mylibrary.databinding.LayoutMyClubMomentNewBinding

class ClubMomentCardView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private val binding =
        LayoutMyClubMomentNewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        LayoutMyClubMomentNewBinding.inflate(LayoutInflater.from(context), this, true)
    }

}