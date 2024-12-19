package com.example.sign_up

import com.example.base.UiState

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
