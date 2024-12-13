package com.example.flush.ui.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun Container(
    shape: Shape = MaterialTheme.shapes.small,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
){
    Surface(
        modifier = modifier
            .animateContentSize(),
        color = containerColor,
        shape = shape,
    ) {
        content()
    }
}