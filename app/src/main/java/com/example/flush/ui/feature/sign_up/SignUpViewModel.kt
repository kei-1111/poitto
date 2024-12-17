package com.example.flush.ui.feature.sign_up

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.use_case.RequestGoogleOneTapAuthUseCase
import com.example.flush.domain.use_case.SignInWithGoogleUseCase
import com.example.flush.domain.use_case.SignUpWithEmailUseCase
import com.example.flush.ui.base.BaseViewModel
import com.github.michaelbull.result.mapBoth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val requestGoogleOneTapAuthUseCase: RequestGoogleOneTapAuthUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
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
            result.mapBoth(
                { sendEffect(SignUpUiEffect.NavigateToSearch) },
                { sendEffect(SignUpUiEffect.ShowToast(it)) },
            )
        }
    }

    fun startGoogleSignIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true) }
            val result = requestGoogleOneTapAuthUseCase()
            result.mapBoth(
                { launcher.launch(it) },
                { sendEffect(SignUpUiEffect.ShowToast(it)) },
            )
        }
    }

    fun handleSignInResult(launcherResult: Intent?) {
        viewModelScope.launch {
            if (launcherResult != null) {
                val result = signInWithGoogleUseCase(launcherResult)
                updateUiState { it.copy(isLoading = false) }
                result.mapBoth(
                    { sendEffect(SignUpUiEffect.NavigateToSearch) },
                    { sendEffect(SignUpUiEffect.ShowToast(it)) },
                )
            } else {
                sendEffect(SignUpUiEffect.ShowToast("Failed to sign in"))
            }
        }
    }
}
