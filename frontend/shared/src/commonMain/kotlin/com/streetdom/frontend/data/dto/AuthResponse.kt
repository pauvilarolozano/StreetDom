package com.streetdom.frontend.data.dto

data class AuthResponse(
    val user: UserResponse,
    val accessToken: String,
    val refreshToken: String
)

