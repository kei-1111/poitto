package com.example.flush.ui.feature.post

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.model.User
import com.example.flush.domain.model.getExceededEmotionTypes
import com.example.flush.domain.model.isSavable
import com.example.flush.domain.use_case.CreateThrowingItemUseCase
import com.example.flush.domain.use_case.EmotionAnalysisUseCase
import com.example.flush.domain.use_case.GetCurrentUserUseCase
import com.example.flush.domain.use_case.UploadThrowingItemImageUseCase
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val emotionAnalysisUseCase: EmotionAnalysisUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createThrowingItemUseCase: CreateThrowingItemUseCase,
    private val uploadThrowingItemImageUseCase: UploadThrowingItemImageUseCase,
) : BaseViewModel<PostUiState, PostUiEvent, PostUiEffect>(PostUiState()) {

    private var currentUser: User? = null
    private var throwingItem: ThrowingItem = ThrowingItem(
        id = UUID.randomUUID().toString(),
    )

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                currentUser = user
                throwingItem = throwingItem.copy(user = user)
            }
        }
    }

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
            throwingItem = throwingItem.copy(message = message)
            analyzeEmotion(message)
            gemini(message)
            uploadImage()
        }
    }

    fun saveThrowingItem() {
        viewModelScope.launch {
            if (throwingItem.isSavable()) {
                val result = createThrowingItemUseCase(throwingItem)
                if (result.isSuccess) {
                    sendEffect(PostUiEffect.ShowToast("Saved"))
                } else {
                    sendEffect(PostUiEffect.ShowToast("Failed to save"))
                }
                sendEffect(PostUiEffect.NavigateToSearch)
            } else {
                sendEffect(PostUiEffect.ShowToast("Please fill in all fields"))
            }
        }
    }

    private fun uploadImage() {
        viewModelScope.launch {
            val imageUri = _uiState.value.imageUri
            imageUri?.let {
                val result = uploadThrowingItemImageUseCase(throwingItem.id, it)
                if (result.isSuccess) {
                    throwingItem = throwingItem.copy(imageUrl = result.getOrNull())
                } else {
                    Log.e(TAG, "uploadImage: Failed to upload image")
                }
            }
        }
    }

    private fun analyzeEmotion(text: String) {
        viewModelScope.launch {
            val result = emotionAnalysisUseCase.analyzeEmotion(text)
            throwingItem = throwingItem.copy(emotion = result)
            throwingItem = throwingItem.copy(labeledEmotion = result.getExceededEmotionTypes(Threshold))
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

    companion object {
        private const val TAG = "PostViewModel"

        private const val Threshold = 0.4f
    }
}
