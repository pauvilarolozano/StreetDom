package com.streetdom.frontend.presentation.screens.gameMenu

data class GameMenuUiState(
    val isCreatingGame: Boolean = false,
    val hasCurrentGame: Boolean = false,
    val currentGameId: String? = null,
    val errorMessage: String? = null,
)
