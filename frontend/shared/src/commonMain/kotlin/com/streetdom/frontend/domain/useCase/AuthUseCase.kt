package com.streetdom.frontend.domain.useCase

import com.streetdom.frontend.domain.model.AuthSession
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.repository.AuthRepository

class AuthUseCase (
    private val authRepository: AuthRepository
) {

    suspend fun login(credentials: LoginCredentials): AuthSession {
        return authRepository.login(credentials)

    }

    suspend fun register(credentials: RegisterCredentials): AuthSession {
        return authRepository.register(credentials)
    }
}
