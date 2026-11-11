package com.yaobing.framemvpproject.mylibrary.activity.composeview

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView

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
                                val bitmap = BitmapFactory.decodeStream(
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
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}