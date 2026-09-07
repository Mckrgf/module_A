package com.yaobing.framemvpproject.mylibrary.activity

import android.R.id.tabs
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.BaseConstant
import com.yaobing.module_middleware.compose.theme.GreetingCardTheme
import com.yaobing.module_middleware.compose.view.CustomText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Router(BaseConstant.ComposeDemoActivity)
class ComposeDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContentView()
        }

    }
    @Preview(
        name = "卡片",
        showBackground = true,
        uiMode = UI_MODE_NIGHT_NO
    )
    @Composable
    fun GreetingCard(name: String = "123") {
        Card(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(56.dp).background(Color.Gray, CircleShape))
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(name, style = MaterialTheme.typography.titleMedium)
                    Text("Android 开发者", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Button(onClick = {}) { Text("关注") }
            }
        }
    }
    @Preview(
        name = "暗黑主题",
        showBackground = true,
        uiMode = UI_MODE_NIGHT_YES
    )
    @Composable
    private fun ContentView() {
        val tabs = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4")
        val pagerState = rememberPagerState(pageCount = { tabs.size }, initialPage = 0)
        val scope = rememberCoroutineScope()
        GreetingCardTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    Modifier
                        .systemBarsPadding()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp)
                ) {
                    CustomText("Compose如何实现沉浸式效果？")
                    CustomText("1：调用 enableEdgeToEdge()，实现全屏效果")
                    CustomText("2：设置 systemBarsPadding()，实现整体内容下滑")
                    CustomText(stringResource(R.string.compose_string_ps))


                    Column(modifier = Modifier.background(color = Color.Transparent).padding(top = 10.dp)) {
                        SubmitExample()
                        Button(onClick = {

                        }) {
                            Text("compose实现fragment加底部导航栏效果")
                        }

                        TabRow(selectedTabIndex = pagerState.currentPage) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = pagerState.currentPage == index,
                                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
                                ) {
                                    Text(text = title, modifier = Modifier.padding(16.dp))
                                }
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) { page ->
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = "这是 ${tabs[page]} 的内容", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }

            }
        }
    }
    @Composable
    fun SubmitExample(){
        var isLoading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Button(onClick = {
            scope.launch {
                isLoading = true
                delay(2000) // 模拟请求
                isLoading = false
            }
        }, enabled = !isLoading){
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(16.dp))
            else Text("模拟点击加载2s动画/显示隐藏顶部介绍")
        }
    }
}