package com.example.flush.ui.feature.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun SignInScreen(
    navigateToSignUp: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    CenteredContainer {
        Column {
            BodyMediumText(
                text = "SignInScreen",
            )
            FilledButton(
                text = "navigateToSignUp",
                onClick = navigateToSignUp,
            )
            FilledButton(
                text = "navigateToSearch",
                onClick = navigateToSearch,
            )
        }
    }
}
