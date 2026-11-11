package com.streetdom.application.game.command;

import java.util.UUID;

public record FinishGameCommand(
        UUID userId,
        UUID gameId
) {}
