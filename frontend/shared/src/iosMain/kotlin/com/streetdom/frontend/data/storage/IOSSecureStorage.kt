package com.streetdom.frontend.data.storage

import com.streetdom.frontend.domain.storage.SecureStorage

class IOSSecureStorage : SecureStorage {
    override suspend fun putString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getString(key: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun remove(key: String) {
        TODO("Not yet implemented")
    }
}