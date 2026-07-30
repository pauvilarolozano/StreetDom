package com.streetdom.frontend.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.useCase.AuthUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class LoginViewModel (
    private val authUseCase: AuthUseCase
): ViewModel() {

    //TODO hacerlo con stateflow cuando haya mas flows, validacion de formularios
    //TODO excepciones en el fututo las gestiona useCase

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
            try {
                delay(3000.milliseconds)
                val authSession = authUseCase.login(credentials)
            } catch (e: Exception) {
                uiState = uiState.copy(loginError = "Error logging in")
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

        if (uiState.password.isBlank()) {
            uiState = uiState.copy(passwordError = "Password is required")
            valid = false
        }

        return valid
    }

}
