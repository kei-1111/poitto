package com.example.flush.ui.feature.auth_selection

import com.example.flush.ui.base.UiEvent

sealed interface AuthSelectionUiEvent : UiEvent {
    data object OnNavigateToSignUpClick : AuthSelectionUiEvent
    data object OnNavigateToSignInClick : AuthSelectionUiEvent
}
