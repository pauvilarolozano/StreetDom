package com.streetdom.frontend.domain.model

data class RegisterCredentials(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
