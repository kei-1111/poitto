package com.example.flush.ui.feature.auth_selection

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun AuthSelectionScreen(
    navigateToSignUp: () -> Unit,
    navigateToSignIn: () -> Unit,
) {
    CenteredContainer {
        Column {
            BodyMediumText(
                text = "AuthSelectionScreen",
            )
            FilledButton(
                text = "navigateToSignUp",
                onClick = navigateToSignUp,
            )
            FilledButton(
                text = "navigateToSignIn",
                onClick = navigateToSignIn,
            )
        }
    }
}
