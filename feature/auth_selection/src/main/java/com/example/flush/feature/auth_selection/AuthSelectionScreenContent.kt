package com.example.flush.feature.auth_selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.flush.core.designsystem.component.DisplayMediumText
import com.example.flush.core.designsystem.component.FilledButton
import com.example.flush.core.designsystem.component.Image
import com.example.flush.core.designsystem.component.OutlinedButton
import com.example.flush.core.designsystem.theme.dimensions.Paddings
import com.example.flush.core.designsystem.theme.dimensions.Weights
import com.example.flush.feature.auth_selection.AuthSelectionScreenDimensions.NavigateToSignInButtonHeight
import com.example.flush.feature.auth_selection.AuthSelectionScreenDimensions.NavigateToSignUpButtonHeight
import com.example.flush.feature.auth_selection.AuthSelectionScreenDimensions.PoittoImageSize

@Composable
fun AuthSelectionScreenContent(
    onEvent: (AuthSelectionUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(Paddings.Large),
    ) {
        AuthSelectionHeader(
            modifier = Modifier.weight(Weights.Heavy),
        )
        AuthSelectionFields(
            onEvent = onEvent,
            modifier = Modifier.weight(Weights.Medium),
        )
    }
}

@Composable
private fun AuthSelectionHeader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                resId = R.drawable.poitto_icon,
                size = PoittoImageSize,
                modifier = Modifier.clip(CircleShape),
            )
            DisplayMediumText(
                text = "Poitto",
            )
        }
    }
}

@Composable
private fun AuthSelectionFields(
    onEvent: (AuthSelectionUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavigateToSignUpButton(
            onNavigateToSignUpClick = { onEvent(AuthSelectionUiEvent.OnNavigateToSignUpClick) },
        )
        NavigateToSignInButton(
            onNavigateToSignInClick = { onEvent(AuthSelectionUiEvent.OnNavigateToSignInClick) },
        )
    }
}

@Composable
private fun NavigateToSignUpButton(
    onNavigateToSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledButton(
        text = "新規登録",
        onClick = onNavigateToSignUpClick,
        modifier = modifier
            .fillMaxWidth()
            .height(NavigateToSignUpButtonHeight),
    )
}

@Composable
private fun NavigateToSignInButton(
    onNavigateToSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        text = "ログイン",
        onClick = onNavigateToSignInClick,
        modifier = modifier
            .fillMaxWidth()
            .height(NavigateToSignInButtonHeight),
        contentColor = MaterialTheme.colorScheme.onSurface,
    )
}
