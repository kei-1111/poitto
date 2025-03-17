package com.example.sign_in

import com.example.core.base.UiState

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
