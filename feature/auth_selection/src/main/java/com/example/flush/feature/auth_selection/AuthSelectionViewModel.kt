package com.example.flush.feature.auth_selection

import com.example.flush.core.base.BaseViewModel

class AuthSelectionViewModel :
    BaseViewModel<AuthSelectionUiState, AuthSelectionUiEvent, AuthSelectionUiEffect>(
        AuthSelectionUiState(),
    )
