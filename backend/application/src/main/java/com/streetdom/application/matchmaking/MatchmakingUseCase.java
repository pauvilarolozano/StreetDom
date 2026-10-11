package com.streetdom.application.matchmaking;

import com.streetdom.application.matchmaking.command.JoinMatchmakingCommand;
import com.streetdom.application.matchmaking.result.JoinMatchmakingResult;

public interface MatchmakingUseCase {
    JoinMatchmakingResult joinMatchmaking(JoinMatchmakingCommand command);
}
