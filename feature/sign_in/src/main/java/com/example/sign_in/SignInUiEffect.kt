package com.example.sign_in

import com.example.core.base.UiEffect

sealed interface SignInUiEffect : UiEffect {
    data class ShowToast(val message: String) : SignInUiEffect
    data object NavigateToSearch : SignInUiEffect
}
