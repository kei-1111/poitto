package com.example.search

import com.example.core.base.UiState
import com.example.model.ThrowingItem
import com.example.model.User

data class SearchUiState(
    val currentUser: User = User(),
    val throwingItems: List<ThrowingItem> = emptyList(),
    val selectedThrowingItem: ThrowingItem? = null,
    val isLoading: Boolean = false,
    val isShowBottomSheet: Boolean = false,
    val isShowEmotionAnalyze: Boolean = false,
) : UiState
