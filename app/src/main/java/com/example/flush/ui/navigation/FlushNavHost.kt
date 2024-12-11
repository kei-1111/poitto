package com.example.flush.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flush.ui.feature.auth_selection.AuthSelectionScreen
import com.example.flush.ui.feature.post.PostScreen
import com.example.flush.ui.feature.search.SearchScreen
import com.example.flush.ui.feature.sign_in.SignInScreen
import com.example.flush.ui.feature.sign_up.SignUpScreen
import com.example.flush.ui.feature.user_settings.UserSettingsScreen

@Composable
fun FlushNavHost(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Screen.AuthSelection> {
            AuthSelectionScreen(
                navigateToSignUp = { navController.navigateTo(Screen.SignUp) },
                navigateToSignIn = { navController.navigateTo(Screen.SignIn) },
            )
        }

        composable<Screen.SignUp> {
            SignUpScreen(
                navigateToSignIn = { navController.navigateTo(Screen.SignIn) },
                navigateToSearch = { navController.navigateTo(Screen.Search) },
            )
        }

        composable<Screen.SignIn> {
            SignInScreen(
                navigateToSignUp = { navController.navigateTo(Screen.SignUp) },
                navigateToSearch = { navController.navigateTo(Screen.Search) },
            )
        }

        composable<Screen.Search> {
            SearchScreen(
                navigateToPost = { navController.navigateTo(Screen.Post) },
                navigateToUserSettings = { navController.navigateTo(Screen.UserSettings) },
            )
        }

        composable<Screen.Post> {
            PostScreen(
                navigateToSearch = { navController.navigateTo(Screen.Search) },
            )
        }

        composable<Screen.UserSettings> {
            UserSettingsScreen(
                navigateToSearch = { navController.navigateTo(Screen.Search) },
                navigateToAuthSelection = { navController.navigateTo(Screen.AuthSelection) },
            )
        }
    }
}

fun NavHostController.navigateTo(screen: Screen) {
    navigate(screen)
}
