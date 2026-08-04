package com.streetdom.frontend.domain.useCase

import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.repository.TokensRepository
import com.streetdom.frontend.domain.result.AuthResult

class AuthUseCase (
    private val authRepository: AuthRepository,
    private val tokensRepository: TokensRepository
) {

    suspend fun login(credentials: LoginCredentials): AuthResult {
        val result = authRepository.login(credentials)

        return when (result) {
            is AuthResult.Success -> {
                tokensRepository.saveTokens(result.authSession.tokens)
                result
            }

            else -> result
        }
    }

    suspend fun register(credentials: RegisterCredentials): AuthResult {
        val result = authRepository.register(credentials)

        return when (result) {
            is AuthResult.Success -> {
                tokensRepository.saveTokens(result.authSession.tokens)
                result
            }

            else -> result
        }
    }

}
