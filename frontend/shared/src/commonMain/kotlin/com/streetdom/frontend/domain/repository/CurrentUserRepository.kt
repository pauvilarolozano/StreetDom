package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.User

interface CurrentUserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun saveUser(user: User)
    suspend fun clear()
}