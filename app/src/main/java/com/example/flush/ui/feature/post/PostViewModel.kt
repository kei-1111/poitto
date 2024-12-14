package com.example.flush.ui.feature.post

import android.content.Context
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
import com.example.flush.domain.use_case.UploadThrowingItemTextureBitmapUseCase
import com.example.flush.ui.base.BaseViewModel
import com.example.flush.ui.utils.BitmapUtils
import com.example.flush.ui.utils.BitmapUtils.uriToBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class PostViewModel @Inject constructor(
    private val emotionAnalysisUseCase: EmotionAnalysisUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createThrowingItemUseCase: CreateThrowingItemUseCase,
    private val uploadThrowingItemImageUseCase: UploadThrowingItemImageUseCase,
    private val uploadThrowingItemTextureBitmapUseCase: UploadThrowingItemTextureBitmapUseCase,
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

    fun toThrowPhase(
        context: Context,
        textColor: Int,
        backgroundColor: Int,
    ) {
        viewModelScope.launch {
            val imageUri = _uiState.value.imageUri
            val message = _uiState.value.message
            throwingItem = throwingItem.copy(message = message)
            createTextureBitmap(context, textColor, backgroundColor, imageUri, message)
            analyzeEmotion(message)
            gemini(message)
            uploadImage()
            uploadTextureBitmap()
            updateUiState { it.copy(phase = PostUiPhase.Throwing) }
        }
    }

    private fun createTextureBitmap(
        context: Context,
        textColor: Int,
        backgroundColor: Int,
        imageUri: Uri?,
        message: String,
    ) {
        viewModelScope.launch {
            val imageBitmap = uriToBitmap(context, imageUri)
            val textureBitmap = BitmapUtils.createTextureBitmap(
                text = message,
                imageBitmap = imageBitmap,
                textColor = textColor,
                backgroundColor = backgroundColor,
            )
            updateUiState { it.copy(textureBitmap = textureBitmap) }
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

    private fun uploadTextureBitmap() {
        viewModelScope.launch {
            val textureBitmap = _uiState.value.textureBitmap
            textureBitmap?.let {
                val result = uploadThrowingItemTextureBitmapUseCase(throwingItem.id, it)
                if (result.isSuccess && result.getOrNull() != null) {
                    throwingItem = throwingItem.copy(textureUrl = result.getOrNull()!!)
                } else {
                    Log.e(TAG, "uploadTextureBitmap: Failed to upload texture bitmap")
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
