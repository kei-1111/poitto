package com.example.flush.core.repository

import com.example.flush.core.model.Emotion


interface EmotionAnalysisRepository {
    suspend fun analyzeEmotion(text: String): Emotion

    suspend fun gemini(text: String): String
}
