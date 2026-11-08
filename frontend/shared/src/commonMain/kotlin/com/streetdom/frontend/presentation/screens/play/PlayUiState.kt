package com.streetdom.frontend.presentation.screens.play

import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.model.Location

data class PlayUiState(
    val isLoading: Boolean = false,
    val currentLocation: Location? = null,
    val currentGame: CurrentGame? = null,
    val errorMessage: String? = null,
)
