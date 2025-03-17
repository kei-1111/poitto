package com.example.flush.feature.user_settings

import com.example.flush.core.base.UiState

data class UserSettingsUiState(
    val name: String = "",
    val imageUri: String? = null,
    val isLoading: Boolean = false,
) : UiState
