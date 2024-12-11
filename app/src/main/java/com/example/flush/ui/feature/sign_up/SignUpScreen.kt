package com.example.flush.ui.feature.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun SignUpScreen(
    navigateToSignIn: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    CenteredContainer {
        Column {
            BodyMediumText(
                text = "SignUpScreen",
            )
            FilledButton(
                text = "navigateToSignIn",
                onClick = navigateToSignIn,
            )
            FilledButton(
                text = "navigateToSearch",
                onClick = navigateToSearch,
            )
        }
    }
}
