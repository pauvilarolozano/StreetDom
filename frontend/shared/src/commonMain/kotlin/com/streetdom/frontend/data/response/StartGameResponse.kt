package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class StartGameResponse(
    val gameId: String,
    val state: String,
    val startedAt: String? = null,
    val player: PlayerResponse? = null,
)
