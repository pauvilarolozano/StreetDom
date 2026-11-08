package com.streetdom.frontend.domain.result

import com.streetdom.frontend.domain.model.CurrentGame

sealed interface StartGameResult {
    data class Success(val game: CurrentGame) : StartGameResult
    data object NoGameIdAvailable : StartGameResult
    data object NetworkError : StartGameResult
    data object ServerError : StartGameResult
}
