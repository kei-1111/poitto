package com.example.flush.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flush.R
import com.example.flush.ui.theme.dimensions.Alpha

private val LottieSize = 125.dp
private val ShadowEleveation = 8.dp

@Composable
fun Loading(
    modifier: Modifier = Modifier,
) {
    CenteredContainer(
        modifier = modifier
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = Alpha.Medium)),
    ) {
        Surface(
            modifier = Modifier.size(LottieSize),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = ShadowEleveation,

        ) {
            LottieAnimation(
                resId = if (isSystemInDarkTheme()) {
                    R.raw.dark_theme_loading_animation
                } else {
                    R.raw.light_theme_loading_animation
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
