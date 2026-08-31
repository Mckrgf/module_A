package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@Router(BaseConstant.TabViewPagerTwoActivity)
class TabViewPagerTwoActivity : AppCompatActivity() {

    // 主 tab 数据模型
    data class MainTab(val title: String, val children: List<String>)

    // 扁平化 page 表示：tabIndex + 可选 childIndex（null 表示无 child）
    data class PageEntry(val tabIndex: Int, val childIndex: Int?)

    private val mainTabs = mutableListOf<MainTab>()
    private val pages = mutableListOf<PageEntry>()

    // 映射：Pair(tabIndex, childIndex) -> pageIndex
    private val pairToPage = HashMap<Pair<Int, Int?>, Int>()

    private lateinit var tabLayout: TabLayout
    private lateinit var subTabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private var isSettingPagerProgrammatically = false
    private var isSettingTabProgrammatically = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_viewpager)

        tabLayout = findViewById(R.id.tab_layout)
        subTabLayout = findViewById(R.id.tab_sub_layout)
        viewPager = findViewById(R.id.view_pager)

        // 随机生成 10 个主 tab，每个 0-4 个 child
        generateRandomTabs()

        // 构建扁平 pages 和映射
        buildPagesAndMappings()

        // 初始化 ViewPager 的 adapter
        viewPager.adapter = object : FragmentStateAdapter(this as FragmentActivity) {
            override fun getItemCount(): Int = pages.size
            override fun createFragment(position: Int): Fragment {
                val entry = pages[position]
                val main = mainTabs[entry.tabIndex]
                val label = if (entry.childIndex == null) {
                    "Tab ${entry.tabIndex}: ${main.title}"
                } else {
                    "Tab ${entry.tabIndex} - Child ${entry.childIndex}: ${main.title} / ${main.children[entry.childIndex] }"
                }
                return PageFragment.newInstance(label)
            }
        }

        // 填充主 Tab UI
        populateMainTabs()

        // 监听主 tab 点击（用户交互）
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                if (isSettingTabProgrammatically) return // 忽略程序化触发

                val tabIndex = tab.position
                val main = mainTabs[tabIndex]

                // 如果主 tab 有 children，默认选第一个 child
                val targetPair = if (main.children.isEmpty()) {
                    Pair(tabIndex, null)
                } else {
                    Pair(tabIndex, 0)
                }

                val page = pairToPage[targetPair] ?: 0
                isSettingPagerProgrammatically = true
                viewPager.setCurrentItem(page, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 监听子 tab 点击
        subTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                if (isSettingTabProgrammatically) return

                val mainIndex = tab.tag as? Int ?: return
                val childIndex = tab.position
                val page = pairToPage[Pair(mainIndex, childIndex)] ?: return

                isSettingPagerProgrammatically = true
                viewPager.setCurrentItem(page, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 当 ViewPager 页面变化时，更新主/子 Tab 的选中状态
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val entry = pages[position]

                // 程序设置页面时，避免在 tab listener 中触发 setCurrentItem 循环
                isSettingTabProgrammatically = true

                // 选中主 tab（如果需要）
                val mainTab = tabLayout.getTabAt(entry.tabIndex)
                mainTab?.select()

                // 更新并展示/隐藏子 tab
                val main = mainTabs[entry.tabIndex]
                if (main.children.isEmpty()) {
                    subTabLayout.visibility = View.GONE
                } else {
                    subTabLayout.visibility = View.VISIBLE
                    // 重新填充子 tab（也可以缓存，但为了简单直接重建）
                    populateSubTabs(entry.tabIndex)
                    // 选择对应 child（entry.childIndex 不应为 null）
                    val childIndex = entry.childIndex ?: 0
                    val childTab = subTabLayout.getTabAt(childIndex)
                    childTab?.select()
                }

                // 重置标志
                isSettingPagerProgrammatically = false
                isSettingTabProgrammatically = false
            }
        })

        // 默认选中第一个主 tab
        if (pages.isNotEmpty()) viewPager.setCurrentItem(0, false)
    }

    // 随机生成数据
    private fun generateRandomTabs() {
        mainTabs.clear()
        val rnd = Random(System.currentTimeMillis())
        repeat(10) { i ->
            val childCount = rnd.nextInt(0, 5) // 0..4
            val children = mutableListOf<String>()
            repeat(childCount) { c -> children.add("Child-$c") }
            mainTabs.add(MainTab("Main-$i", children))
        }
    }

    // 构建扁平 pages 列表和映射
    private fun buildPagesAndMappings() {
        pages.clear()
        pairToPage.clear()
        for (t in mainTabs.indices) {
            val children = mainTabs[t].children
            if (children.isEmpty()) {
                val idx = pages.size
                pages.add(PageEntry(t, null))
                pairToPage[Pair(t, null)] = idx
            } else {
                for (c in children.indices) {
                    val idx = pages.size
                    pages.add(PageEntry(t, c))
                    pairToPage[Pair(t, c)] = idx
                }
            }
        }
    }

    // 填充主 tab（只执行一次）
    private fun populateMainTabs() {
        tabLayout.removeAllTabs()
        for (i in mainTabs.indices) {
            val t = tabLayout.newTab().setText(mainTabs[i].title)
            tabLayout.addTab(t)
        }
    }

    // 填充子 tab：将子 tab 的 tag 设为主 tab index，方便在监听中取回
    private fun populateSubTabs(mainIndex: Int) {
        subTabLayout.removeAllTabs()
        val children = mainTabs[mainIndex].children
        for (i in children.indices) {
            val t = subTabLayout.newTab().setText(children[i])
            t.tag = mainIndex
            subTabLayout.addTab(t)
        }
    }

    // 简单页面 Fragment：仅展示 label
    class PageFragment : Fragment() {
        companion object {
            private const val ARG_LABEL = "arg_label"
            fun newInstance(label: String): PageFragment {
                val f = PageFragment()
                val b = Bundle()
                b.putString(ARG_LABEL, label)
                f.arguments = b
                return f
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val v = inflater.inflate(R.layout.fragment_page, container, false)
            val tv = v.findViewById<TextView>(R.id.text_page)
            tv.text = arguments?.getString(ARG_LABEL) ?: "Page"
            return v
        }
    }
}
