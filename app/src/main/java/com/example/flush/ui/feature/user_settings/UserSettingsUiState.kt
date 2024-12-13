package com.example.flush.ui.feature.user_settings

import com.example.flush.ui.base.UiState

data class UserSettingsUiState(
    val name: String = "",
    val imageUri: String? = null,
    val isLoading: Boolean = false,
) : UiState
