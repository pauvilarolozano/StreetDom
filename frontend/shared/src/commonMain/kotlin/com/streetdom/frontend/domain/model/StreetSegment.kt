package com.streetdom.frontend.domain.model

data class StreetSegment(
    val streetSegmentId: String,
    val state: StreetSegmentState,
    val geometry: List<Location>,
)
