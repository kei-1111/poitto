package com.example.flush

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _startDestination = MutableStateFlow<Screen?>(null)
    val startDestination: StateFlow<Screen?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val isUserLoggedIn = authRepository.getCurrentUser() != null
            _startDestination.update {
                if (isUserLoggedIn) {
                    Screen.Search
                } else {
                    Screen.AuthSelection
                }
            }
        }
    }
}
