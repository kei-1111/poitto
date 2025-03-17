package com.example.flush.feature.user_settings

import com.example.flush.core.base.UiEffect

sealed interface UserSettingsUiEffect : UiEffect {
    data class ShowToast(val message: String) : UserSettingsUiEffect
    data object NavigateToAuthSelection : UserSettingsUiEffect
    data object NavigateToSearch : UserSettingsUiEffect
}
