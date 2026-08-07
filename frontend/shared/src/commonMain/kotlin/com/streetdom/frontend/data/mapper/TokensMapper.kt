package com.streetdom.frontend.data.mapper

import com.streetdom.frontend.data.dto.TokensResponse
import com.streetdom.frontend.domain.model.Tokens

fun TokensResponse.toDomain(): Tokens {
    return Tokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}