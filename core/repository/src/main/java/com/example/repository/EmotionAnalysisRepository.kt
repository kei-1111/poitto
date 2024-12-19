package com.example.repository

import com.example.model.Emotion


interface EmotionAnalysisRepository {
    suspend fun analyzeEmotion(text: String): Emotion

    suspend fun gemini(text: String): String
}
