package com.example.flush.data.model

import com.example.flush.domain.model.Emotion
import kotlinx.serialization.Serializable

@Serializable
data class AnalyzeEmotionRequest(
    val text: String,
)

@Serializable
data class AnalyzeEmotionResponse(
    val text: String,
    val response: Emotion,
)

@Serializable
data class GeminiRequest(
    val text: String,
)

@Serializable
data class GeminiResponse(
    val text: String,
    val response: String,
)
