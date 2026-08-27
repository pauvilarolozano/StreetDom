package com.streetdom.frontend.data.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
)

