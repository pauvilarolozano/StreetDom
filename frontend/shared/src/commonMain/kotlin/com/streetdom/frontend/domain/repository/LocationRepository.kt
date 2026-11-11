package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.GpsPosition

interface LocationRepository {

    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): GpsPosition?
}
