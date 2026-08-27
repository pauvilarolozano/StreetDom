package com.streetdom.application.port.in.result;

public record SessionResult(
                UserResult user,
                TokensResult tokens
) {}
