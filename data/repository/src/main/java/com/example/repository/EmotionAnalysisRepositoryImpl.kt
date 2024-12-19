package com.example.repository

import com.example.data.api.EmotionAnalysisApi
import com.example.model.AnalyzeEmotionRequest
import com.example.model.Emotion
import com.example.model.GeminiRequest
import javax.inject.Inject

class EmotionAnalysisRepositoryImpl @Inject constructor(
    private val emotionAnalysisApi: EmotionAnalysisApi,
) : EmotionAnalysisRepository {

    override suspend fun analyzeEmotion(text: String): Emotion {
        val request = AnalyzeEmotionRequest(
            text = text,
        )
        return emotionAnalysisApi.analyzeEmotion(request).response
    }

    override suspend fun gemini(text: String): String {
        val request = GeminiRequest(
            text = text,
        )
        return emotionAnalysisApi.gemini(request).response
    }
}
