package com.streetdom.frontend.domain.model

data class Player(
    val userId: String,
    val gameId: String,
    val role: PlayerRole,
    val joinedAt: String,
)
