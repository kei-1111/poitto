package com.example.flush.ui.feature.sign_in

import com.example.flush.ui.base.UiEffect

sealed interface SignInUiEffect : UiEffect {
    data class ShowToast(val message: String) : SignInUiEffect
    data object NavigateToSearch : SignInUiEffect
}
