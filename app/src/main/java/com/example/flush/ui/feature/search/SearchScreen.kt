package com.example.flush.ui.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.BottomBar
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton

@Suppress("ModifierMissing")
@Composable
fun SearchScreen(
    navigateToPost: () -> Unit,
    navigateToUserSettings: () -> Unit,
    currentDestination: NavDestination?,
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                navigateToUserSettings = navigateToUserSettings,
                currentDestination = currentDestination,
            )
        },
    ) { innerPadding ->
        CenteredContainer(
            modifier = Modifier.padding(innerPadding),
        ) {
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
}
