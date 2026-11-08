package com.streetdom.frontend.data.request

import kotlinx.serialization.Serializable

@Serializable
data class StartGameRequest(
    val gameId: String,
)
