package com.streetdom.frontend.domain.model

data class AuthSession(
    val user: User,
    val tokens: Tokens
)
