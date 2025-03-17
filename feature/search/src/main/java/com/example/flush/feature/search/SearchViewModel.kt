package com.example.flush.feature.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flush.core.base.BaseViewModel
import com.example.flush.core.domain.GetCurrentUserUseCase
import com.example.flush.core.domain.GetThrowingItemUseCase
import com.github.michaelbull.result.fold
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
            try {
                val result = getThrowingItemUseCase()
                result.fold(
                    { updateUiState { it.copy(throwingItems = result.value) } },
                    { sendEffect(SearchUiEffect.ShowToast(result.error)) },
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch throwing items", e)
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

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
