package com.streetdom.frontend.domain.model

data class AuthSession(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)
