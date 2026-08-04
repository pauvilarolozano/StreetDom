package com.streetdom.frontend.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: UserResponse,
    val tokens: TokensResponse
)

