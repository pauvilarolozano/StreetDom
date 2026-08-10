package com.streetdom.frontend.data.repository

import com.streetdom.frontend.domain.model.User
import com.streetdom.frontend.domain.repository.UserRepository
import com.streetdom.frontend.domain.storage.SecureStorage
import kotlinx.serialization.json.Json

class CurrentUserRepositoryImpl (
    private val secureStorage: SecureStorage,
    private val json: Json
) : UserRepository {

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