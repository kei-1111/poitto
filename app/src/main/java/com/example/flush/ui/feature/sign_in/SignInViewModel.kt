package com.example.flush.ui.feature.sign_in

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.use_case.RequestGoogleOneTapAuthUseCase
import com.example.flush.domain.use_case.SignInWithEmailUseCase
import com.example.flush.domain.use_case.SignInWithGoogleUseCase
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val requestGoogleOneTapAuthUseCase: RequestGoogleOneTapAuthUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : BaseViewModel<SignInUiState, SignInUiEvent, SignInUiEffect>(SignInUiState()) {

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
            val result = signInWithEmailUseCase(email, password)
            updateUiState { it.copy(isLoading = false) }
            if (result.isSuccess) {
                sendEffect(SignInUiEffect.NavigateToSearch)
            } else {
                sendEffect(SignInUiEffect.ShowToast("Failed to register"))
            }
        }
    }

    fun startGoogleSignIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true) }
            val intentSenderRequest = requestGoogleOneTapAuthUseCase()
            launcher.launch(intentSenderRequest)
        }
    }

    fun handleSignInResult(launcherResult: Intent?) {
        viewModelScope.launch {
            if (launcherResult != null) {
                val result = signInWithGoogleUseCase(launcherResult)
                updateUiState { it.copy(isLoading = false) }
                if (result.isSuccess) {
                    sendEffect(SignInUiEffect.NavigateToSearch)
                } else {
                    sendEffect(SignInUiEffect.ShowToast("Failed to sign in"))
                }
            } else {
                sendEffect(SignInUiEffect.ShowToast("Failed to sign in"))
            }
        }
    }
}
