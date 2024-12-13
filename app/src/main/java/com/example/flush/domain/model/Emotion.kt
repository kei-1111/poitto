package com.example.flush.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Emotion(
    val joy: Float = 0f,
    val sadness: Float = 0f,
    val surprise: Float = 0f,
    val anger: Float = 0f,
    val fear: Float = 0f,
    val disgust: Float = 0f,
    val trust: Float = 0f,
    val anticipation: Float = 0f,
)
