package com.streetdom.application.game.command;

import java.util.UUID;

public record AddPlayerToGameCommand(
        UUID userId,
        UUID gameId
) {}
