package com.example.flush.feature.post

import android.graphics.Bitmap
import android.net.Uri
import com.example.flush.core.base.UiState

data class PostUiState(
    val isLoading: Boolean = false,
    val message: String = "",
    val imageUri: Uri? = null,
    val textureBitmap: Bitmap? = null,
    val phase: PostUiPhase = PostUiPhase.Writing,
    val animationState: PostUiAnimationState = PostUiAnimationState.Idle,
    val responseMessage: String = "",
) : UiState

enum class PostUiPhase {
    Writing,
    Throwing,
}

enum class PostUiAnimationState {
    Idle,
    Running,
    Completed,
}
