package com.example.flush.ui.feature.user_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun UserSettingsScreen(
    navigateToSearch: () -> Unit,
    navigateToAuthSelection: () -> Unit,
) {
    Scaffold { innerPadding ->
        CenteredContainer(
            modifier = Modifier.padding(innerPadding),
        ) {
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
}
