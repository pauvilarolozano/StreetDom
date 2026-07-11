package com.backend.application.result;

import com.backend.domain.model.User;

public record AuthResult (
        User user,
        TokensResult tokens
) {}
