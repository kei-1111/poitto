package com.example.flush.ui.feature.post

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("ModifierNotUsedAtRoot")
@Composable
fun PostScreenContent(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = uiState.phase == PostUiPhase.Writing,
        enter = fadeIn(),
    ) {
        PostScreenWritingPhaseContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize(),
        )
    }

    AnimatedVisibility(
        visible = uiState.phase == PostUiPhase.Throwing,
        enter = fadeIn(),
    ) {
        PostScreenThrowingPhaseContent(
            uiState = uiState,
            onEvent = onEvent,
        )
    }
}
