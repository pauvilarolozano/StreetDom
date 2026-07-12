package com.streetdom.application.result;

import com.streetdom.domain.model.User;

public record AuthResult (
        User user,
        TokensResult tokens
) {}
