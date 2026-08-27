package com.streetdom.frontend.data.mapper

import com.streetdom.frontend.data.response.AuthResponse
import com.streetdom.frontend.data.request.LoginRequest
import com.streetdom.frontend.data.request.RegisterRequest
import com.streetdom.frontend.data.response.TokensResponse
import com.streetdom.frontend.data.response.UserResponse
import com.streetdom.frontend.domain.model.AuthSession
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.model.Tokens
import com.streetdom.frontend.domain.model.User

fun AuthResponse.toDomain(): AuthSession {
    return AuthSession(
        user = user.toDomain(),
        tokens = tokens.toDomain()
    )
}

fun UserResponse.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email
    )
}

fun TokensResponse.toDomain(): Tokens {
    return Tokens(
        accessToken = accessToken,
        refreshToken = refreshToken
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