package com.streetdom.application.matchmaking.result;

import java.util.UUID;

public record JoinMatchmakingResult(
        MatchmakingStatus status,
        UUID gameId
) {
}
