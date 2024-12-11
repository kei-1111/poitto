package com.example.flush.ui.feature.sign_up

import androidx.lifecycle.viewModelScope
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : BaseViewModel<SignUpUiState, SignUpUiEvent, SignUpUiEffect>(SignUpUiState()) {

    fun updateEmail(email: String) {
        updateUiState { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        updateUiState { it.copy(password = password) }
    }

    fun submitRegister() {
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true) }
        }
    }

    fun startGoogleSignIn() {
    }
}
