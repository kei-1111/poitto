package com.example.flush.ui.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun SearchScreen(
    navigateToPost: () -> Unit,
    navigateToUserSettings: () -> Unit,
) {
    CenteredContainer {
        Column {
            BodyMediumText(
                text = "SearchScreen",
            )
            FilledButton(
                text = "navigateToPost",
                onClick = navigateToPost,
            )
            FilledButton(
                text = "navigateToSignUserSettings",
                onClick = navigateToUserSettings,
            )
        }
    }
}
