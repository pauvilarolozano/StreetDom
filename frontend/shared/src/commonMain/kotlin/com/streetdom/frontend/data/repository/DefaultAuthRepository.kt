package com.streetdom.frontend.data.repository

import com.streetdom.frontend.data.mapper.toDomain
import com.streetdom.frontend.data.mapper.toRequest
import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.domain.model.AuthSession
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.repository.AuthRepository

class DefaultAuthRepository(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(credentials: LoginCredentials): AuthSession {
        val response = authApi.login(credentials.toRequest())
        return response.toDomain()
    }

    override suspend fun register(credentials: RegisterCredentials): AuthSession {
        val response = authApi.register(credentials.toRequest())
        return response.toDomain()
    }

}