package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.EmotionAnalysisRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EmotionAnalysisUseCase @Inject constructor(
    private val emotionAnalysisRepository: EmotionAnalysisRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun analyzeEmotion(text: String) = emotionAnalysisRepository.analyzeEmotion(text)

    suspend fun gemini(text: String) = emotionAnalysisRepository.gemini(text)
}