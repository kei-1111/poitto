package com.example.model

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data class Emotion(
    val joy: Float = 0f,
    val sadness: Float = 0f,
    val surprise: Float = 0f,
    val anger: Float = 0f,
    val fear: Float = 0f,
    val disgust: Float = 0f,
    val trust: Float = 0f,
    val anticipation: Float = 0f,
)

enum class EmotionType {
    Joy, Sadness, Surprise, Anger,
    Fear, Disgust, Trust, Anticipation
}

fun Emotion.getExceededEmotionTypes(threshold: Float): List<EmotionType> {
    return EmotionType.entries.filter { emotionType ->
        when (emotionType) {
            EmotionType.Joy -> joy
            EmotionType.Sadness -> sadness
            EmotionType.Surprise -> surprise
            EmotionType.Anger -> anger
            EmotionType.Fear -> fear
            EmotionType.Disgust -> disgust
            EmotionType.Trust -> trust
            EmotionType.Anticipation -> anticipation
        } > threshold
    }
}

fun Emotion.toMap(): Map<String, Float> {
    return mapOf(
        "joy" to joy,
        "sadness" to sadness,
        "surprise" to surprise,
        "anger" to anger,
        "fear" to fear,
        "disgust" to disgust,
        "trust" to trust,
        "anticipation" to anticipation,
    )
}

@Composable
fun Emotion.forEach(action: @Composable (property: String, value: Float) -> Unit) {
    toMap().forEach { (property, value) ->
        action(property, value)
    }
}
