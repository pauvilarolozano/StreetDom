package com.streetdom.frontend.domain.manager

import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.useCase.LocationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.seconds

class GpsTrackerManager(
    private val locationUseCase: LocationUseCase,
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var trackerJob: Job? = null

    private val gpsPositionBuffer = mutableListOf<GpsPosition>()
    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()

    private val lifecycleMutex = Mutex()
    
    fun toggleTracking() {
        scope.launch {
            lifecycleMutex.withLock {
                if (trackerJob?.isActive == true) {
                    stopTracking()
                } else {
                    startTracking()
                }
            }
        }
    }

    private fun startTracking() {
        _isTracking.value = true

        trackerJob = scope.launch {
            try {
                while (isActive) {
                    val currentGpsPosition = locationUseCase.getCurrentLocation()

                    if (currentGpsPosition != null) {
                        gpsPositionBuffer.add(currentGpsPosition)
                    }

                    delay(5.seconds)
                }
            } finally {
                _isTracking.value = false
            }
        }
    }

    private suspend fun stopTracking() {
        trackerJob?.cancelAndJoin()
        trackerJob = null
        gpsPositionBuffer.clear()
        _isTracking.value = false
    }

}
