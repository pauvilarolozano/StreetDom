package com.streetdom.frontend.domain.useCase

import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.repository.LocationRepository

class LocationUseCase(
    private val locationRepository: LocationRepository
) {

    fun isLocationEnabled(): Boolean {
        return locationRepository.isLocationEnabled()
    }

    suspend fun getCurrentLocation(): GpsPosition? {
        return locationRepository.getCurrentLocation()
    }
}
