package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.config.ApiConfig
import com.streetdom.frontend.data.response.AuthResponse
import com.streetdom.frontend.data.request.LoginRequest
import com.streetdom.frontend.data.request.RefreshTokenRequest
import com.streetdom.frontend.data.request.RegisterRequest
import com.streetdom.frontend.data.response.TokensResponse
import com.streetdom.frontend.data.response.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorAuthApi (
    private val httpClient: HttpClient,
) : AuthApi {

    override suspend fun login(request: LoginRequest): AuthResponse {
        val response = httpClient.post(ApiConfig.LOGIN_URL) {
            setBody(request)
        }

        return response.body()
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        val response = httpClient.post(ApiConfig.REGISTER_URL) {
            setBody(request)
        }

        return response.body()
    }


    override suspend fun logout(request: RefreshTokenRequest) {
        httpClient.post(ApiConfig.LOGOUT_URL) {
            setBody(request)
        }
    }

    override suspend fun refresh(request: RefreshTokenRequest): TokensResponse {
        val response = httpClient.post(ApiConfig.REFRESH_URL) {
            setBody(request)
        }

        return response.body()
    }


    override suspend fun me(): UserResponse {
        val response = httpClient.get(ApiConfig.ME_URL)
        return response.body()
    }
}