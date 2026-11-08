package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class StreetSegmentResponse(
    val streetSegmentId: String,
    val geometry: List<LocationResponse>,
    val state: String,
)
