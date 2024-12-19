package com.example.sign_in

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.domain.RequestGoogleOneTapAuthUseCase
import com.example.domain.SignInWithEmailUseCase
import com.example.domain.SignInWithGoogleUseCase
import com.github.michaelbull.result.fold
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
            try {
                updateUiState { it.copy(isLoading = true) }
                val email = uiState.value.email
                val password = uiState.value.password
                val result = signInWithEmailUseCase(email, password)
                updateUiState { it.copy(isLoading = false) }
                result.fold(
                    { sendEffect(SignInUiEffect.NavigateToSearch) },
                    { sendEffect(SignInUiEffect.ShowToast(it)) },
                )
            } catch (e: Exception) {
                Log.e(TAG, "Sign in failed", e)
            }
        }
    }

    fun startGoogleSignIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            try {
                updateUiState { it.copy(isLoading = true) }
                val result = requestGoogleOneTapAuthUseCase()
                result.fold(
                    { launcher.launch(it) },
                    { sendEffect(SignInUiEffect.ShowToast(it)) },
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed to start Google sign in", e)
            }
        }
    }

    fun handleSignInResult(launcherResult: Intent?) {
        viewModelScope.launch {
            if (launcherResult != null) {
                try {
                    val result = signInWithGoogleUseCase(launcherResult)
                    updateUiState { it.copy(isLoading = false) }
                    result.fold(
                        { sendEffect(SignInUiEffect.NavigateToSearch) },
                        { sendEffect(SignInUiEffect.ShowToast(it)) },
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error signing in with Google One Tap", e)
                }
            } else {
                sendEffect(SignInUiEffect.ShowToast("サインインに失敗しました"))
            }
        }
    }

    companion object {
        private const val TAG = "SignInViewModel"
    }
}
