package com.streetdom.frontend.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token: String
)
