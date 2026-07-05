package com.backend.ports.in.dto;

import com.backend.domain.model.User;

public record AuthResult (
        User user,
        TokensResult tokens
) {}
