package com.backend.adapters.in.web.response;

public record ErrorResponse(
        String code,
        String message
) {}
