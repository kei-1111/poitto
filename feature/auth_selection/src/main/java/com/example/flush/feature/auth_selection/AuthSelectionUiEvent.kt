package com.example.flush.feature.auth_selection

import com.example.flush.core.base.UiEvent

sealed interface AuthSelectionUiEvent : UiEvent {
    data object OnNavigateToSignUpClick : AuthSelectionUiEvent
    data object OnNavigateToSignInClick : AuthSelectionUiEvent
}
