package com.streetdom.frontend.data.mapper

import com.streetdom.frontend.data.dto.AuthResponse
import com.streetdom.frontend.data.dto.LoginRequest
import com.streetdom.frontend.data.dto.RegisterRequest
import com.streetdom.frontend.domain.model.AuthSession
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials

fun AuthResponse.toDomain(): AuthSession {
    return AuthSession(
        user = user.toDomain(),
        tokens = tokens.toDomain()
    )
}

fun LoginCredentials.toRequest(): LoginRequest {
    return LoginRequest(
        username = username,
        password = password
    )
}

fun RegisterCredentials.toRequest(): RegisterRequest {
    return RegisterRequest(
        username = username,
        email = email,
        password = password
    )
}