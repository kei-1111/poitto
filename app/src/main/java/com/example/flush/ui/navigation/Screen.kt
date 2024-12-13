package com.example.flush.ui.navigation

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
