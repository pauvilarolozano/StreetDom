package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.AuthSession

sealed interface RegisterResult {
    data class Success(val authSession: AuthSession) : RegisterResult
    data object UserAlreadyExists : RegisterResult
    data object NetworkError : RegisterResult
    data object UnknownError : RegisterResult
}