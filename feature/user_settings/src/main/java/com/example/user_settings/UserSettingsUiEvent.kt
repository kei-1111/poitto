package com.example.user_settings

import com.example.core.base.UiEvent

sealed interface UserSettingsUiEvent : UiEvent {
    data object OnNavigateToSearchClick : UserSettingsUiEvent
    data class OnNameInputChange(val name: String) : UserSettingsUiEvent
    data object OnImagePickerLaunchClick : UserSettingsUiEvent
    data object OnSignOutClick : UserSettingsUiEvent
}
