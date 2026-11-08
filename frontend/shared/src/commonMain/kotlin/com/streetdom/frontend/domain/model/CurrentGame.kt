package com.streetdom.frontend.domain.model

data class CurrentGame(
    val gameId: String,
    val state: String,
    val segments: List<StreetSegment> = emptyList(),
    val player: Player? = null,
    val createdAt: String? = null,
    val startedAt: String? = null,
    val finishedAt: String? = null,
)
