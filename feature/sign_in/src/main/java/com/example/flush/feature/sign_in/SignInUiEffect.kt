package com.example.flush.feature.sign_in

import com.example.flush.core.base.UiEffect

sealed interface SignInUiEffect : UiEffect {
    data class ShowToast(val message: String) : SignInUiEffect
    data object NavigateToSearch : SignInUiEffect
}
