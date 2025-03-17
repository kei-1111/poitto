package com.example.search

import com.example.core.base.UiEffect

sealed interface SearchUiEffect : UiEffect {
    data class ShowToast(val message: String) : SearchUiEffect
}
