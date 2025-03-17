package com.example.flush.feature.post

import com.example.flush.core.base.UiEffect

sealed interface PostUiEffect : UiEffect {
    data class ShowToast(val message: String) : PostUiEffect
    data object NavigateToSearch : PostUiEffect
}
