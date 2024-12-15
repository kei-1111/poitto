package com.example.flush.ui.feature.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.flush.domain.model.Emotion
import com.example.flush.domain.model.EmotionType
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.model.forEach
import com.example.flush.ktx.toFormattedTime
import com.example.flush.ui.component.AsyncImage
import com.example.flush.ui.component.BodyMediumText
import com.example.flush.ui.component.LabelMediumText
import com.example.flush.ui.component.TitleLargeText
import com.example.flush.ui.component.TitleSmallText
import com.example.flush.ui.feature.search.SearchScreenDimensions.ParameterThickness
import com.example.flush.ui.feature.search.SearchScreenDimensions.PreviewImageHeight
import com.example.flush.ui.theme.dimensions.Alpha
import com.example.flush.ui.theme.dimensions.IconSize
import com.example.flush.ui.theme.dimensions.Paddings
import com.example.flush.ui.theme.dimensions.Weights
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenBottomSheet(
    uiState: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onEvent(SearchUiEvent.OnBottomSheetDismissRequest)
        },
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        ),
    ) {
        uiState.selectedThrowingItem?.let {
            SearchScreenBottomSheetContent(
                throwingItem = it,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenBottomSheetContent(
    throwingItem: ThrowingItem,
    modifier: Modifier = Modifier,
) {
    var isEx by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                isEx = !isEx
            }
            .padding(
                horizontal = Paddings.Large,
            )
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(Paddings.Small),
    ) {
        ThrowingItemHeader(
            throwingItem = throwingItem,
            modifier = Modifier.fillMaxWidth(),
        )
        BodyMediumText(
            text = throwingItem.message,
        )
        throwingItem.imageUrl?.let {
            AsyncImage(
                uri = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PreviewImageHeight),
            )
        }
        if (isEx) {
            Spacer(
                modifier = Modifier.height(Paddings.Small),
            )
            AnalyzedEmotionField(
                emotion = throwingItem.emotion,
            )
        }
    }
}

@Composable
private fun ThrowingItemHeader(
    throwingItem: ThrowingItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        throwingItem.user.iconUrl?.let {
            AsyncImage(
                uri = it,
                modifier = Modifier.size(IconSize.Medium),
                shape = CircleShape,
            )
        }
        Column(
            modifier = Modifier
                .weight(Weights.Medium)
                .padding(
                    horizontal = Paddings.Small,
                ),
        ) {
            BodyMediumText(
                text = throwingItem.user.name,
            )
            LabelMediumText(
                text = throwingItem.timestamp.toFormattedTime(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = Alpha.Medium),
            )
        }
        LabeledEmotion(
            labeledEmotion = throwingItem.labeledEmotion.toPersistentList(),
        )
    }
}

@Composable
private fun LabeledEmotion(
    labeledEmotion: ImmutableList<EmotionType>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Paddings.Small),
    ) {
        labeledEmotion.forEach { emotion ->
            val emoji = when (emotion) {
                EmotionType.Joy -> "😄"
                EmotionType.Sadness -> "😢"
                EmotionType.Surprise -> "😲"
                EmotionType.Anger -> "😡"
                EmotionType.Fear -> "😨"
                EmotionType.Disgust -> "🤢"
                EmotionType.Trust -> "🤝"
                EmotionType.Anticipation -> "😏"
            }
            TitleLargeText(
                text = emoji,
            )
        }
    }
}

@Composable
private fun AnalyzedEmotionField(
    emotion: Emotion,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraSmall),
    ) {
        TitleSmallText(
            text = "感情解析",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(Paddings.ExtraSmall),
        ) {
            emotion.forEach { property, value ->
                AnalyzedEmotion(
                    emotionType = EmotionType.valueOf(property.capitalize(Locale.ROOT)),
                    value = value,
                )
            }
        }
    }
}

@Composable
private fun AnalyzedEmotion(
    emotionType: EmotionType,
    value: Float,
    modifier: Modifier = Modifier,
) {
    val label = returnEmotionLabel(emotionType)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Paddings.Small),
    ) {
        BodyMediumText(
            text = label,
        )
        Parameter(
            value = value,
            modifier = Modifier.weight(Weights.Medium),
        )
    }
}

@Composable
private fun Parameter(
    value: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape),
            thickness = ParameterThickness,
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(value)
                .clip(CircleShape),
            thickness = ParameterThickness,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

private fun returnEmotionLabel(
    emotionType: EmotionType,
): String {
    return when (emotionType) {
        EmotionType.Joy -> "😄 期待"
        EmotionType.Sadness -> "😢 悲し"
        EmotionType.Surprise -> "😲 驚き"
        EmotionType.Anger -> "😡 怒り"
        EmotionType.Fear -> "😨 恐れ"
        EmotionType.Disgust -> "🤢 嫌悪"
        EmotionType.Trust -> "🤝 信頼"
        EmotionType.Anticipation -> "😏 期待"
    }
}
