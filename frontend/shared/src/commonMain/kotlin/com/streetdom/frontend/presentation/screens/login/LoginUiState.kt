package com.streetdom.frontend.presentation.screens.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",

    val usernameError: String? = null,
    val passwordError: String? = null,

    val loginError: String? = null,
    val isLoading: Boolean = false
)