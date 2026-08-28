package com.streetdom.frontend.domain.useCase

import com.streetdom.frontend.domain.model.Location
import com.streetdom.frontend.domain.repository.LocationRepository

class LocationUseCase(
    private val locationRepository: LocationRepository
) {

    suspend fun getCurrentLocation(): Location? {
        return locationRepository.getCurrentLocation()
    }

}