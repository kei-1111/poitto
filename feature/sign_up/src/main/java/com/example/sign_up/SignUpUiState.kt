package com.example.sign_up

import com.example.core.base.UiState

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
