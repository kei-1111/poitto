package com.example.flush.data.repository

import com.example.flush.data.api.EmotionAnalysisApi
import com.example.flush.core.model.AnalyzeEmotionRequest
import com.example.flush.core.model.Emotion
import com.example.flush.core.model.GeminiRequest
import com.example.flush.core.repository.EmotionAnalysisRepository
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
