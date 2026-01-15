package com.yaobing.framemvpproject.mylibrary.ui.dialog

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.yaobing.framemvpproject.mylibrary.R

class BottomDialog(activity: Context) : AlertDialog(activity,R.style.bottom_pop_activity) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_bottom_sample)
        setCanceledOnTouchOutside(false)
    }

}