package com.example.flush.ui.feature.sign_in

import androidx.activity.compose.rememberLauncherForActivityResult
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
fun SignInScreen(
    navigateToAuthSelection: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val latestNavigateToAuthSelection by rememberUpdatedState(navigateToAuthSelection)
    val latestNavigateToSearch by rememberUpdatedState(navigateToSearch)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            viewModel.handleSignInResult(result.data)
        },
    )

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is SignInUiEvent.OnNavigateToAuthSelectionClick -> latestNavigateToAuthSelection()
                is SignInUiEvent.OnEmailInputChange -> viewModel.updateEmail(event.email)
                is SignInUiEvent.OnPasswordInputChange -> viewModel.updatePassword(event.password)
                is SignInUiEvent.OnSubmitClick -> viewModel.submitRegister()
                is SignInUiEvent.OnGoogleSignUpClick -> viewModel.startGoogleSignIn(googleSignInLauncher)
            }
        }.launchIn(this)
    }

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEffect.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { effect ->
            when (effect) {
                is SignInUiEffect.NavigateToSearch -> latestNavigateToSearch()
                is SignInUiEffect.ShowToast -> showToast(context, effect.message)
            }
        }.launchIn(this)
    }

    SignInScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun SignInScreen(
    uiState: SignInUiState,
    onEvent: (SignInUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SignInScreenTopBar(
                onNavigateToAuthSelection = { onEvent(SignInUiEvent.OnNavigateToAuthSelectionClick) },
            )
        },
    ) { innerPadding ->
        SignInScreenContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }

    FadeInAnimateVisibility(
        visible = uiState.isLoading,
    ) {
        Loading()
    }
}

@Composable
private fun SignInScreenTopBar(
    onNavigateToAuthSelection: () -> Unit,
) {
    TopBar(
        title = null,
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                icon = Icons.Outlined.ArrowBackIosNew,
                onClick = onNavigateToAuthSelection,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        },
    )
}
