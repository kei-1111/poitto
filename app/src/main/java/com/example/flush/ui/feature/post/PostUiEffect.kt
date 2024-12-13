package com.example.flush.ui.feature.post

import com.example.flush.ui.base.UiEffect

sealed interface PostUiEffect : UiEffect {
    data class ShowToast(val message: String) : PostUiEffect
}