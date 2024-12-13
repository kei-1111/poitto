package com.example.flush.domain.use_case

import com.example.flush.domain.repository.EmotionAnalysisRepository
import javax.inject.Inject

class EmotionAnalysisUseCase @Inject constructor(
    private val emotionAnalysisRepository: EmotionAnalysisRepository,
) {
    suspend fun analyzeEmotion(text: String) = emotionAnalysisRepository.analyzeEmotion(text)

    suspend fun gemini(text: String) = emotionAnalysisRepository.gemini(text)
}
