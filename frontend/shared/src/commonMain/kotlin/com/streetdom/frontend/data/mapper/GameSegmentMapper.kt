package com.streetdom.frontend.data.mapper

import com.streetdom.frontend.data.response.CurrentGameResponse
import com.streetdom.frontend.data.response.StreetSegmentResponse
import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.model.Location
import com.streetdom.frontend.domain.model.Player
import com.streetdom.frontend.domain.model.PlayerRole
import com.streetdom.frontend.domain.model.StreetSegment
import com.streetdom.frontend.domain.model.StreetSegmentState

fun StreetSegmentResponse.toDomain(): StreetSegment = StreetSegment(
    streetSegmentId = streetSegmentId,
    state = StreetSegmentState.fromString(state),
    geometry = geometry.map { Location(latitude = it.latitude, longitude = it.longitude) },
)

fun CurrentGameResponse.toDomain(): CurrentGame = CurrentGame(
    gameId = gameId,
    state = state,
    segments = segments.map { it.toDomain() },
    player = Player(
        userId = player.userId,
        gameId = player.gameId,
        role = PlayerRole.fromString(player.role),
        joinedAt = player.joinedAt,
    ),
    createdAt = createdAt,
    startedAt = startedAt,
    finishedAt = finishedAt,
)
