package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class FinishGameResponse(
    val gameId: String,
    val state: String,
    val finishedAt: String,
)
