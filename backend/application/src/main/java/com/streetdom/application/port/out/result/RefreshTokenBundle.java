package com.streetdom.application.port.out.result;

import com.streetdom.domain.model.identity.RefreshToken;

public record RefreshTokenBundle(
        RefreshToken domainToken,
        String rawTokenValue

) {}
