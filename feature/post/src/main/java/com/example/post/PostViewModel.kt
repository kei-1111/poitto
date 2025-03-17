package com.example.post

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.domain.CreateThrowingItemUseCase
import com.example.domain.EmotionAnalysisUseCase
import com.example.domain.GetCurrentUserUseCase
import com.example.domain.UploadThrowingItemImageUseCase
import com.example.domain.UploadThrowingItemTextureBitmapUseCase
import com.example.model.ThrowingItem
import com.example.model.User
import com.example.model.getExceededEmotionTypes
import com.example.model.isSavable
import com.example.utils.BitmapUtils
import com.example.utils.BitmapUtils.uriToBitmap
import com.github.michaelbull.result.fold
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
            updateUiState { it.copy(animationState = PostUiAnimationState.Running) }
            delay(AnimationConfig.AnimationDuration.toLong())
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
                try {
                    updateUiState { it.copy(isLoading = false) }
                    createThrowingItemUseCase(throwingItem)
                    sendEffect(PostUiEffect.NavigateToSearch)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to save throwing item", e)
                }
            } else {
                updateUiState { it.copy(isLoading = true) }
                sendEffect(PostUiEffect.ShowToast("アップロードまで少々お待ちください"))
            }
        }
    }

    private fun uploadImage() {
        viewModelScope.launch {
            try {
                val imageUri = _uiState.value.imageUri
                imageUri?.let { uri ->
                    val result = uploadThrowingItemImageUseCase(throwingItem.id, uri)
                    result.fold(
                        { throwingItem = throwingItem.copy(imageUrl = result.value) },
                        { sendEffect(PostUiEffect.ShowToast(result.error)) },
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to upload image", e)
            }
        }
    }

    private fun uploadTextureBitmap() {
        viewModelScope.launch {
            try {
                val textureBitmap = _uiState.value.textureBitmap
                textureBitmap?.let {
                    val result = uploadThrowingItemTextureBitmapUseCase(throwingItem.id, it)
                    result.fold(
                        { throwingItem = throwingItem.copy(textureUrl = result.value) },
                        { sendEffect(PostUiEffect.ShowToast(result.error)) },
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to upload texture bitmap", e)
            }
        }
    }

    private fun analyzeEmotion(text: String) {
        viewModelScope.launch {
            val result = emotionAnalysisUseCase.analyzeEmotion(text)
            throwingItem = throwingItem.copy(emotion = result)
            throwingItem =
                throwingItem.copy(labeledEmotion = result.getExceededEmotionTypes(Threshold))
            updateUiState { it.copy(isLoading = false) }
        }
    }

    private fun gemini(text: String) {
        viewModelScope.launch {
            val result = emotionAnalysisUseCase.gemini(text)
            updateUiState { it.copy(responseMessage = result.trimEnd()) }
            updateUiState { it.copy(isLoading = false) }
        }
    }

    companion object {
        private const val TAG = "PostViewModel"

        private const val Threshold = 0.4f
    }
}
