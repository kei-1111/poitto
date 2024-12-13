package com.example.flush.ui.feature.user_settings

import android.net.Uri
import com.example.flush.ui.base.UiState

data class UserSettingsUiState(
    val name: String = "",
    val imageUri: Uri? = null,
) : UiState
