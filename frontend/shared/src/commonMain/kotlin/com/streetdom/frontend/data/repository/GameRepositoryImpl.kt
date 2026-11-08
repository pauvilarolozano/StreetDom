package com.streetdom.frontend.data.repository

import com.streetdom.frontend.data.mapper.toDomain
import com.streetdom.frontend.data.remote.GameApi
import com.streetdom.frontend.data.request.CreateGameRequest
import com.streetdom.frontend.data.request.GpsPositionRequest
import com.streetdom.frontend.data.request.StartGameRequest
import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.model.Location
import com.streetdom.frontend.domain.model.Player
import com.streetdom.frontend.domain.model.PlayerRole
import com.streetdom.frontend.domain.repository.GameRepository
import com.streetdom.frontend.domain.result.CreateGameResult
import com.streetdom.frontend.domain.result.GetCurrentGameResult
import com.streetdom.frontend.domain.result.GetGameSegmentsResult
import com.streetdom.frontend.domain.result.StartGameResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameRepositoryImpl(
    private val gameApi: GameApi,
) : GameRepository {

    private val _currentGame = MutableStateFlow<CurrentGame?>(null)
    override val currentGame: StateFlow<CurrentGame?> = _currentGame.asStateFlow()

    override suspend fun createGame(location: Location): CreateGameResult {
        return try {
            val request = CreateGameRequest(
                gpsPosition = GpsPositionRequest(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    recordedAt = location.recordedAt ?: "",
                    accuracyMeters = location.accuracyMeters ?: 0.0,
                ),
            )

            val response = gameApi.createGame(request)

            val game = CurrentGame(
                gameId = response.gameId,
                state = response.state,
            )

            _currentGame.value = game
            CreateGameResult.Success(game)
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: ClientRequestException) {
            CreateGameResult.ServerError
        } catch (_: ServerResponseException) {
            CreateGameResult.ServerError
        } catch (_: Exception) {
            CreateGameResult.NetworkError
        }
    }

    override suspend fun startGame(gameId: String): StartGameResult {
        return try {
            val request = StartGameRequest(gameId = gameId)
            val response = gameApi.startGame(request)

            _currentGame.update { activeGame ->
                if (activeGame?.gameId == gameId) {
                    activeGame.copy(
                        state = response.state,
                        startedAt = response.startedAt ?: activeGame.startedAt,
                        player = response.player?.let { p ->
                            Player(
                                userId = p.userId,
                                gameId = p.gameId,
                                role = PlayerRole.fromString(p.role),
                                joinedAt = p.joinedAt,
                            )
                        } ?: activeGame.player,
                    )
                } else {
                    activeGame
                }
            }

            val updatedGame = _currentGame.value
            if (updatedGame != null) StartGameResult.Success(updatedGame) else StartGameResult.ServerError
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: ClientRequestException) {
            StartGameResult.ServerError
        } catch (_: ServerResponseException) {
            StartGameResult.ServerError
        } catch (_: Exception) {
            StartGameResult.NetworkError
        }
    }

    override suspend fun loadCurrentGame(): GetCurrentGameResult {
        return try {
            val game = gameApi.getCurrentGame()?.toDomain()
            _currentGame.value = game
            if (game == null) GetCurrentGameResult.NotFound else GetCurrentGameResult.Success(game)
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: ClientRequestException) {
            GetCurrentGameResult.ServerError
        } catch (_: ServerResponseException) {
            GetCurrentGameResult.ServerError
        } catch (_: Exception) {
            GetCurrentGameResult.NetworkError
        }
    }

    override fun getCurrentGame(): CurrentGame? {
        return _currentGame.value
    }

    override suspend fun loadGameSegments(gameId: String): GetGameSegmentsResult {
        return try {
            val response = gameApi.getGameSegments(gameId)
            val segments = response.segments.map { it.toDomain() }

            _currentGame.update { activeGame ->
                if (activeGame?.gameId == gameId) activeGame.copy(segments = segments) else activeGame
            }

            GetGameSegmentsResult.Success(segments)
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: ClientRequestException) {
            GetGameSegmentsResult.NotFound
        } catch (_: ServerResponseException) {
            GetGameSegmentsResult.ServerError
        } catch (_: Exception) {
            GetGameSegmentsResult.NetworkError
        }
    }

    override fun clearCurrentGame() {
        _currentGame.value = null
    }
}
