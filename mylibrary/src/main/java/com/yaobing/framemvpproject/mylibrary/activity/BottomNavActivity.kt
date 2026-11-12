package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaobing.framemvpproject.mylibrary.activity.composeview.BottomNavBar
import com.yaobing.framemvpproject.mylibrary.activity.composeview.ContentScreen
import com.yaobing.framemvpproject.mylibrary.activity.composeview.FirstScreen
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.BaseConstant
import com.yaobing.module_middleware.compose.theme.GreetingCardTheme

@Router(BaseConstant.BottomNavActivity)
class BottomNavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    BottomNavScreen()
                }
            }
        }
    }

    @Preview
    @Composable
    fun BottomNavScreen() {
        var selectedIndex by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // 上方内容区域 - 所有页面始终在组合树中，切换时不销毁
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // 所有页面始终存在，用带动画的 alpha 控制可见性
                // 这样切换时页面不会销毁重建，状态保持，同时有淡入淡出效果
                PageWrapper(isVisible = selectedIndex == 0) {
                    FirstScreen("首页")
                }
                PageWrapper(isVisible = selectedIndex == 1) {
                    ContentScreen("发现")
                }
                PageWrapper(isVisible = selectedIndex == 2) {
                    ContentScreen("消息")
                }
                PageWrapper(isVisible = selectedIndex == 3) {
                    ContentScreen("收藏")
                }
                PageWrapper(isVisible = selectedIndex == 4) {
                    ContentScreen("我的")
                }
            }

            HorizontalDivider()

            // 底部导航栏
            BottomNavBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    }
}

@Composable
private fun PageWrapper(
    isVisible: Boolean,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "page_alpha"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
    ) {
        content()
    }
}
