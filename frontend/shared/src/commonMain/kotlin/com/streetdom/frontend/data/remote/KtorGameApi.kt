package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.config.ApiConfig
import com.streetdom.frontend.data.request.CreateGameRequest
import com.streetdom.frontend.data.request.StartGameRequest
import com.streetdom.frontend.data.response.CreateGameResponse
import com.streetdom.frontend.data.response.CurrentGameResponse
import com.streetdom.frontend.data.response.GameSegmentsResponse
import com.streetdom.frontend.data.response.StartGameResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class KtorGameApi(
    private val authenticatedClient: HttpClient,
) : GameApi {

    override suspend fun getCurrentGame(): CurrentGameResponse? {
        val response = authenticatedClient.get(ApiConfig.CURRENT_GAME_URL)
        return if (response.status == HttpStatusCode.NoContent) null else response.body()
    }

    override suspend fun createGame(request: CreateGameRequest): CreateGameResponse {
        val response = authenticatedClient.post(ApiConfig.CREATE_GAME_URL) {
            setBody(request)
        }
        return response.body()
    }

    override suspend fun startGame(request: StartGameRequest): StartGameResponse {
        val response = authenticatedClient.post(ApiConfig.START_GAME_URL) {
            setBody(request)
        }
        return response.body()
    }

    override suspend fun getGameSegments(gameId: String): GameSegmentsResponse {
        val response = authenticatedClient.get(ApiConfig.getGameSegmentsUrl(gameId))
        return response.body()
    }
}
