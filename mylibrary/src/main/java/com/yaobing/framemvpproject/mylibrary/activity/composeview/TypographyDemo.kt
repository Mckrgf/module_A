package com.yaobing.framemvpproject.mylibrary.activity.composeview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TypographyDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Display 系列 - 超大标题
        Text("Display Large", style = MaterialTheme.typography.displayLarge)
        Text("Display Medium", style = MaterialTheme.typography.displayMedium)
        Text("Display Small", style = MaterialTheme.typography.displaySmall)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Headline 系列 - 大标题
        Text("Headline Large", style = MaterialTheme.typography.headlineLarge)
        Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
        Text("Headline Small", style = MaterialTheme.typography.headlineSmall)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Title 系列 - 标题
        Text("Title Large", style = MaterialTheme.typography.titleLarge)
        Text("Title Medium", style = MaterialTheme.typography.titleMedium)
        Text("Title Small", style = MaterialTheme.typography.titleSmall)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Body 系列 - 正文
        Text("Body Large - 正文内容示例", style = MaterialTheme.typography.bodyLarge)
        Text("Body Medium - 正文内容示例", style = MaterialTheme.typography.bodyMedium)
        Text("Body Small - 正文内容示例", style = MaterialTheme.typography.bodySmall)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Label 系列 - 标签
        Text("Label Large", style = MaterialTheme.typography.labelLarge)
        Text("Label Medium", style = MaterialTheme.typography.labelMedium)
        Text("Label Small", style = MaterialTheme.typography.labelSmall)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // 使用 copy() 微调样式
        Text(
            "自定义样式 - 基于 bodyLarge 调整",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}
