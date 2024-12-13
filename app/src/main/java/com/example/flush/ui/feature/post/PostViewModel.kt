package com.example.flush.ui.feature.post

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.use_case.EmotionAnalysisUseCase
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val emotionAnalysisUseCase: EmotionAnalysisUseCase,
) : BaseViewModel<PostUiState, PostUiEvent, PostUiEffect>(PostUiState()) {

    fun updateMessage(message: String) {
        updateUiState { it.copy(message = message) }
    }

    fun updateImageUri(imageUri: Uri?) {
        updateUiState { it.copy(imageUri = imageUri) }
    }

    fun startAnimation() {
        viewModelScope.launch {
            Log.d("Animation", "Animation started")
            updateUiState { it.copy(animationState = PostUiAnimationState.Running) }
            delay(AnimationConfig.AnimationDuration.toLong())
            Log.d("Animation", "Animation completed")
            updateUiState { it.copy(animationState = PostUiAnimationState.Completed) }
        }
    }

    fun toThrowPhase() {
        viewModelScope.launch {
            updateUiState { it.copy(phase = PostUiPhase.Throwing) }
            val message = _uiState.value.message
            analyzeEmotion(message)
            gemini(message)
        }
    }

    private fun analyzeEmotion(text: String) {
        viewModelScope.launch {
            val result = emotionAnalysisUseCase.analyzeEmotion(text)
            Log.d("PostViewModel", "analyzeEmotion: $result")
        }
    }

    private fun gemini(text: String) {
        viewModelScope.launch {
            val result = emotionAnalysisUseCase.gemini(text)
            Log.d("PostViewModel", "gemini: $result")
            updateUiState { it.copy(responseMessage = result.trimEnd()) }
        }
    }
}
