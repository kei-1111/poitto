package com.example.flush.ui.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.use_case.GetCurrentUserUseCase
import com.example.flush.domain.use_case.GetThrowingItemUseCase
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getThrowingItemUseCase: GetThrowingItemUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : BaseViewModel<SearchUiState, SearchUiEvent, SearchUiEffect>(SearchUiState()) {

    init {
        fetchThrowingItems()
        fetchCurrentUser()
    }

    fun updateSelectedThrowingItem(throwingItemId: String?) {
        val selectedThrowingItem =
            _uiState.value.throwingItems.find { it.id == throwingItemId }
        updateUiState { it.copy(selectedThrowingItem = selectedThrowingItem) }
    }

    fun updateIsShowBottomSheet(isShowBottomSheet: Boolean) {
        updateUiState { it.copy(isShowBottomSheet = isShowBottomSheet) }
    }

    fun updateIsShowAnalyzeEmotion() {
        updateUiState { it.copy(isShowEmotionAnalyze = !it.isShowEmotionAnalyze) }
    }

    private fun fetchThrowingItems() {
        viewModelScope.launch {
            val result = getThrowingItemUseCase()
            if (result.isSuccess) {
                updateUiState { it.copy(throwingItems = result.getOrNull() ?: emptyList()) }
            } else {
                sendEffect(SearchUiEffect.ShowToast("Failed to fetch throwing items"))
            }
        }
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .collect { user ->
                    updateUiState { it.copy(currentUser = user) }
                }
        }
    }
}
