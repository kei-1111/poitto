package com.example.flush.feature.search

import com.example.flush.core.base.UiEffect

sealed interface SearchUiEffect : UiEffect {
    data class ShowToast(val message: String) : SearchUiEffect
}
