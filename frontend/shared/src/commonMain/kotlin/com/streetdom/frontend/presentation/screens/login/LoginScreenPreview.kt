package com.streetdom.frontend.presentation.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun LoginScreenPreview() {
    
    LoginContent(
        uiState = LoginUiState(),
        onUsernameChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onRegisterClick = {}
    )

}