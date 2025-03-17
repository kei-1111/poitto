package com.example.flush.feature.sign_up

import com.example.flush.core.base.UiEffect

sealed interface SignUpUiEffect : UiEffect {
    data class ShowToast(val message: String) : SignUpUiEffect
    data object NavigateToSearch : SignUpUiEffect
}
