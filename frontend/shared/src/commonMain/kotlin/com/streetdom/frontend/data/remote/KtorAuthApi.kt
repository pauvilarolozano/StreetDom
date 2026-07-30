package com.streetdom.frontend.data.remote

import com.streetdom.frontend.data.config.ApiConfig
import com.streetdom.frontend.data.dto.AuthResponse
import com.streetdom.frontend.data.dto.LoginRequest
import com.streetdom.frontend.data.dto.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

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
}