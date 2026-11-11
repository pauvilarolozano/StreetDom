package com.streetdom.frontend.data.request

import kotlinx.serialization.Serializable

@Serializable
data class FinishGameRequest(
    val gameId: String,
)
