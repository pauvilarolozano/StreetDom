package com.streetdom.frontend.presentation.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.useCase.AuthUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class RegisterViewModel (
    private val authUseCase: AuthUseCase
): ViewModel() {

    //TODO hacerlo con stateflow cunado hayas mas flows, validacion de formularios
    //TODO excepciones en el fututo las gestiona useCase

    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onUsernameChange(username: String) {
        uiState = uiState.copy(username = username, usernameError = null, registerError = null)
    }

    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email, emailError = null, registerError = null)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password, passwordError = null, registerError = null)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        uiState = uiState.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = null,
            registerError = null)
    }

    fun onRegisterClick() {

        if (!validateForm()) return

        val credentials = RegisterCredentials(
            username = uiState.username,
            email = uiState.email,
            password = uiState.password,
            confirmPassword = uiState.confirmPassword
        )

        uiState = uiState.copy(isLoading = true, registerError = null)

        viewModelScope.launch {
            try {
                val authsession = authUseCase.register(credentials)
            } catch (e: Exception) {
                uiState = uiState.copy(registerError = "Error registering")
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    private fun validateForm(): Boolean {

        var valid = true

        if (uiState.username.isBlank()) {
            uiState = uiState.copy(usernameError = "Username is required")
            valid = false
        }

        if (uiState.email.isBlank()) {
            uiState = uiState.copy(emailError = "Email is required")
            valid = false
        }

        if (!uiState.email.contains("@")) {
            uiState = uiState.copy(emailError = "Invalid email")
            valid = false
        }

        if (uiState.password.isBlank()) {
            uiState = uiState.copy(passwordError = "Password is required")
            valid = false
        }

        if (uiState.confirmPassword.isBlank()) {
            uiState = uiState.copy(confirmPasswordError = "Confirm password is required")
            valid = false
        }

        if (uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(confirmPasswordError = "Passwords do not match")
            valid = false
        }

        return valid
    }

}