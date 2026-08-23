package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.dto.AuthResponse
import com.streetdom.frontend.data.dto.LoginRequest
import com.streetdom.frontend.data.dto.RefreshTokenRequest
import com.streetdom.frontend.data.dto.RegisterRequest
import com.streetdom.frontend.data.dto.TokensResponse

interface AuthApi {

    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
    suspend fun refresh(request: RefreshTokenRequest): TokensResponse
    suspend fun logout(request: RefreshTokenRequest)

}