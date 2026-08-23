package com.streetdom.frontend.data.repository

import com.streetdom.frontend.data.dto.RefreshTokenRequest
import com.streetdom.frontend.data.mapper.toDomain
import com.streetdom.frontend.data.mapper.toRequest
import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.result.AuthResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(credentials: LoginCredentials): AuthResult {

        return try {
            val response = authApi.login(credentials.toRequest())
            AuthResult.Success(response.toDomain())

        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized ->
                    AuthResult.InvalidCredentials

                else -> {
                    e.printStackTrace()
                    AuthResult.UnknownError
                }
            }
        } catch (_: IOException){
            AuthResult.NetworkError

        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.UnknownError

        }
    }

    override suspend fun register(credentials: RegisterCredentials): AuthResult {

        return try {
            val response = authApi.register(credentials.toRequest())
            AuthResult.Success(response.toDomain())

        }catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Conflict ->
                    AuthResult.UserAlreadyExists

                else -> {
                    e.printStackTrace()
                    AuthResult.UnknownError
                }
            }
        } catch (_: IOException){
            AuthResult.NetworkError

        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.UnknownError

        }
    }

    override suspend fun refresh(refreshToken: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun logout(refreshToken: String) {
        authApi.logout(RefreshTokenRequest(refreshToken))
    }

}