package com.example.flush.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.flush.ui.navigation.Screen
import com.example.flush.ui.navigation.TopLevelScreen
import com.example.flush.ui.theme.dimensions.IconSize

private val topLevelScreens = listOf(
    TopLevelScreen(
        name = "探す",
        route = Screen.Search,
        outlinedIcon = Icons.Outlined.RocketLaunch,
        filledIcon = Icons.Filled.RocketLaunch,
    ),
    TopLevelScreen(
        name = "設定",
        route = Screen.UserSettings,
        outlinedIcon = Icons.Outlined.Person,
        filledIcon = Icons.Filled.Person,
    ),
)

@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    navigateToSearch: (() -> Unit)? = null,
    navigateToUserSettings: (() -> Unit)? = null,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        topLevelScreens.forEach { topLevelScreen ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == topLevelScreen.route::class.qualifiedName } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (isSelected) return@NavigationBarItem
                    navigateToSearch?.invoke()
                    navigateToUserSettings?.invoke()
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) topLevelScreen.filledIcon else topLevelScreen.outlinedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(IconSize.Medium),
                    )
                },
                label = {
                    Text(
                        text = topLevelScreen.name,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
            )
        }
    }
}
