package com.streetdom.frontend.domain.storage

import com.streetdom.frontend.domain.model.User

interface UserStorage {
    suspend fun getCurrentUser(): User?
    suspend fun saveUser(user: User)
    suspend fun clear()
}