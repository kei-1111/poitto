package com.example.flush.feature.user_settings

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flush.core.base.BaseViewModel
import com.example.flush.core.domain.GetCurrentUserUseCase
import com.example.flush.core.domain.SaveUserUseCase
import com.example.flush.core.domain.SignOutUseCase
import com.example.flush.core.model.User
import com.github.michaelbull.result.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : BaseViewModel<UserSettingsUiState, UserSettingsUiEvent, UserSettingsUiEffect>(
    UserSettingsUiState(),
) {
    private var initialUser: User? = null

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .collect { user ->
                    updateUiState { it.copy(name = user.name, imageUri = user.iconUrl) }
                    initialUser = user
                }
        }
    }

    fun updateName(name: String) {
        updateUiState { it.copy(name = name) }
    }

    fun updateImageUri(imageUri: Uri) {
        updateUiState { it.copy(imageUri = imageUri.toString()) }
    }

    fun saveUser() {
        viewModelScope.launch {
            try {
                updateUiState { it.copy(isLoading = true) }
                val name = uiState.value.name
                val imageUri = uiState.value.imageUri
                val result = initialUser?.let { user ->
                    saveUserUseCase(
                        user.copy(
                            name = name,
                            iconUrl = imageUri,
                        ),
                    )
                }
                updateUiState { it.copy(isLoading = false) }
                result?.fold(
                    { sendEffect(UserSettingsUiEffect.NavigateToSearch) },
                    { sendEffect(UserSettingsUiEffect.ShowToast(it)) },
                )
            } catch (e: Exception) {
                Log.e(TAG, "Save user failed", e)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                val result = signOutUseCase()
                result.fold(
                    { sendEffect(UserSettingsUiEffect.NavigateToAuthSelection) },
                    { sendEffect(UserSettingsUiEffect.ShowToast(it)) },
                )
            } catch (e: Exception) {
                Log.e(TAG, "Sign out failed", e)
            }
        }
    }

    companion object {
        private const val TAG = "UserSettingsViewModel"
    }
}
