package com.example.flush.ui.feature.search

import com.example.flush.ui.base.UiEvent

sealed interface SearchUiEvent : UiEvent {
    data object OnNavigateToUserSettingsClick : SearchUiEvent
    data object OnNavigateToPostClick : SearchUiEvent
    data class OnModelTap(val throwingItemId: String?) : SearchUiEvent
    data object OnBottomSheetDismissRequest : SearchUiEvent
    data object OnBottomSheetClick : SearchUiEvent
}
