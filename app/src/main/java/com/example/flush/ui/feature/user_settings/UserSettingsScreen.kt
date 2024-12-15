package com.example.flush.ui.feature.user_settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.flush.ui.component.FadeInAnimateVisibility
import com.example.flush.ui.component.IconButton
import com.example.flush.ui.component.Loading
import com.example.flush.ui.component.TopBar
import com.example.flush.ui.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("ModifierMissing")
@Composable
fun UserSettingsScreen(
    navigateToSearch: () -> Unit,
    navigateToAuthSelection: () -> Unit,
    viewModel: UserSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val latestNavigateToAuthSelection by rememberUpdatedState(navigateToAuthSelection)
    val latestNavigateToSearch by rememberUpdatedState(navigateToSearch)

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.updateImageUri(uri)
            }
        },
    )

    val focusManager = LocalFocusManager.current

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is UserSettingsUiEvent.OnNavigateToSearchClick -> {
                    viewModel.saveUser()
                }
                is UserSettingsUiEvent.OnNameInputChange -> viewModel.updateName(event.name)
                is UserSettingsUiEvent.OnImagePickerLaunchClick -> {
                    focusManager.clearFocus()
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }
                is UserSettingsUiEvent.OnSignOutClick -> viewModel.signOut()
            }
        }.launchIn(this)
    }

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEffect.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { effect ->
            when (effect) {
                is UserSettingsUiEffect.ShowToast -> showToast(context, effect.message)
                is UserSettingsUiEffect.NavigateToAuthSelection -> latestNavigateToAuthSelection()
                is UserSettingsUiEffect.NavigateToSearch -> latestNavigateToSearch()
            }
        }.launchIn(this)
    }

    UserSettingsScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
private fun UserSettingsScreen(
    uiState: UserSettingsUiState,
    onEvent: (UserSettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            UserSettingsTopBar(
                onNavigateToSearch = { onEvent(UserSettingsUiEvent.OnNavigateToSearchClick) },
            )
        },
    ) { innerPadding ->
        UserSettingsScreenContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier.padding(innerPadding),
        )
    }

    FadeInAnimateVisibility(
        visible = uiState.isLoading,
    ) {
        Loading()
    }
}

@Composable
private fun UserSettingsTopBar(
    onNavigateToSearch: () -> Unit,
) {
    TopBar(
        title = "ユーザ設定",
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                icon = Icons.Outlined.ArrowBackIosNew,
                onClick = onNavigateToSearch,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        },
    )
}
