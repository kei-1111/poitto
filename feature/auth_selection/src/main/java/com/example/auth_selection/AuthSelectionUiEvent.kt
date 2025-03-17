package com.example.auth_selection

import com.example.core.base.UiEvent

sealed interface AuthSelectionUiEvent : UiEvent {
    data object OnNavigateToSignUpClick : AuthSelectionUiEvent
    data object OnNavigateToSignInClick : AuthSelectionUiEvent
}
