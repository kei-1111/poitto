package com.example.flush.ui.feature.sign_in

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.flush.R
import com.example.flush.ui.component.BodyMediumText
import com.example.flush.ui.component.EmailTextField
import com.example.flush.ui.component.FilledButton
import com.example.flush.ui.component.Image
import com.example.flush.ui.component.PasswordTextField
import com.example.flush.ui.component.TitleLargeText
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.BorderStrokeWidth
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.EmailTextFieldHeight
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.GoogleSignUpButtonHeight
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.HorizontalDividerWidth
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.MinPasswordLength
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.PasswordTextFieldHeight
import com.example.flush.ui.feature.sign_in.SignInScreenDimensions.SubmitButtonHeight
import com.example.flush.ui.theme.dimensions.Paddings

@Composable
fun SignInScreenContent(
    uiState: SignInUiState,
    onEvent: (SignInUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = Paddings.Large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.Large),
    ) {
        SignUpHeader()
        EmailSignUpFields(
            uiState = uiState,
            onEvent = onEvent,
        )
        HorizontalDivider(
            modifier = Modifier
                .width(HorizontalDividerWidth)
                .padding(vertical = Paddings.Large),
        )
        GoogleSignInButton(
            onClick = { onEvent(SignInUiEvent.OnGoogleSignUpClick) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SignUpHeader(
    modifier: Modifier = Modifier,
) {
    TitleLargeText(
        text = "サインイン",
        modifier = modifier
            .padding(Paddings.Medium),
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
private fun EmailSignUpFields(
    uiState: SignInUiState,
    onEvent: (SignInUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Paddings.Large),
        ) {
            EmailTextField(
                email = uiState.email,
                onEmailChange = { onEvent(SignInUiEvent.OnEmailInputChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(EmailTextFieldHeight),
            )
            PasswordTextField(
                password = uiState.password,
                onPasswordChange = { onEvent(SignInUiEvent.OnPasswordInputChange(it)) },
                enable = uiState.password.length >= MinPasswordLength,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PasswordTextFieldHeight),
            )
        }
        SubmitButton(
            onSubmit = { onEvent(SignInUiEvent.OnSubmitClick) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.email.isNotEmpty() && uiState.password.length >= MinPasswordLength,
        )
    }
}

@Composable
private fun SubmitButton(
    onSubmit: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    FilledButton(
        text = "サインイン",
        onClick = onSubmit,
        modifier = modifier
            .height(SubmitButtonHeight),
        enabled = enabled,
    )
}

@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(GoogleSignUpButtonHeight),
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(
            horizontal = Paddings.Medium,
            vertical = Paddings.Small,
        ),
        border = BorderStroke(width = BorderStrokeWidth, MaterialTheme.colorScheme.onSurface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                resId = R.drawable.ic_google,
                modifier = Modifier.align(Alignment.CenterStart),
            )
            BodyMediumText(
                text = "Googleでサインイン",
            )
        }
    }
}
