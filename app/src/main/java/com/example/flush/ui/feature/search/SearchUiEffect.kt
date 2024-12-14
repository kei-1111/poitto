package com.example.flush.ui.feature.search

import com.example.flush.ui.base.UiEffect

sealed interface SearchUiEffect : UiEffect {
    data class ShowToast(val message: String) : SearchUiEffect
}
