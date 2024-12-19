package com.example.sign_in

import com.example.base.UiState

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
