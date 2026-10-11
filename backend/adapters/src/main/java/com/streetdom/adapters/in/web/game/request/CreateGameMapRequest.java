package com.streetdom.adapters.in.web.game.request;

import jakarta.validation.constraints.NotBlank;

public record CreateGameMapRequest(
        @NotBlank String name
) {}
