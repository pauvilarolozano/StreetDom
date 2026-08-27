package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.AuthSession

sealed interface LoginResult {
    data class Success(val authSession: AuthSession) : LoginResult
    data object InvalidCredentials : LoginResult
    data object NetworkError : LoginResult
    data object UnknownError : LoginResult
}