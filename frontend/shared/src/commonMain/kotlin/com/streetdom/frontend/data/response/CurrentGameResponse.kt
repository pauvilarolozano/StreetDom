package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class CurrentGameResponse(
    val gameId: String,
    val state: String,
    val createdAt: String,
    val startedAt: String? = null,
    val finishedAt: String? = null,
    val player: PlayerResponse,
    val segments: List<StreetSegmentResponse>,
)
