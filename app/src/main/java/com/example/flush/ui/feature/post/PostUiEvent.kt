package com.example.flush.ui.feature.post

import com.example.flush.ui.base.UiEvent

sealed interface PostUiEvent : UiEvent {
    data object OnNavigateToSearchClick : PostUiEvent
    data class OnMessageInputChange(val message: String) : PostUiEvent
    data object OnImagePickerLaunchClick : PostUiEvent
    data object OnImageRemoveClick : PostUiEvent
    data object OnMessageSendClick : PostUiEvent
}
