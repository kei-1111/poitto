package com.example.flush.ui.feature.search

import com.example.flush.ui.base.UiEvent

sealed interface SearchUiEvent : UiEvent {
    data object OnNavigateToUserSettingsClick : SearchUiEvent
    data object OnNavigateToPostClick : SearchUiEvent
}
