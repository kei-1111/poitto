package com.example.flush.domain.model

data class ThrowingItem(
    val id: String = "",
    val message: String = "",
    val imageUrl: String? = null,
    val textureUrl: String = "",
    val user: User = User(),
    val emotion: Emotion = Emotion(),
    val labeledEmotion: List<EmotionType> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
)

@Suppress("ComplexCondition")
fun ThrowingItem.isSavable(): Boolean {
    if (id.isBlank() || user == User() || emotion == Emotion() || textureUrl.isBlank()) return false

    return message.isNotBlank() || !imageUrl.isNullOrBlank()
}
