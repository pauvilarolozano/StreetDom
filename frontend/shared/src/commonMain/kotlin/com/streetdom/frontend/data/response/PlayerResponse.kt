package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponse(
    val userId: String,
    val gameId: String,
    val role: String,
    val joinedAt: String
)
