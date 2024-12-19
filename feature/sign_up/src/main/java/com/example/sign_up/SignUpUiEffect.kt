package com.example.sign_up

import com.example.base.UiEffect

sealed interface SignUpUiEffect : UiEffect {
    data class ShowToast(val message: String) : SignUpUiEffect
    data object NavigateToSearch : SignUpUiEffect
}
