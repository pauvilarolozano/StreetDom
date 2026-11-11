package com.streetdom.frontend.presentation.screens.play

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.manager.GpsTrackerManager
import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.result.GetCurrentGameResult
import com.streetdom.frontend.domain.result.GetGameSegmentsResult
import com.streetdom.frontend.domain.useCase.GameUseCase
import com.streetdom.frontend.domain.useCase.LocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val locationUseCase: LocationUseCase,
    private val gameUseCase: GameUseCase,
    private val gpsTrackerManager: GpsTrackerManager,
) : ViewModel() {

    var uiState by mutableStateOf(PlayUiState())
        private set

    private val _gpsPosition = MutableStateFlow<GpsPosition?>(null)
    val gpsPosition: StateFlow<GpsPosition?> = _gpsPosition.asStateFlow()

    init {
        viewModelScope.launch {
            gameUseCase.currentGame.collect { game ->
                uiState = uiState.copy(currentGame = game)
            }
        }
        viewModelScope.launch {
            gpsTrackerManager.isTracking.collect { isTracking ->
                uiState = uiState.copy(isTrackingActive = isTracking)
            }
        }
        loadGameData()
    }

    fun loadGameData() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            val currentGpsPosition = locationUseCase.getCurrentLocation()
            _gpsPosition.value = currentGpsPosition

            var errorMessage: String? = null
            if (gameUseCase.getCurrentGame() == null) {
                errorMessage = when (gameUseCase.loadCurrentGame()) {
                    is GetCurrentGameResult.Success -> null
                    GetCurrentGameResult.NotFound -> "No current game found."
                    GetCurrentGameResult.NetworkError -> "Network error loading current game."
                    GetCurrentGameResult.ServerError -> "Server error loading current game."
                }
            }
            val currentGame = gameUseCase.getCurrentGame()

            uiState = uiState.copy(
                isLoading = false,
                currentGpsPosition = currentGpsPosition,
                currentGame = currentGame,
                errorMessage = errorMessage,
            )
        }
    }

    fun reloadMap() {
        val gameId = uiState.currentGame?.gameId ?: return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            when (gameUseCase.loadGameSegments(gameId)) {
                is GetGameSegmentsResult.Success -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        currentGame = gameUseCase.getCurrentGame(),
                    )
                }
                is GetGameSegmentsResult.NotFound -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "Map not found for game.",
                    )
                }
                is GetGameSegmentsResult.NetworkError -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "Network error reloading map.",
                    )
                }
                is GetGameSegmentsResult.ServerError -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "Server error reloading map.",
                    )
                }
            }
        }
    }

    fun onToggleTracking() {
        if (uiState.currentGame == null) return
        gpsTrackerManager.toggleTracking()
    }
}
