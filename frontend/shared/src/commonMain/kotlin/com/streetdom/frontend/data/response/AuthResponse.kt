package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: UserResponse,
    val tokens: TokensResponse
)

