package com.yaobing.framemvpproject.mylibrary.activity

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.BaseConstant
import androidx.compose.material3.Typography // 新增正确的Material3 Typography导入


@Router(BaseConstant.ComposeDemoActivity)
class ComposeDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            ContentView()
        }

    }
    @Preview(
        name = "暗黑主题",
        showBackground = true,
        uiMode = UI_MODE_NIGHT_YES
    )
    @Composable
    private fun ContentView() {
        GreetingCardTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(Modifier
                    .systemBarsPadding()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)) {
                    Greeting("Android")
                    Greeting("Android2")
                    Greeting("Android3")
                    Greeting("Android4")
                    Row(){
                        Greeting("Android5", Modifier.padding(end = 10.dp))
                        Greeting("Android6")
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        Greeting("Meghan")
    }
}

@Composable
fun GreetingCardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 自定义符合Material3要求的Typography，你可以在这里自定义各类文本样式
    val AppTypography = Typography(
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        // 其他文本样式可以根据需要自行配置，不配置会使用Material3默认值
    )
    val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}