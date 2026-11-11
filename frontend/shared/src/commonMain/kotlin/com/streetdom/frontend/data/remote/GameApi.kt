package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.request.CreateGameRequest
import com.streetdom.frontend.data.request.FinishGameRequest
import com.streetdom.frontend.data.request.StartGameRequest
import com.streetdom.frontend.data.response.CreateGameResponse
import com.streetdom.frontend.data.response.CurrentGameResponse
import com.streetdom.frontend.data.response.FinishGameResponse
import com.streetdom.frontend.data.response.GameSegmentsResponse
import com.streetdom.frontend.data.response.StartGameResponse

interface GameApi {
    suspend fun getCurrentGame(): CurrentGameResponse?
    suspend fun createGame(request: CreateGameRequest): CreateGameResponse
    suspend fun startGame(request: StartGameRequest): StartGameResponse
    suspend fun finishGame(request: FinishGameRequest): FinishGameResponse
    suspend fun getGameSegments(gameId: String): GameSegmentsResponse
}
