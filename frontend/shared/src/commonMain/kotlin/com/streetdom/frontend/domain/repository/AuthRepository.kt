package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.data.dto.AuthResponse
import com.streetdom.frontend.domain.model.AuthSession
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): AuthSession
    suspend fun register(credentials: RegisterCredentials): AuthSession
}