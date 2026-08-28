package com.streetdom.frontend.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.streetdom.frontend.domain.model.Location
import com.streetdom.frontend.domain.repository.LocationRepository
import kotlinx.coroutines.tasks.await

class AndroidLocationRepository(
    context: Context
) : LocationRepository{

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        return try {
            fusedLocationClient.lastLocation.await()?.let {
                Location(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}