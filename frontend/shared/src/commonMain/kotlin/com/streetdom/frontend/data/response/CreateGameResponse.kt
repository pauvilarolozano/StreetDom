package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameResponse(
    val gameId: String,
    val state: String,
)
