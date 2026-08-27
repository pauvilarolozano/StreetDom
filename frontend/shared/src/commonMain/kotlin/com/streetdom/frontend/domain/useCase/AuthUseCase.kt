package com.streetdom.frontend.domain.useCase

import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.result.GetProfileResult
import com.streetdom.frontend.domain.result.LoginResult
import com.streetdom.frontend.domain.result.RegisterResult
import com.streetdom.frontend.domain.storage.TokensStorage
import com.streetdom.frontend.domain.storage.UserStorage

class AuthUseCase (
    private val authRepository: AuthRepository,
    private val tokensStorage: TokensStorage,
    private val userStorage: UserStorage
) {

    suspend fun login(credentials: LoginCredentials): LoginResult {
        return when (val result = authRepository.login(credentials)) {
            is LoginResult.Success -> {
                tokensStorage.saveTokens(result.authSession.tokens)
                userStorage.saveUser(result.authSession.user)
                result
            }

            else -> result
        }
    }

    suspend fun register(credentials: RegisterCredentials): RegisterResult {
        return when (val result = authRepository.register(credentials)) {
            is RegisterResult.Success -> {
                tokensStorage.saveTokens(result.authSession.tokens)
                userStorage.saveUser(result.authSession.user)
                result
            }

            else -> result
        }
    }

    suspend fun logout() {
        try {
            tokensStorage.getRefreshToken()?.let { refreshToken ->
                authRepository.logout(refreshToken)
            }
        } catch (_: Exception) {
            // We ignore errors: local logout must proceed regardless.
        } finally {
            tokensStorage.clear()
            userStorage.clear()
        }
    }

    suspend fun authenticatedSession(): Boolean {
        return when (val result = authRepository.me()) {
            is GetProfileResult.Success -> {
                userStorage.saveUser(result.user)
                true
            }

            else -> false
        }
    }

}
