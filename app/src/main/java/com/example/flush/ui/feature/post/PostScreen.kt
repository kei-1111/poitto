package com.example.flush.ui.feature.post

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
import com.example.flush.ui.compose.IconButton
import com.example.flush.ui.compose.TopBar
import com.example.flush.ui.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("ModifierMissing")
@Composable
fun PostScreen(
    navigateToSearch: () -> Unit,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val latestNavigateToSearch by rememberUpdatedState(navigateToSearch)

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.updateImageUri(uri) },
    )

    val focusManager = LocalFocusManager.current

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is PostUiEvent.OnNavigateToSearchClick -> latestNavigateToSearch()
                is PostUiEvent.OnMessageInputChange -> viewModel.updateMessage(event.message)
                is PostUiEvent.OnImagePickerLaunchClick -> {
                    focusManager.clearFocus()
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }
                is PostUiEvent.OnImageRemoveClick -> viewModel.updateImageUri(null)
                is PostUiEvent.OnMessageSendClick -> viewModel.toThrowPhase()
            }
        }.launchIn(this)
    }

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEffect.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { effect ->
            when (effect) {
                is PostUiEffect.ShowToast -> showToast(context, effect.message)
            }
        }.launchIn(this)
    }

    PostScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun PostScreen(
    uiState: PostUiState,
    onEvent: (PostUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            when (uiState.phase) {
                PostUiPhase.Writing -> {
                    PostScreenTopBar(
                        onNavigateToSearch = { onEvent(PostUiEvent.OnNavigateToSearchClick) },
                    )
                }

                PostUiPhase.Throwing -> {}
            }
        },
    ) { innerPadding ->
        PostScreenContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

@Composable
private fun PostScreenTopBar(
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopBar(
        title = null,
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                icon = Icons.Outlined.ArrowBackIosNew,
                onClick = onNavigateToSearch,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        },
    )
}
