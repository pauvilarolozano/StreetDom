package com.streetdom.frontend.data.mapper

import com.streetdom.frontend.data.dto.UserResponse
import com.streetdom.frontend.domain.model.User

fun UserResponse.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        role = role
    )
}

