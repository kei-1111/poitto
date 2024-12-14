package com.example.flush.ui.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.CenteredContainer
import com.example.flush.ui.compose.FilledButton
import com.example.flush.ui.theme.dimensions.Weights
import com.example.flush.ui.utils.showToast
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
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
    val throwingItems = viewModel.throwingItems

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val latestNavigateToAuthSelection by rememberUpdatedState(navigateToPost)
    val latestNavigateToSearch by rememberUpdatedState(navigateToUserSettings)

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is SearchUiEvent.OnNavigateToPostClick -> latestNavigateToAuthSelection()
                is SearchUiEvent.OnNavigateToUserSettingsClick -> latestNavigateToSearch()
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
        throwingItems = throwingItems.toPersistentList(),
        onEvent = viewModel::onEvent,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun SearchScreen(
    throwingItems: ImmutableList<ThrowingItem>,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
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
                    onClick = { onEvent(SearchUiEvent.OnNavigateToPostClick) },
                )
                FilledButton(
                    text = "navigateToSignUserSettings",
                    onClick = { onEvent(SearchUiEvent.OnNavigateToUserSettingsClick) },
                )
                SearchScreenContent(
                    throwingItems = throwingItems,
                    modifier = Modifier.weight(Weights.Medium),
                )
            }
        }
    }
}
