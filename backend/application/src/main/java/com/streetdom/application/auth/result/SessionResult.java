package com.streetdom.application.auth.result;

public record SessionResult(
        UserResult user,
        TokensResult tokens
) {}
