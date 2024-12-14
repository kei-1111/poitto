package com.example.flush.ui.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.use_case.GetThrowingItemUseCase
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getThrowingItemUseCase: GetThrowingItemUseCase,
) : BaseViewModel<SearchUiState, SearchUiEvent, SearchUiEffect>(SearchUiState()) {

    var throwingItems by mutableStateOf(emptyList<ThrowingItem>())
        private set

    init {
        fetchThrowingItems()
    }

    private fun fetchThrowingItems() {
        viewModelScope.launch {
            val result = getThrowingItemUseCase()
            if (result.isSuccess) {
                throwingItems = result.getOrNull() ?: emptyList()
            } else {
                sendEffect(SearchUiEffect.ShowToast("Failed to fetch throwing items"))
            }
        }
    }
}
