package com.example.domain

import com.example.di.IoDispatcher
import com.example.repository.EmotionAnalysisRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmotionAnalysisUseCase @Inject constructor(
    private val emotionAnalysisRepository: EmotionAnalysisRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun analyzeEmotion(text: String) = withContext(ioDispatcher) {
        emotionAnalysisRepository.analyzeEmotion(text)
    }

    suspend fun gemini(text: String) = withContext(ioDispatcher) {
        emotionAnalysisRepository.gemini(text)
    }
}