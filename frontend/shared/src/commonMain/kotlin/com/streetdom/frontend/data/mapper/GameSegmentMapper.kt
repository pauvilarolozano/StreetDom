package com.streetdom.frontend.data.mapper

import com.streetdom.frontend.data.response.CurrentGameResponse
import com.streetdom.frontend.data.response.StreetSegmentResponse
import com.streetdom.frontend.domain.model.CurrentGame
import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.model.Player
import com.streetdom.frontend.domain.model.PlayerRole
import com.streetdom.frontend.domain.model.StreetSegment
import com.streetdom.frontend.domain.model.StreetSegmentState

fun StreetSegmentResponse.toDomain(): StreetSegment = StreetSegment(
    streetSegmentId = streetSegmentId,
    state = StreetSegmentState.fromString(state),
    geometry = geometry.map { GpsPosition(latitude = it.latitude, longitude = it.longitude) }
)

fun CurrentGameResponse.toDomain(): CurrentGame = CurrentGame(
    gameId = gameId,
    state = state,
    segments = segments.map { it.toDomain() },
    player = player.let { p ->
        Player(
            userId = p.userId,
            gameId = p.gameId,
            role = PlayerRole.fromString(p.role),
            joinedAt = p.joinedAt
        )
    },
    createdAt = createdAt,
    startedAt = startedAt,
    finishedAt = finishedAt,
)
