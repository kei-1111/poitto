package com.example.flush.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object AuthSelection : Screen

    @Serializable
    data object SignUp : Screen

    @Serializable
    data object SignIn : Screen

    @Serializable
    data object Search : Screen

    @Serializable
    data object Post : Screen

    @Serializable
    data object UserSettings : Screen
}

data class TopLevelScreen(
    val name: String,
    val route: Screen,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
)
