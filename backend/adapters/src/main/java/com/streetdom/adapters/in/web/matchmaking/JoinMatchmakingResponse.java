package com.streetdom.adapters.in.web.matchmaking;

import com.streetdom.application.matchmaking.result.MatchmakingStatus;

import java.util.UUID;

public record JoinMatchmakingResponse(
        MatchmakingStatus status,
        UUID gameId
) {}
