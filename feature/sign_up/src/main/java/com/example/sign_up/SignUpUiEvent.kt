package com.example.sign_up

import com.example.base.UiEvent

sealed interface SignUpUiEvent : UiEvent {
    data object OnNavigateToAuthSelectionClick : SignUpUiEvent
    data class OnEmailInputChange(val email: String) : SignUpUiEvent
    data class OnPasswordInputChange(val password: String) : SignUpUiEvent
    data object OnSubmitClick : SignUpUiEvent
    data object OnGoogleSignUpClick : SignUpUiEvent
}
