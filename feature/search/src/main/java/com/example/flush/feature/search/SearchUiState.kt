package com.example.flush.feature.search

import com.example.flush.core.base.UiState
import com.example.flush.core.model.ThrowingItem
import com.example.flush.core.model.User

data class SearchUiState(
    val currentUser: User = User(),
    val throwingItems: List<ThrowingItem> = emptyList(),
    val selectedThrowingItem: ThrowingItem? = null,
    val isLoading: Boolean = false,
    val isShowBottomSheet: Boolean = false,
    val isShowEmotionAnalyze: Boolean = false,
) : UiState
