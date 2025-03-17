package com.example.flush.feature.sign_up

import com.example.flush.core.base.UiState

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
