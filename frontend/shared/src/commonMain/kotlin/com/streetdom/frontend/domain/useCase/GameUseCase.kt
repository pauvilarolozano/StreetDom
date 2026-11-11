package com.streetdom.frontend.domain.useCase

import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.repository.GameRepository
import com.streetdom.frontend.domain.repository.LocationRepository
import com.streetdom.frontend.domain.result.CreateGameResult
import com.streetdom.frontend.domain.result.FinishGameResult
import com.streetdom.frontend.domain.result.GetCurrentGameResult
import com.streetdom.frontend.domain.result.GetGameSegmentsResult
import com.streetdom.frontend.domain.result.StartGameResult

class GameUseCase(
    private val locationRepository: LocationRepository,
    private val gameRepository: GameRepository,
) {

    val currentGame = gameRepository.currentGame

    suspend fun createGame(): CreateGameResult {
        val currentLocation = locationRepository.getCurrentLocation()
            ?: return CreateGameResult.NoLocationAvailable

        val createResult = gameRepository.createGame(currentLocation)
        if (createResult !is CreateGameResult.Success) return createResult

        val segmentsResult = gameRepository.loadGameSegments(createResult.game.gameId)
        return if (segmentsResult is GetGameSegmentsResult.Success) {
            val updatedGame = gameRepository.getCurrentGame() ?: createResult.game
            CreateGameResult.Success(updatedGame)
        } else {
            CreateGameResult.SegmentsLoadError
        }
    }

    suspend fun startGame(): StartGameResult {
        val activeGame = gameRepository.getCurrentGame()
            ?: return StartGameResult.NoGameIdAvailable

        if (activeGame.state.equals("IN_PROGRESS", ignoreCase = true)) {
            return StartGameResult.Success(activeGame)
        }

        return gameRepository.startGame(activeGame.gameId)
    }

    suspend fun finishGame(): FinishGameResult {
        val activeGame = gameRepository.getCurrentGame()
            ?: return FinishGameResult.NoGameIdAvailable

        return gameRepository.finishGame(activeGame.gameId)
    }

    suspend fun loadCurrentGame(): GetCurrentGameResult = gameRepository.loadCurrentGame()

    fun getCurrentGame(): CurrentGame? {
        return gameRepository.getCurrentGame()
    }

    suspend fun loadGameSegments(gameId: String): GetGameSegmentsResult {
        return gameRepository.loadGameSegments(gameId)
    }

    fun clearCurrentGame() {
        gameRepository.clearCurrentGame()
    }
}
