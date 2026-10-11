package com.streetdom.application.port.out.auth;

import com.streetdom.domain.model.auth.RefreshToken;

public record RefreshTokenBundle(
        RefreshToken domainToken,
        String rawTokenValue

) {}
