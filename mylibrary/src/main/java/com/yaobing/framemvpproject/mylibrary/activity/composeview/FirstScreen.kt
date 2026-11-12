package com.yaobing.framemvpproject.mylibrary.activity.composeview

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.blankj.utilcode.util.SnackbarUtils.addView
import kotlin.math.roundToInt

@Preview
@Composable
fun FirstScreen(title: String = "fef") {
    val tabTitles = listOf("推荐", "关注") + (1..10).map { "其他$it" }
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    // 0=显示, 1=隐藏
    var tabVisibility by rememberSaveable { mutableIntStateOf(0) }
    
    // 获取状态栏高度
    val statusBarHeight = 20.dp

    // Tab 高度（状态栏 padding + Tab 内容高度）
    val tabTotalHeight = statusBarHeight + 48.dp

    // Tab 偏移动画
    val tabOffsetY by animateDpAsState(
        targetValue = if (tabVisibility == 1) (-tabTotalHeight) else 0.dp,
        animationSpec = tween(300),
        label = "tab_offset"
    )

    // 缓存每个 tab 的 WebView 实例
    val webViewCache = remember { mutableMapOf<Int, android.webkit.WebView>() }

    // 使用 Box 叠加 Tab 和 List
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 内容区域
        when (selectedTabIndex) {
            0 -> RecommendTabContent(
                tabVisibility = tabVisibility,
                tabTotalHeight = tabTotalHeight,
                onScrollDirectionChange = { isScrollingUp ->
                    tabVisibility = if (isScrollingUp) 1 else 0
                }
            )
            else -> {
                if (selectedTabIndex == 1) {
                    // "关注" tab 显示居中标题
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = tabTotalHeight + 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tabTitles[selectedTabIndex],
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                } else {
                    // 其他 tab 用 WebView 展示，带下拉刷新
                    WebViewWithRefresh(
                        tabIndex = selectedTabIndex,
                        webViewCache = webViewCache,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = tabTotalHeight)
                    )
                }
            }
        }

        // Tab 浮在上方
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = tabOffsetY)
                .padding(0.dp, statusBarHeight, 0.dp, 0.dp),
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .padding(16.dp,0.dp,16.dp,2.dp,)
                        .height(4.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Blue, Color.Green,Color.Blue)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                )
            }
        ) {
            tabTitles.forEachIndexed { index, tabTitle ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = tabTitle,
                            color = Color.Black
                        )
                    },
                    modifier = Modifier.background(Color.White),
                )
            }
        }
    }
}

@Composable
private fun RecommendTabContent(
    tabVisibility: Int,
    tabTotalHeight: androidx.compose.ui.unit.Dp,
    onScrollDirectionChange: (Boolean) -> Unit
) {
    val listItems = (1..50).map { "推荐内容 item $it" }
    val listState = rememberLazyListState()

    // List 的 paddingTop - Tab 隐藏时减小到 0，Tab 显示时等于 tabTotalHeight
    val listTopPadding by animateDpAsState(
        targetValue = if (tabVisibility == 1) 0.dp else tabTotalHeight,
        animationSpec = tween(300),
        label = "list_padding"
    )

    // 监听滚动方向
    var previousIndex by remember { mutableIntStateOf(0) }
    var previousScrollOffset by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val currentIndex = listState.firstVisibleItemIndex
        val currentOffset = listState.firstVisibleItemScrollOffset

        // 判断滚动方向
        val isScrollingUp = when {
            currentIndex > previousIndex -> true
            currentIndex < previousIndex -> false
            currentOffset > previousScrollOffset -> true
            currentOffset < previousScrollOffset -> false
            else -> null
        }

        isScrollingUp?.let { onScrollDirectionChange(it) }

        previousIndex = currentIndex
        previousScrollOffset = currentOffset
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = listTopPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listItems) { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun WebViewWithRefresh(
    tabIndex: Int,
    webViewCache: MutableMap<Int, android.webkit.WebView>,
    modifier: Modifier = Modifier
) {
    // 使用 key 强制在 tab 切换时重新组合
    androidx.compose.runtime.key(tabIndex) {
        AndroidView(
            factory = { context ->
                // 从缓存获取或创建新的 WebView
                val webView = webViewCache.getOrPut(tabIndex) {
                    android.webkit.WebView(context).apply {
                        settings.javaScriptEnabled = true
                        // 设置 WebViewClient 让链接在内部打开
                        webViewClient = android.webkit.WebViewClient()
                        loadUrl("https://www.baidu.com")
                    }
                }
                
                // 如果 WebView 已经有父 View，先移除
                (webView.parent as? android.view.ViewGroup)?.removeView(webView)
                
                // 使用 SwipeRefreshLayout 包裹 WebView
                androidx.swiperefreshlayout.widget.SwipeRefreshLayout(context).apply {
                    addView(webView)
                    setOnRefreshListener {
                        webView.reload()
                        isRefreshing = false
                    }
                }
            },
            modifier = modifier
        )
    }
}
