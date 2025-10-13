package com.yaobing.framemvpproject.mylibrary.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.adapter.ViewPagerAdapter
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityFuncShowBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestDactivityBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ViewCBinding
import com.yaobing.framemvpproject.mylibrary.databinding.ViewDBinding
import com.yaobing.framemvpproject.mylibrary.fragments.FuncFragment
import com.yaobing.module_apt.Router

@Router(value = "CeilingAlphaActivity")
class FuncShowActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFuncShowBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fragments = listOf(FuncFragment.newInstance("kotlin相关","0"),
            FuncFragment.newInstance("待续","1"),
            FuncFragment.newInstance("待续","2"),
            FuncFragment.newInstance("待续","3"),
            FuncFragment.newInstance("待续","4")
            )

        val tabTitles = listOf("kotlin相关","待续","待续","待续","待续")

        val adapter = ViewPagerAdapter(this,fragments)
        binding.vpFunc.adapter = adapter

        TabLayoutMediator(binding.tlFunc,binding.vpFunc) { tab,pos ->
            tab.text = tabTitles[pos]
        }.attach()
    }
}