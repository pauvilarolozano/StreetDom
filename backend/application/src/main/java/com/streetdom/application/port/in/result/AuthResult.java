package com.streetdom.application.port.in.result;

public record AuthResult(
                UserResult user,
                TokensResult tokens) {
}
