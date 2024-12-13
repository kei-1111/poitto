package com.example.flush.ui.feature.user_settings

import com.example.flush.ui.base.UiEffect

interface UserSettingsUiEffect : UiEffect {
    data class ShowToast(val message: String) : UserSettingsUiEffect
    data object NavigateToAuthSelection : UserSettingsUiEffect
    data object NavigateToSearch : UserSettingsUiEffect
}
