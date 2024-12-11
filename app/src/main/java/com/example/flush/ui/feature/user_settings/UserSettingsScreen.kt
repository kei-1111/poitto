package com.example.flush.ui.feature.user_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun UserSettingsScreen(
    navigateToSearch: () -> Unit,
    navigateToAuthSelection: () -> Unit,
) {
    CenteredContainer {
        Column {
            BodyMediumText(
                text = "UserSettingsScreen",
            )
            FilledButton(
                text = "navigateToSearch",
                onClick = navigateToSearch,
            )
            FilledButton(
                text = "navigateToAuthSelection",
                onClick = navigateToAuthSelection,
            )
        }
    }
}
