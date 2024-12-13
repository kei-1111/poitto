package com.example.flush.ui.feature.user_settings

import com.example.flush.ui.base.UiEffect
import com.example.flush.ui.feature.sign_up.SignUpUiEffect

interface UserSettingsUiEffect : UiEffect {
    data class ShowToast(val message: String) : UserSettingsUiEffect
    data object NavigateToAuthSelection : UserSettingsUiEffect
}