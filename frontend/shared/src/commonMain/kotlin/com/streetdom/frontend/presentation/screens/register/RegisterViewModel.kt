package com.streetdom.frontend.presentation.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.result.AuthResult
import com.streetdom.frontend.domain.useCase.AuthUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val authUseCase: AuthUseCase

): ViewModel() {

    //TODO hacerlo con stateflow cunado hayas mas flows, validacion de formularios?

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events = _events.asSharedFlow()

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
            register(credentials)
        }
    }

    private suspend fun register(credentials: RegisterCredentials) {
        when (authUseCase.register(credentials)) {
            is AuthResult.Success -> {
                uiState = uiState.copy(isLoading = false, registerError = null)
                _events.emit(RegisterEvent.RegisterSuccess)

            } is AuthResult.UserAlreadyExists -> {
                uiState = uiState.copy(isLoading = false, registerError = "User already exists")

            } is AuthResult.NetworkError -> {
                uiState = uiState.copy(isLoading = false, registerError = "Network error")

            } else -> {
                uiState = uiState.copy(isLoading = false, registerError = "Server error")
            }
        }

    }

    private fun validateForm(): Boolean {
        val usernameError =
            if (uiState.username.isBlank()) "Username is required" else null

        val emailError = when {
            uiState.email.isBlank() -> "Email is required"
            "@" !in uiState.email -> "Invalid email"
            else -> null
        }

        val passwordError =
            if (uiState.password.isBlank()) "Password is required" else null

        val confirmPasswordError = when {
            uiState.confirmPassword.isBlank() -> "Confirm password is required"
            uiState.password != uiState.confirmPassword -> "Passwords do not match"
            else -> null
        }

        uiState = uiState.copy(
            usernameError = usernameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )

        return usernameError == null && emailError == null &&
                passwordError == null && confirmPasswordError == null
    }

}