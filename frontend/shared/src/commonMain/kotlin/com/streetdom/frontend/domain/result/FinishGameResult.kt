package com.streetdom.frontend.domain.result

import com.streetdom.frontend.data.response.FinishGameResponse

sealed interface FinishGameResult {
    data class Success(val response: FinishGameResponse) : FinishGameResult
    data object NoGameIdAvailable : FinishGameResult
    data class GameCannotBeFinished(val message: String) : FinishGameResult
    data class PlayerNotFound(val message: String) : FinishGameResult
    data class GameNotFound(val message: String) : FinishGameResult
    data object NetworkError : FinishGameResult
    data object ServerError : FinishGameResult
}
