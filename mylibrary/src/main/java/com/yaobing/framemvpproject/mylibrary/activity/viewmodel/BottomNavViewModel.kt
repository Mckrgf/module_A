package com.yaobing.framemvpproject.mylibrary.activity.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * BottomNavV2 页面的 ViewModel
 * 使用 ViewModel 管理状态，Activity 重建时状态不会丢失
 */
class BottomNavViewModel : ViewModel() {
    /**
     * 当前选中的 tab 索引
     */
    var selectedIndex by mutableStateOf(0)
        private set

    /**
     * 切换 tab
     */
    fun selectTab(index: Int) {
        selectedIndex = index
    }
}
