package com.streetdom.frontend.data.request

import kotlinx.serialization.Serializable

@Serializable
data class GpsPositionRequest(
    val latitude: Double,
    val longitude: Double,
    val recordedAt: String,
    val accuracyMeters: Double
)
