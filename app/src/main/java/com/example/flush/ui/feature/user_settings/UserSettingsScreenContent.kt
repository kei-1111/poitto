package com.example.flush.ui.feature.user_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flush.ui.compose.NameTextField
import com.example.flush.ui.compose.OutlinedButton
import com.example.flush.ui.feature.user_settings.UserSettingsScreenDimensions.NameTextFieldHeight
import com.example.flush.ui.feature.user_settings.UserSettingsScreenDimensions.SignOutButtonHeight
import com.example.flush.ui.theme.dimensions.Paddings
import com.example.flush.ui.theme.dimensions.Weights

@Composable
fun UserSettingsScreenContent(
    uiState: UserSettingsUiState,
    onEvent: (UserSettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(Paddings.Large),
    ) {
        NameTextField(
            name = uiState.name,
            onNameChange = { onEvent(UserSettingsUiEvent.OnNameInputChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(NameTextFieldHeight),
        )
        Spacer(
            modifier = Modifier.weight(Weights.Medium)
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
