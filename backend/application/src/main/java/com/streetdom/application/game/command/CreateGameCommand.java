package com.streetdom.application.game.command;

import java.util.UUID;

public record CreateGameCommand(
        UUID gameMapId
) {}
