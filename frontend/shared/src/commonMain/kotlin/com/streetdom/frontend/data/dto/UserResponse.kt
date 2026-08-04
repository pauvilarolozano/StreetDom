package com.streetdom.frontend.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
)

