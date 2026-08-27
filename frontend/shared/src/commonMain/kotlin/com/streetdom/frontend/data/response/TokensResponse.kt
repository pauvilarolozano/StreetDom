package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class TokensResponse(
    val accessToken: String,
    val refreshToken: String
)
