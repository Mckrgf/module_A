package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
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

    @Composable
    fun BottomNavScreen() {
        var selectedIndex by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // 上方内容区域
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (selectedIndex) {
                    0 -> ContentScreen("首页")
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

    @Composable
    fun ContentScreen(title: String) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "这是 $title 页面",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }

    @Composable
    fun BottomNavBar(
        selectedIndex: Int,
        onItemSelected: (Int) -> Unit
    ) {
        val items = listOf("首页", "发现", "消息", "收藏", "我的")
        val lottieFiles = listOf(
            "home-main.json",
            "home-store.json",
            "home-lovecar.json",
            "home-mall.json",
            "home-mine.json"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, title ->
                val isSelected = selectedIndex == index
                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onItemSelected(index) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 图标区域：选中时显示 Lottie 动画，非选中时显示静态图片
                    if (isSelected) {
                        AndroidView(
                            factory = { context ->
                                LottieAnimationView(context).apply {
                                    setAnimation(lottieFiles[index])
                                    repeatCount = 0
                                    playAnimation()
                                }
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .height(32.dp)
                                .fillMaxWidth(0.3f)
                        )
                    } else {
                        AndroidView(
                            factory = { context ->
                                ImageView(context).apply {
                                    // 从 assets 加载 test.png
                                    val bitmap = android.graphics.BitmapFactory.decodeStream(
                                        context.assets.open("test.png")
                                    )
                                    setImageBitmap(bitmap)
                                }
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .height(32.dp)
                                .fillMaxWidth(0.3f)
                        )
                    }

                    // 文字标签
                    Text(
                        text = title,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}
