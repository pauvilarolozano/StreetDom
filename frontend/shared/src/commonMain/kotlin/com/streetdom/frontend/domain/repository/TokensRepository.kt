package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.Tokens

interface TokensRepository {

    suspend fun saveTokens(tokens: Tokens)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clear()
}