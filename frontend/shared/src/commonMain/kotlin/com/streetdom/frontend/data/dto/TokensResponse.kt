package com.streetdom.frontend.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokensResponse(
    val accessToken: String,
    val refreshToken: String
)
