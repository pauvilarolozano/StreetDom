package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.CurrentGame

sealed interface CreateGameResult {
    data class Success(val game: CurrentGame) : CreateGameResult
    data object NoLocationAvailable : CreateGameResult
    data object SegmentsLoadError : CreateGameResult
    data object NetworkError : CreateGameResult
    data object ServerError : CreateGameResult
}
