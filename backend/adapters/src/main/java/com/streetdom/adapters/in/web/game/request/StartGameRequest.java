package com.streetdom.adapters.in.web.game.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StartGameRequest(
        @NotNull UUID gameId
) {}
