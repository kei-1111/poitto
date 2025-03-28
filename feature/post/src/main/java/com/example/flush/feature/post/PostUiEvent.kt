package com.example.flush.feature.post

import com.example.flush.core.base.UiEvent

sealed interface PostUiEvent : UiEvent {
    data object OnNavigateToSearchClick : PostUiEvent
    data class OnMessageInputChange(val message: String) : PostUiEvent
    data object OnImagePickerLaunchClick : PostUiEvent
    data object OnImageRemoveClick : PostUiEvent
    data object OnMessageSendClick : PostUiEvent
    data object OnModelTapped : PostUiEvent
    data object OnResponseMessageViewerClick : PostUiEvent
}
