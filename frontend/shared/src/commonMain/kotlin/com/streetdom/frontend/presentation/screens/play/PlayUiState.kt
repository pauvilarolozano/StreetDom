package com.streetdom.frontend.presentation.screens.play

import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.model.GpsPosition

data class PlayUiState(
    val isLoading: Boolean = false,
    val currentGpsPosition: GpsPosition? = null,
    val currentGame: CurrentGame? = null,
    val isTrackingActive: Boolean = false,
    val errorMessage: String? = null,
)
