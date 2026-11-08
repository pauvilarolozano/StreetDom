package com.streetdom.frontend.data.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRequest(
    val gpsPosition: GpsPositionRequest,
)
