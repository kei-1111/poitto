package com.example.flush.ui.feature.sign_up

import androidx.lifecycle.viewModelScope
import com.example.flush.domain.use_case.SignUpWithEmailUseCase
import com.example.flush.ui.base.BaseViewModel
import com.example.flush.ui.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
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
            val email = uiState.value.email
            val password = uiState.value.password
            val result = signUpWithEmailUseCase(email, password)
            updateUiState { it.copy(isLoading = false) }
            if (result.isSuccess) {
                sendEffect(SignUpUiEffect.NavigateToSearch)
            } else {
                sendEffect(SignUpUiEffect.ShowToast("Failed to register"))
            }
        }
    }

    fun startGoogleSignIn() {
    }
}
