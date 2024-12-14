package com.example.flush.ui.feature.search

import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.model.User
import com.example.flush.ui.base.UiState

data class SearchUiState(
    val currentUser: User = User(),
    val throwingItems: List<ThrowingItem> = emptyList(),
    val selectedThrowingItem: ThrowingItem? = null,
    val isLoading: Boolean = false,
    val isShowBottomSheet: Boolean = false,
) : UiState
