package com.example.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun AnimatedText(
    text: String,
    modifier: Modifier = Modifier,
    delayMillis: Long = 100L,
) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        visibleText = ""
        for (i in text.indices) {
            visibleText = text.substring(0, i + 1)
            delay(delayMillis) // 次の文字を表示するまで待つ
        }
    }

    BodyMediumText(
        text = visibleText,
        modifier = modifier,
    )
}
