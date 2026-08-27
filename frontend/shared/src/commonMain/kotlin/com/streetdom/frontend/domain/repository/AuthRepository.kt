package com.streetdom.frontend.domain.repository

import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.result.GetProfileResult
import com.streetdom.frontend.domain.result.LoginResult
import com.streetdom.frontend.domain.result.RegisterResult

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): LoginResult
    suspend fun register(credentials: RegisterCredentials): RegisterResult
    suspend fun logout(refreshToken: String)
    suspend fun me(): GetProfileResult
}