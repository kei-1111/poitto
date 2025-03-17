package com.example.flush.feature.sign_in

import com.example.flush.core.base.UiState

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
