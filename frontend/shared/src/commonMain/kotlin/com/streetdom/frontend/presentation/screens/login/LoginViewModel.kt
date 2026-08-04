package com.streetdom.frontend.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.result.AuthResult
import com.streetdom.frontend.domain.useCase.AuthUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class LoginViewModel (
    private val authUseCase: AuthUseCase
): ViewModel() {

    //TODO hacerlo con stateflow cuando haya mas flows, validacion de formularios

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(username: String) {
        uiState = uiState.copy(username = username, usernameError = null, loginError = null)
    }
    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password, passwordError = null, loginError = null)
    }

    fun onLoginClick() {
        if (!validateForm()) return

        val credentials = LoginCredentials(
            username = uiState.username,
            password = uiState.password
        )

        uiState = uiState.copy(isLoading = true, loginError = null)

        viewModelScope.launch {
            login(credentials)
        }
    }

    private suspend fun login(credentials: LoginCredentials) {

        when (authUseCase.login(credentials)) {
            is AuthResult.Success -> {
                uiState = uiState.copy(isLoading = false,loginError = null)
                //TODO navegar a otra pantalla

            } is AuthResult.InvalidCredentials -> {
                uiState = uiState.copy(isLoading = false,loginError = "Invalid credentials")

            }is AuthResult.NetworkError -> {
                uiState = uiState.copy(isLoading = false, loginError = "Network error")

            } else -> {
                uiState = uiState.copy(isLoading = false, loginError = "Server error")
            }
        }
    }

    private fun validateForm(): Boolean {

        val usernameError =
            if (uiState.username.isBlank()) "Username is required" else null

        val passwordError =
            if (uiState.password.isBlank()) "Password is required" else null

        uiState = uiState.copy(
            usernameError = usernameError,
            passwordError = passwordError
        )

        return usernameError == null && passwordError == null
    }

}
