package com.example.post

import com.example.base.UiEffect

sealed interface PostUiEffect : UiEffect {
    data class ShowToast(val message: String) : PostUiEffect
    data object NavigateToSearch : PostUiEffect
}
