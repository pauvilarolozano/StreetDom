package com.streetdom.frontend.data.config

import com.streetdom.frontend.data.request.RefreshTokenRequest
import com.streetdom.frontend.data.response.TokensResponse
import com.streetdom.frontend.domain.model.Tokens
import com.streetdom.frontend.domain.storage.TokensStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val tokenStorage: TokensStorage
) {

    fun createAuthenticated(): HttpClient {
        val refreshClient = createBaseClient()

        return HttpClient {
            expectSuccess = true

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url(ApiConfig.BASE_URL)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenStorage.getAccessToken()
                        val refreshToken = tokenStorage.getRefreshToken()

                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken
                            )
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        val refreshToken = tokenStorage.getRefreshToken()
                            ?: return@refreshTokens null

                        val response = refreshClient.post(
                            ApiConfig.REFRESH_URL
                        ) {
                            setBody(RefreshTokenRequest(refreshToken))
                        }

                        val tokensResponse = response.body<TokensResponse>()

                        val tokens = Tokens(
                            accessToken = tokensResponse.accessToken,
                            refreshToken = tokensResponse.refreshToken
                        )

                        tokenStorage.saveTokens(tokens)

                        BearerTokens(
                            accessToken = tokens.accessToken,
                            refreshToken = tokens.refreshToken
                        )
                    }
                }
            }
        }
    }

    fun createUnauthenticated(): HttpClient {
        return createBaseClient()
    }

    private fun createBaseClient(): HttpClient {
        return HttpClient {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url(ApiConfig.BASE_URL)
            }
        }
    }
}
