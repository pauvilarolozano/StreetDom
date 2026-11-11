package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String? = null,
    val message: String? = null,
)
