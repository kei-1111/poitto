package com.example.flush.ui.feature.sign_in

import com.example.flush.ui.base.UiState

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
