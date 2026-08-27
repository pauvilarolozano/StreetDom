package com.streetdom.frontend.data.repository

import com.streetdom.frontend.domain.storage.SecureStorage
import com.streetdom.frontend.domain.model.Tokens
import com.streetdom.frontend.domain.storage.TokensStorage

class TokensStorageImpl(
    private val secureStorage: SecureStorage
): TokensStorage {

    private companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    override suspend fun saveTokens(tokens: Tokens) {
        secureStorage.putString(ACCESS_TOKEN_KEY, tokens.accessToken)
        secureStorage.putString(REFRESH_TOKEN_KEY, tokens.refreshToken)
    }

    override suspend fun getAccessToken(): String? {
        return secureStorage.getString(ACCESS_TOKEN_KEY)
    }

    override suspend fun getRefreshToken(): String? {
        return secureStorage.getString(REFRESH_TOKEN_KEY)
    }

    override suspend fun clear() {
        secureStorage.remove(ACCESS_TOKEN_KEY)
        secureStorage.remove(REFRESH_TOKEN_KEY)
    }

}