package com.streetdom.frontend.presentation.screens.gameMenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.result.CreateGameResult
import com.streetdom.frontend.domain.result.GetCurrentGameResult
import com.streetdom.frontend.domain.result.StartGameResult
import com.streetdom.frontend.domain.useCase.GameUseCase
import kotlinx.coroutines.launch

class GameMenuViewModel(
    private val gameUseCase: GameUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(GameMenuUiState())
        private set

    init {
        viewModelScope.launch {
            gameUseCase.currentGame.collect { currentGame ->
                uiState = uiState.copy(
                    hasCurrentGame = currentGame != null,
                    currentGameId = currentGame?.gameId,
                )
            }
        }
        if (gameUseCase.getCurrentGame() == null) {
            viewModelScope.launch {
                when (gameUseCase.loadCurrentGame()) {
                    is GetCurrentGameResult.Success, GetCurrentGameResult.NotFound -> Unit
                    GetCurrentGameResult.NetworkError -> uiState = uiState.copy(
                        errorMessage = "Network error loading current game.",
                    )
                    GetCurrentGameResult.ServerError -> uiState = uiState.copy(
                        errorMessage = "Server error loading current game.",
                    )
                }
            }
        }
    }

    fun onCreateGameClick() {
        uiState = uiState.copy(isCreatingGame = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = gameUseCase.createGame()) {
                is CreateGameResult.Success -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        hasCurrentGame = true,
                        currentGameId = result.game.gameId,
                    )
                }
                is CreateGameResult.NoLocationAvailable -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "No location available. Please check GPS.",
                    )
                }
                is CreateGameResult.SegmentsLoadError -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "Game created, but map failed to load.",
                    )
                }
                is CreateGameResult.NetworkError -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "Network error while creating game.",
                    )
                }
                is CreateGameResult.ServerError -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "Server error while creating game.",
                    )
                }
            }
        }
    }

    fun onStartGameClick(onSuccess: () -> Unit) {
        uiState = uiState.copy(isCreatingGame = true, errorMessage = null)

        viewModelScope.launch {
            when (gameUseCase.startGame()) {
                is StartGameResult.Success -> {
                    uiState = uiState.copy(isCreatingGame = false)
                    onSuccess()
                }
                is StartGameResult.NoGameIdAvailable -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "No game available. Please create a game first.",
                    )
                }
                is StartGameResult.NetworkError -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "Network error starting game.",
                    )
                }
                is StartGameResult.ServerError -> {
                    uiState = uiState.copy(
                        isCreatingGame = false,
                        errorMessage = "Server error starting game.",
                    )
                }
            }
        }
    }
}
