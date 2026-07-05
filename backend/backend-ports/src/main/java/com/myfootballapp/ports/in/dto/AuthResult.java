package com.myfootballapp.ports.in.dto;

import com.myfootballapp.domain.model.User;

public record AuthResult (
        User user,
        TokensResult tokens
) {}
