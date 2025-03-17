package com.example.user_settings

import com.example.core.base.UiState

data class UserSettingsUiState(
    val name: String = "",
    val imageUri: String? = null,
    val isLoading: Boolean = false,
) : UiState
