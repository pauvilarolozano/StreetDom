package com.streetdom.frontend.domain.model

enum class PlayerRole {
    INFECTED,
    MEDIC,
    SURVIVOR,
    UNASSIGNED;

    companion object {
        fun fromString(value: String): PlayerRole {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: UNASSIGNED
        }
    }
}
