package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.data.dto.AuthResponse
import com.streetdom.frontend.domain.model.AuthSession
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.result.AuthResult

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): AuthResult
    suspend fun register(credentials: RegisterCredentials): AuthResult
    suspend fun refresh(refreshToken: String): AuthResult
}