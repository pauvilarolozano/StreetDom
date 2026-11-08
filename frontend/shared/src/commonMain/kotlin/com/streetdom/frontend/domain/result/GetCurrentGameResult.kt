package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.CurrentGame

sealed interface GetCurrentGameResult {
    data class Success(val game: CurrentGame) : GetCurrentGameResult
    data object NotFound : GetCurrentGameResult
    data object NetworkError : GetCurrentGameResult
    data object ServerError : GetCurrentGameResult
}
