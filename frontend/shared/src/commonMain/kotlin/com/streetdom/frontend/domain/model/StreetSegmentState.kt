package com.streetdom.frontend.domain.model

enum class StreetSegmentState {
    CLEAN,
    INFECTED;

    companion object {
        fun fromString(value: String): StreetSegmentState {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: CLEAN
        }
    }
}
