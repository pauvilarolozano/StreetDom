package com.streetdom.adapters.in.web.game.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FinishGameRequest(
        @NotNull UUID gameId
) {}
