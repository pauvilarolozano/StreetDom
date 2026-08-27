package com.streetdom.frontend.data.repository

import com.streetdom.frontend.data.request.RefreshTokenRequest
import com.streetdom.frontend.data.mapper.toDomain
import com.streetdom.frontend.data.mapper.toRequest
import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.domain.model.LoginCredentials
import com.streetdom.frontend.domain.model.RegisterCredentials
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.result.GetProfileResult
import com.streetdom.frontend.domain.result.LoginResult
import com.streetdom.frontend.domain.result.RegisterResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(credentials: LoginCredentials): LoginResult {

        return try {
            val response = authApi.login(credentials.toRequest())
            LoginResult.Success(response.toDomain())

        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized ->
                    LoginResult.InvalidCredentials

                else -> {
                    e.printStackTrace()
                    LoginResult.UnknownError
                }
            }
        } catch (_: IOException){
            LoginResult.NetworkError

        } catch (e: Exception) {
            e.printStackTrace()
            LoginResult.UnknownError

        }
    }

    override suspend fun register(credentials: RegisterCredentials): RegisterResult {

        return try {
            val response = authApi.register(credentials.toRequest())
            RegisterResult.Success(response.toDomain())

        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Conflict ->
                    RegisterResult.UserAlreadyExists

                else -> {
                    e.printStackTrace()
                    RegisterResult.UnknownError
                }
            }
        } catch (_: IOException){
            RegisterResult.NetworkError

        } catch (e: Exception) {
            e.printStackTrace()
            RegisterResult.UnknownError

        }
    }

    override suspend fun logout(refreshToken: String) {
        authApi.logout(RefreshTokenRequest(refreshToken))
    }

    override suspend fun me(): GetProfileResult {
        return try {
            val response = authApi.me()
            GetProfileResult.Success(response.toDomain())

        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Conflict ->
                    GetProfileResult.SessionExpired

                else -> {
                    e.printStackTrace()
                    GetProfileResult.UnknownError
                }
            }
        } catch (_: IOException){
            GetProfileResult.NetworkError

        } catch (e: Exception) {
            e.printStackTrace()
            GetProfileResult.UnknownError

        }
    }

}