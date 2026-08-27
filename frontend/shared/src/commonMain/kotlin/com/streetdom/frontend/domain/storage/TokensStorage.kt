package com.streetdom.frontend.domain.storage

import com.streetdom.frontend.domain.model.Tokens

interface TokensStorage {

    suspend fun saveTokens(tokens: Tokens)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clear()
}