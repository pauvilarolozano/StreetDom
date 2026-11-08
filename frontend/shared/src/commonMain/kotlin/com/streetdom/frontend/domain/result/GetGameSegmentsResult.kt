package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.StreetSegment

sealed interface GetGameSegmentsResult {
    data class Success(val segments: List<StreetSegment>) : GetGameSegmentsResult
    data object NotFound : GetGameSegmentsResult
    data object NetworkError : GetGameSegmentsResult
    data object ServerError : GetGameSegmentsResult
}
