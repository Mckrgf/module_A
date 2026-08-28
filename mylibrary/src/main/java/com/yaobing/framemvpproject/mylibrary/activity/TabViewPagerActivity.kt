package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.BaseConstant
import kotlin.random.Random

@Router(BaseConstant.TabViewPagerActivity)
class TabViewPagerActivity : AppCompatActivity() {
    data class MainTab(val title: String, val children: List<String>)

    //思路
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_viewpager)

        // 模拟从后台动态获取：生成随机主 tab（固定 4 个）及每个主 tab 的 0..3 个子 tab
        val mainTabs = mokeTabData()

        // 构建 pages 列表和映射：pages 按顺序包含主没有子时的主页，或子页
        // 例如 pages = [Tab1, 2A, 2B, Tab3, Tab4]
        val pages = mutableListOf<String>()
        // 例如 pageToMain = [0, 1, 1, 2, 3]
        val pageToMain = mutableListOf<Int>()
        // -1 表示主页,1-3 表示子页的位置
        val pageToChildIndex = mutableListOf<Int>()
        //表示每个主tab的第一个子页的索引
        val mainFirstPageIndex = IntArray(mainTabs.size)

        cokeData(mainTabs, mainFirstPageIndex, pages, pageToMain, pageToChildIndex)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val tabSubLayout = findViewById<TabLayout>(R.id.tab_sub_layout)

        viewPager.adapter = PagerAdapter(this, pages)

        // flags to avoid recursive callbacks when selecting programmatically
        var ignoreMainTabSelection = false
        var ignoreSubTabSelection = false
        var suppressSubTabSetup = false
        var currentMainIndex = 0

        // 初始化主 tabs
        for (main in mainTabs) {
            tabLayout.addTab(tabLayout.newTab().setText(main.title))
        }

        // helper: show/hide and set sub tabs for given main index
        fun setupSubTabsFor(mainIndex: Int) {
            suppressSubTabSetup = true
            tabSubLayout.removeAllTabs()
            val children = mainTabs[mainIndex].children
            if (children.isEmpty()) {
                tabSubLayout.visibility = View.GONE
            } else {
                for (c in children) tabSubLayout.addTab(tabSubLayout.newTab().setText(c))
                tabSubLayout.visibility = View.VISIBLE
            }
            suppressSubTabSetup = false
        }

        // 主 Tab 点击：若有子 tab 则显示并默认选中第一子项（并跳转到相应 page）；否则跳到主页
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (ignoreMainTabSelection) {
                    // programmatic select, ignore and clear flag
                    ignoreMainTabSelection = false
                    return
                }
                val mainIndex = tab.position
                currentMainIndex = mainIndex
                val children = mainTabs[mainIndex].children
                setupSubTabsFor(mainIndex)

                val target = mainFirstPageIndex[mainIndex]
                // 使用无动画切换，避免触发复杂的回调链
                if (viewPager.currentItem != target) viewPager.setCurrentItem(target, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // 二级 tab 点击 -> 切换到对应页面（mainFirstPageIndex + childIndex）
        tabSubLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (suppressSubTabSetup) return
                if (ignoreSubTabSelection) {
                    ignoreSubTabSelection = false
                    return
                }
                // 使用 currentMainIndex 来计算目标
                val mainIndex = currentMainIndex
                if (mainIndex < 0 || mainIndex >= mainTabs.size) return
                val childIndex = tab.position
                val target = mainFirstPageIndex[mainIndex] + childIndex
                if (viewPager.currentItem != target) viewPager.setCurrentItem(target, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // ViewPager 页面变更监听：根据 page 更新主 tab 与二级 tab（全部通过手动 select）
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position < 0 || position >= pageToMain.size) return
                val mainIndex = pageToMain[position]
                val childIndex = pageToChildIndex[position]

                // 选中主 tab（如果未选中）
                val mainTab = tabLayout.getTabAt(mainIndex)
                if (mainTab != null && !mainTab.isSelected) {
                    ignoreMainTabSelection = true
                    currentMainIndex = mainIndex
                    mainTab.select()
                } else {
                    currentMainIndex = mainIndex
                }

                // 设置子 tab 状态
                val children = mainTabs[mainIndex].children
                if (children.isEmpty()) {
                    tabSubLayout.visibility = View.GONE
                } else {
                    // 确保子 tab 已初始化为该 main 的子项目
                    setupSubTabsFor(mainIndex)
                    val subPos = if (childIndex >= 0) childIndex else 0
                    val subTab = tabSubLayout.getTabAt(subPos)
                    if (subTab != null && !subTab.isSelected) {
                        ignoreSubTabSelection = true
                        subTab.select()
                    }
                }
            }
        })

        // 初始选中第一个主 tab（触发初始化逻辑）
        if (tabLayout.tabCount > 0) tabLayout.getTabAt(0)?.select()
    }

    private fun cokeData(mainTabs: MutableList<MainTab>, mainFirstPageIndex: IntArray, pages: MutableList<String>, pageToMain: MutableList<Int>, pageToChildIndex: MutableList<Int>) {
        var pageIndex = 0
        for ((mIndex, main) in mainTabs.withIndex()) {
            mainFirstPageIndex[mIndex] = pageIndex
            if (main.children.isEmpty()) {
                pages.add(main.title)
                pageToMain.add(mIndex)
                pageToChildIndex.add(-1)
                pageIndex++
            } else {
                for ((cIndex, child) in main.children.withIndex()) {
                    pages.add(child)
                    pageToMain.add(mIndex)
                    pageToChildIndex.add(cIndex)
                    pageIndex++
                }
            }
        }
    }

    private fun mokeTabData(): MutableList<MainTab> {
        val mainCount = 4
        val mainTabs = mutableListOf<MainTab>()
        for (i in 1..mainCount) {
            val childCount = Random.nextInt(0, 4) // 0..3
            val children = mutableListOf<String>()
            for (j in 1..childCount) {
                // 命名如 2A/2B/2C
                val suffix = ('A' + (j - 1)).toString()
                children.add("${i}$suffix")
            }
            mainTabs.add(MainTab("Tab $i", children))
        }
        return mainTabs
    }

    private class PagerAdapter(fa: FragmentActivity, private val pages: List<String>) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = pages.size
        override fun createFragment(position: Int): Fragment = PageFragment.newInstance(position + 1, pages[position])
    }

    class PageFragment : Fragment(R.layout.fragment_page) {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val args = requireArguments()
            val index = args.getInt(ARG_INDEX)
            val title = args.getString(ARG_TITLE)
            val tv = view.findViewById<TextView>(R.id.text_page)
            tv.text = "第 $index 页：$title"
        }

        companion object {
            private const val ARG_INDEX = "arg_index"
            private const val ARG_TITLE = "arg_title"
            fun newInstance(index: Int, title: String): PageFragment {
                val f = PageFragment()
                val b = Bundle()
                b.putInt(ARG_INDEX, index)
                b.putString(ARG_TITLE, title)
                f.arguments = b
                return f
            }
        }
    }
}
