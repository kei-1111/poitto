package com.example.flush.domain.repository

import com.example.flush.domain.model.Emotion

interface EmotionAnalysisRepository {
    suspend fun analyzeEmotion(text: String): Emotion

    suspend fun gemini(text: String): String
}
