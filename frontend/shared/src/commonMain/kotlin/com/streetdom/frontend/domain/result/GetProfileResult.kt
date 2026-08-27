package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.User

sealed interface GetProfileResult {
    data class Success(val user: User) : GetProfileResult
    data object SessionExpired : GetProfileResult
    data object NetworkError : GetProfileResult
    data object UnknownError : GetProfileResult
}