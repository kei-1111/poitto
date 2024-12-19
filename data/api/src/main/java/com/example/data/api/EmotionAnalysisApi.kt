package com.example.data.api

import com.example.model.AnalyzeEmotionRequest
import com.example.model.AnalyzeEmotionResponse
import com.example.model.GeminiRequest
import com.example.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EmotionAnalysisApi {
    @POST("/analyze_emotion")
    suspend fun analyzeEmotion(@Body request: AnalyzeEmotionRequest): AnalyzeEmotionResponse

    @POST("/gemini")
    suspend fun gemini(@Body request: GeminiRequest): GeminiResponse
}
