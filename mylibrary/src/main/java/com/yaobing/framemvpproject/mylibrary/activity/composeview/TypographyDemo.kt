package com.yaobing.framemvpproject.mylibrary.activity.composeview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
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


@Preview
@Composable
fun LayoutDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ========== 1. Box 叠加对齐 ==========
        Text("1. Box 叠加对齐", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray)
        ) {
            // 左上角
            Text(
                "左上",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(Color.Yellow)
            )
            // 居中
            Text(
                "居中",
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Green)
            )
            // 右下角
            Text(
                "右下",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .background(Color.Red)
            )
        }

        HorizontalDivider()

        // ========== 2. Row/Column + weight 比例布局 ==========
        Text("2. Row + weight 比例布局", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 固定宽度
            Text(
                "固定100dp",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxSize()
                    .background(Color.Cyan),
                style = MaterialTheme.typography.bodySmall
            )
            // 占剩余空间的 1/3
            Text(
                "weight(1)",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color.Magenta),
                style = MaterialTheme.typography.bodySmall
            )
            // 占剩余空间的 2/3
            Text(
                "weight(2)",
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
                    .background(Color.Yellow),
                style = MaterialTheme.typography.bodySmall
            )
        }

        HorizontalDivider()

        // ========== 3. Column 排列控制 ==========
        Text("3. Column 排列控制", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 靠左
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .background(Color(0xFFE0E0E0)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Top", style = MaterialTheme.typography.labelSmall)
                Text("内容", style = MaterialTheme.typography.bodySmall)
            }
            // 居中
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .background(Color(0xFFD0D0D0)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Center", style = MaterialTheme.typography.labelSmall)
                Text("内容", style = MaterialTheme.typography.bodySmall)
            }
            // 靠下
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .background(Color(0xFFC0C0C0)),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bottom", style = MaterialTheme.typography.labelSmall)
                Text("内容", style = MaterialTheme.typography.bodySmall)
            }
        }

        HorizontalDivider()

        // ========== 4. SpaceBetween 两端对齐 ==========
        Text("4. Arrangement.SpaceBetween", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFFE8E8E8))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("左侧文字", style = MaterialTheme.typography.bodyMedium)
            Text("中间", style = MaterialTheme.typography.bodyMedium)
            Text("右侧文字", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
