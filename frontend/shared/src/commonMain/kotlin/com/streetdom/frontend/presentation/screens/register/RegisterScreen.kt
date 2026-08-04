package com.streetdom.frontend.presentation.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streetdom.frontend.presentation.components.SDButton
import com.streetdom.frontend.presentation.components.SDTextField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen (onLoginClick: () -> Unit) {

    val viewModel: RegisterViewModel = koinViewModel()

    RegisterContent(
        uiState = viewModel.uiState,
        onUsernameChange = viewModel::onUsernameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = viewModel::onRegisterClick,
        onLoginClick = onLoginClick
    )
}

@Composable
private fun RegisterContent(
    uiState: RegisterUiState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("StreetDom")
        Text("Own your city")

        SDTextField(
            value = uiState.username,
            onValueChange = onUsernameChange,
            label = "Username",
            error = uiState.usernameError,
            modifier = Modifier.fillMaxWidth(0.7f).padding(top = 32.dp)
        )

        SDTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Email",
            error = uiState.emailError,
            modifier = Modifier.fillMaxWidth(0.7f).padding(top = 16.dp),
        )

        SDTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = "Password",
            error = uiState.passwordError,
            modifier = Modifier.fillMaxWidth(0.7f).padding(top = 16.dp),
            isPassword = true
        )

        SDTextField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm Password",
            error = uiState.confirmPasswordError,
            modifier = Modifier.fillMaxWidth(0.7f).padding(top = 16.dp),
            isPassword = true
        )

        SDButton(
            text = "Register",
            onClick = onRegisterClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth(0.7f)
                .padding(top = 24.dp)
        )

        uiState.registerError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Row (
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Ya tienes cuenta? ")

            Text(
                text = "Inicia sesión",
                color = if (uiState.isLoading)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = onLoginClick,
                    enabled = !uiState.isLoading
                )
            )
        }
    }
}