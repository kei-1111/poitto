package com.example.flush.ui.feature.post

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun PostScreen(
    navigateToSearch: () -> Unit,
) {
    CenteredContainer {
        Column {
            BodyMediumText(
                text = "Post Screen",
            )
            FilledButton(
                text = "navigateToSearch",
                onClick = navigateToSearch,
            )
        }
    }
}
