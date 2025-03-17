package com.example.flush.data.api

import com.example.flush.core.model.AnalyzeEmotionRequest
import com.example.flush.core.model.AnalyzeEmotionResponse
import com.example.flush.core.model.GeminiRequest
import com.example.flush.core.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EmotionAnalysisApi {
    @POST("/analyze_emotion")
    suspend fun analyzeEmotion(@Body request: AnalyzeEmotionRequest): AnalyzeEmotionResponse

    @POST("/gemini")
    suspend fun gemini(@Body request: GeminiRequest): GeminiResponse
}
