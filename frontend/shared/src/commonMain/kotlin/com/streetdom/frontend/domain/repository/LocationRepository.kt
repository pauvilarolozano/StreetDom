package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.Location

interface LocationRepository {

    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): Location?
}