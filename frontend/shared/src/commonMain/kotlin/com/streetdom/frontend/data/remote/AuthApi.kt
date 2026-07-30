package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.dto.AuthResponse
import com.streetdom.frontend.data.dto.LoginRequest
import com.streetdom.frontend.data.dto.RegisterRequest

interface AuthApi {

    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse

}