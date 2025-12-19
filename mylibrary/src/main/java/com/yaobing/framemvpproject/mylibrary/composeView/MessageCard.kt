package com.yaobing.framemvpproject.mylibrary.composeView

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MessageCard(content : String,modifier : Modifier = Modifier) {
    Text(text = content,modifier = modifier)
}