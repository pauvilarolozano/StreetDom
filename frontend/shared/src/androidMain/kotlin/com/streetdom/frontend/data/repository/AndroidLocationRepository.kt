package com.streetdom.frontend.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.repository.LocationRepository
import kotlinx.coroutines.tasks.await

class AndroidLocationRepository(
    context: Context
) : LocationRepository{

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER
        ) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): GpsPosition? {
        return try {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()?.let {
                val isoDate = java.text.SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    java.util.Locale.US
                ).apply {
                    timeZone = java.util.TimeZone.getTimeZone("UTC")
                }.format(java.util.Date(it.time))

                GpsPosition(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    recordedAt = isoDate,
                    accuracyMeters = it.accuracy.toDouble()
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}
