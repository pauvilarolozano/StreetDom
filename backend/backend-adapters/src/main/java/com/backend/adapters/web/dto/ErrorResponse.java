package com.backend.adapters.web.dto;

public record ErrorResponse(
        String code,
        String message
) {}
