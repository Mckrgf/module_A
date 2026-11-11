package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            GreetingCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
        ) {
            // 上方内容区域
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp,0.dp,16.dp,0.dp),
                contentAlignment = Alignment.Center
            ) {
                when (selectedIndex) {
                    0 -> FirstScreen("首页")
                    1 -> ContentScreen("发现")
                    2 -> ContentScreen("消息")
                    3 -> ContentScreen("收藏")
                    4 -> ContentScreen("我的")
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
