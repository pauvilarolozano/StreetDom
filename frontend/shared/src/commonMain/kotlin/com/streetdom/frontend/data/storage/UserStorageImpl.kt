package com.streetdom.frontend.data.storage

import com.streetdom.frontend.domain.model.User
import com.streetdom.frontend.domain.storage.UserStorage
import com.streetdom.frontend.domain.storage.SecureStorage
import kotlinx.serialization.json.Json

class UserStorageImpl (
    private val secureStorage: SecureStorage,
    private val json: Json
) : UserStorage {

    private companion object {
        const val USER_KEY = "auth_user"
    }

    override suspend fun getCurrentUser(): User? {
        val jsonUser = secureStorage.getString(USER_KEY) ?: return null

        return json.decodeFromString<User>(jsonUser)
    }

    override suspend fun saveUser(user: User) {
        secureStorage.putString(
            USER_KEY,
            json.encodeToString(user)
        )
    }

    override suspend fun clear() {
        secureStorage.remove(USER_KEY)
    }
}