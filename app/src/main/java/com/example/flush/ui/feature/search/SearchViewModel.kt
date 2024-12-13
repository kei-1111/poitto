package com.example.flush.ui.feature.search

import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : BaseViewModel<SearchUiState, SearchUiEvent, SearchUiEffect>(SearchUiState()){

}