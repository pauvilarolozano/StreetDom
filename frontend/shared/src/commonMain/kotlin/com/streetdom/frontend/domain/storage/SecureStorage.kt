package com.streetdom.frontend.domain.storage

import com.streetdom.frontend.domain.model.Tokens

interface SecureStorage {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun remove(key: String)
}