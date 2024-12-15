package com.example.flush.ui.feature.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.flush.ui.component.AsyncImage
import com.example.flush.ui.component.FadeInAnimateVisibility
import com.example.flush.ui.component.FloatingActionButton
import com.example.flush.ui.component.Icon
import com.example.flush.ui.component.Loading
import com.example.flush.ui.component.TopBar
import com.example.flush.ui.feature.search.SearchScreenDimensions.UserIconBorderWidth
import com.example.flush.ui.theme.dimensions.IconSize
import com.example.flush.ui.theme.dimensions.Paddings
import com.example.flush.ui.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("ModifierMissing")
@Composable
fun SearchScreen(
    navigateToPost: () -> Unit,
    navigateToUserSettings: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val latestNavigateToAuthSelection by rememberUpdatedState(navigateToPost)
    val latestNavigateToSearch by rememberUpdatedState(navigateToUserSettings)

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is SearchUiEvent.OnNavigateToPostClick -> latestNavigateToAuthSelection()
                is SearchUiEvent.OnNavigateToUserSettingsClick -> latestNavigateToSearch()
                is SearchUiEvent.OnModelTap -> {
                    viewModel.updateSelectedThrowingItem(event.throwingItemId)
                    viewModel.updateIsShowBottomSheet(true)
                }
                is SearchUiEvent.OnBottomSheetDismissRequest -> viewModel.updateIsShowBottomSheet(false)
            }
        }.launchIn(this)
    }

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEffect.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { effect ->
            when (effect) {
                is SearchUiEffect.ShowToast -> showToast(context, effect.message)
            }
        }.launchIn(this)
    }

    SearchScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isShowBottomSheet) {
        SearchScreenBottomSheet(
            uiState = uiState,
            onEvent = onEvent,
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchScreenTopBar(
                userIconUrl = uiState.currentUser.iconUrl,
                onNavigateToUserSettings = { onEvent(SearchUiEvent.OnNavigateToUserSettingsClick) },
            )
        },
        floatingActionButton = {
            SearchScreenFloatingActionButton(
                onNavigateToPost = { onEvent(SearchUiEvent.OnNavigateToPostClick) },
            )
        },
    ) { _ ->
        SearchScreenContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize(),
        )
    }

    FadeInAnimateVisibility(
        visible = uiState.isLoading,
    ) {
        Loading()
    }
}

@Composable
private fun SearchScreenTopBar(
    userIconUrl: String?,
    onNavigateToUserSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopBar(
        title = "サーチ",
        modifier = modifier,
        actions = {
            TopBarActionButton(
                userIconUrl = userIconUrl,
                onNavigateToUserSettings = onNavigateToUserSettings,
            )
        },
        contentColor = Color.White,
        containerColor = Color.Transparent,
    )
}

@Composable
private fun TopBarActionButton(
    userIconUrl: String?,
    onNavigateToUserSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        userIconUrl != null -> {
            UserIcon(
                userIconUrl = userIconUrl,
                modifier = modifier
                    .clickable { onNavigateToUserSettings() },
            )
        }

        else -> {
            DefaultUseIcon(
                modifier = modifier
                    .clickable { onNavigateToUserSettings() },
            )
        }
    }
}

@Composable
private fun UserIcon(
    userIconUrl: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        uri = userIconUrl,
        modifier = modifier
            .padding(end = Paddings.Medium)
            .size(IconSize.Medium)
            .border(
                border = BorderStroke(
                    width = UserIconBorderWidth,
                    color = Color.White,
                ),
                shape = CircleShape,
            ),
        shape = CircleShape,
    )
}

@Composable
private fun DefaultUseIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        icon = Icons.Rounded.Person,
        modifier = modifier
            .size(IconSize.Medium)
            .padding(end = Paddings.Medium)
            .border(
                border = BorderStroke(
                    width = UserIconBorderWidth,
                    color = Color.White,
                ),
                shape = CircleShape,
            )
            .clip(CircleShape),
        size = IconSize.Medium,
    )
}

@Composable
private fun SearchScreenFloatingActionButton(
    onNavigateToPost: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onNavigateToPost,
        modifier = modifier,
    )
}
