package com.example.flush.ui.feature.user_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.flush.ui.component.AsyncImage
import com.example.flush.ui.component.IconButton
import com.example.flush.ui.component.NameTextField
import com.example.flush.ui.component.OutlinedButton
import com.example.flush.ui.feature.user_settings.UserSettingsScreenDimensions.NameTextFieldHeight
import com.example.flush.ui.feature.user_settings.UserSettingsScreenDimensions.PreviewUserIconSize
import com.example.flush.ui.feature.user_settings.UserSettingsScreenDimensions.SignOutButtonHeight
import com.example.flush.ui.theme.dimensions.IconSize
import com.example.flush.ui.theme.dimensions.Paddings
import com.example.flush.ui.theme.dimensions.Weights

@Composable
fun UserSettingsScreenContent(
    uiState: UserSettingsUiState,
    onEvent: (UserSettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(Paddings.Large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.Medium),
    ) {
        PreviewUserIcon(
            uiState = uiState,
            onEvent = onEvent,
        )
        NameTextField(
            name = uiState.name,
            onNameChange = { onEvent(UserSettingsUiEvent.OnNameInputChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(NameTextFieldHeight),
        )
        Spacer(
            modifier = Modifier.weight(Weights.Medium),
        )
        SignOutButton(
            onSignOut = { onEvent(UserSettingsUiEvent.OnSignOutClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(SignOutButtonHeight),
        )
    }
}

@Composable
private fun PreviewUserIcon(
    uiState: UserSettingsUiState,
    onEvent: (UserSettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .wrapContentSize(),
    ) {
        uiState.imageUri?.let {
            AsyncImage(
                uri = it,
                modifier = Modifier.size(PreviewUserIconSize),
                shape = CircleShape,
            )
        }
        IconButton(
            icon = Icons.Outlined.Image,
            onClick = { onEvent(UserSettingsUiEvent.OnImagePickerLaunchClick) },
            modifier = Modifier.align(Alignment.BottomEnd),
            iconSize = IconSize.Medium,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    }
}

@Composable
private fun SignOutButton(
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        text = "ログアウト",
        onClick = onSignOut,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        contentColor = MaterialTheme.colorScheme.error,
    )
}
