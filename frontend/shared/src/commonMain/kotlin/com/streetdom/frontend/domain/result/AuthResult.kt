package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.AuthSession

sealed interface AuthResult {

    data class Success(val authSession: AuthSession) : AuthResult

    data object InvalidCredentials : AuthResult
    data object UserAlreadyExists : AuthResult
    data object SessionExpired : AuthResult
    data object NetworkError : AuthResult
    data object UnknownError : AuthResult

}