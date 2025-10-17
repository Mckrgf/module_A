package com.yaobing.framemvpproject.mylibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.yaobing.framemvpproject.mylibrary.adapter.ViewPagerAdapter
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityFuncShowBinding
import com.yaobing.framemvpproject.mylibrary.fragments.FuncFragment
import com.yaobing.framemvpproject.mylibrary.fragments.KotlinFuncFragment
import com.yaobing.module_apt.Router

@Router(value = "funcShowActivity")
class FuncShowActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFuncShowBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        window.enterTransition = null
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fragments = listOf(KotlinFuncFragment.newInstance("kotlin相关","0"),
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