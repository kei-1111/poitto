package com.example.flush.ui.feature.sign_in

import com.example.flush.ui.base.UiEvent

sealed interface SignInUiEvent : UiEvent {
    data object OnNavigateToAuthSelectionClick : SignInUiEvent
    data class OnEmailInputChange(val email: String) : SignInUiEvent
    data class OnPasswordInputChange(val password: String) : SignInUiEvent
    data object OnSubmitClick : SignInUiEvent
    data object OnGoogleSignUpClick : SignInUiEvent
}
