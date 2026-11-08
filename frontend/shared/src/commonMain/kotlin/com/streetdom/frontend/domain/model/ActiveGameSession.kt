package com.streetdom.frontend.domain.model

data class ActiveGameSession(
    val gameId: String,
    val state: String,
    val role: PlayerRole
)
