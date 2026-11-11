package com.streetdom.frontend.domain.model

data class GpsPosition(
    val latitude: Double,
    val longitude: Double,
    val recordedAt: String? = null,
    val accuracyMeters: Double? = null,
)
