package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.response.AuthResponse
import com.streetdom.frontend.data.request.LoginRequest
import com.streetdom.frontend.data.request.RefreshTokenRequest
import com.streetdom.frontend.data.request.RegisterRequest
import com.streetdom.frontend.data.response.TokensResponse
import com.streetdom.frontend.data.response.UserResponse

interface AuthApi {

    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
    suspend fun logout(request: RefreshTokenRequest)
    suspend fun refresh(request: RefreshTokenRequest): TokensResponse
    suspend fun me(): UserResponse

}