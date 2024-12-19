package com.example.search

import com.example.base.UiEffect

sealed interface SearchUiEffect : UiEffect {
    data class ShowToast(val message: String) : SearchUiEffect
}
