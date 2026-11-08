package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val latitude: Double,
    val longitude: Double,
)
