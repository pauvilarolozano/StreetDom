package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.result.CreateGameResult
import com.streetdom.frontend.domain.result.FinishGameResult
import com.streetdom.frontend.domain.result.GetCurrentGameResult
import com.streetdom.frontend.domain.result.GetGameSegmentsResult
import com.streetdom.frontend.domain.result.StartGameResult
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    val currentGame: StateFlow<CurrentGame?>

    suspend fun createGame(gpsPosition: GpsPosition): CreateGameResult
    suspend fun startGame(gameId: String): StartGameResult
    suspend fun finishGame(gameId: String): FinishGameResult
    suspend fun loadCurrentGame(): GetCurrentGameResult
    fun getCurrentGame(): CurrentGame?
    suspend fun loadGameSegments(gameId: String): GetGameSegmentsResult
    fun clearCurrentGame()
}
