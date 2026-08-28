package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
//import androidx.compose.foundation.pager.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.yaobing.module_middleware.compose.theme.GreetingCardTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class ComposeTabViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TabPagerScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabPagerScreen() {
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4")
//    val pagerState = rememberPagerState()
//    val scope = rememberCoroutineScope()
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .systemBarsPadding()
//        .padding(8.dp)) {
//
//        val selectedIndex by remember { derivedStateOf { pagerState.currentPage } }
//
//        TabRow(selectedTabIndex = selectedIndex) {
//            tabs.forEachIndexed { index, title ->
//                Tab(
//                    selected = selectedIndex == index,
//                    onClick = {
//                        // 点击 tab 手动更新 viewpager
//                        scope.launch { pagerState.animateScrollToPage(index) }
//                    },
//                    text = { Text(title) }
//                )
//            }
//        }

        // viewpager 监听滑动，手动更新 tab（selectedIndex 从 pagerState.currentPage 派生）
//        HorizontalPager(
//            pageCount = tabs.size,
//            state = pagerState,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) { page ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = if (page % 2 == 0) Color(0xFFE3F2FD) else Color(0xFFFFF3E0)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "这是第 ${page + 1} 页", style = MaterialTheme.typography.headlineMedium)
//            }
//        }
//    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabPagerPreview() {
    GreetingCardTheme {
        TabPagerScreen()
    }
}
