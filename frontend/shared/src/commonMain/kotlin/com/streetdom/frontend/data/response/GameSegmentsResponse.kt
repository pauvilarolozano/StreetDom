package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class GameSegmentsResponse(
    val gameId: String,
    val segments: List<StreetSegmentResponse>,
)
