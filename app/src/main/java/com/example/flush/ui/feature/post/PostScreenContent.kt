package com.example.flush.ui.feature.post

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction.Companion.Send
import com.example.flush.ui.compose.AsyncImage
import com.example.flush.ui.compose.Container
import com.example.flush.ui.compose.Icon
import com.example.flush.ui.compose.IconButton

import com.example.flush.ui.theme.dimensions.Alpha
import com.example.flush.ui.theme.dimensions.Paddings
import com.example.flush.ui.theme.dimensions.Weights

@Composable
fun PostScreenContent(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        when (uiState.phase) {
            PostUiPhase.Writing -> PostScreenWritingPhaseContent(
                uiState = uiState,
                onEvent = onEvent,
                modifier = modifier
            )
            PostUiPhase.Throwing -> {}
        }
    }
}

@Composable
private fun PostScreenWritingPhaseContent(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .padding(Paddings.Large)
    ) {
        WriteField(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WriteField(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Container(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(Paddings.Medium),
            verticalArrangement = Arrangement.spacedBy(Paddings.Medium)
        ) {
            BasicTextField(
                value = uiState.message,
                onValueChange = { onEvent(PostUiEvent.OnMessageInputChange(it)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
            )
            uiState.imageUri?.let {
                ImagePreview(
                    imageUri = it,
                    deleteImage = { onEvent(PostUiEvent.OnImageRemoveClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(Weights.Medium, fill = false)
                )
            }
            ActionIcons(
                uiState = uiState,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ImagePreview(
    imageUri: Uri,
    deleteImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize(),
    ) {
        AsyncImage(
            uri = imageUri,
            modifier = Modifier
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
        IconButton(
            icon = Icons.Outlined.Close,
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = deleteImage,
            containerColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = Alpha.Medium),
            contentColor = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
private fun ActionIcons (
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSendEnabled = uiState.message.isNotBlank() || uiState.imageUri != null

    Row(
        modifier = modifier
    ) {
        Icon(
            icon = Icons.Outlined.Image,
            modifier = Modifier.clickable {
                onEvent(PostUiEvent.OnImagePickerLaunchClick)
            }
        )
        Spacer(
            modifier = Modifier.weight(Weights.Medium)
        )
        Icon(
            icon = if (isSendEnabled) Icons.Filled.Send else Icons.Outlined.Send,
            modifier = Modifier.clickable(
                enabled = isSendEnabled
            ) {
                onEvent(PostUiEvent.OnMessageSendClick)
            }
        )
    }
}
