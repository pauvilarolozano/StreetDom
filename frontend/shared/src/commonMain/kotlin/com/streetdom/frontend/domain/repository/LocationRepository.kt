package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
}