package com.example.flush.ui.feature.post

import android.net.Uri
import com.example.flush.ui.base.UiState

data class PostUiState(
    val message: String = "",
    val imageUri: Uri? = null,
    val phase: PostUiPhase = PostUiPhase.Writing
) : UiState

enum class PostUiPhase {
    Writing,
    Throwing,
}
