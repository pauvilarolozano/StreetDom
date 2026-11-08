package com.streetdom.frontend.data.config

object ApiConfig {
    //const val BASE_URL = "http://localhost:8080"
    const val BASE_URL = "http://10.0.2.2:8080"  //url en =emulator
    const val LOGIN_URL = "/auth/login"
    const val REGISTER_URL = "/auth/register"
    const val LOGOUT_URL = "/auth/logout"
    const val REFRESH_URL = "/auth/refresh"
    const val ME_URL = "/auth/me"
    const val CURRENT_GAME_URL = "/api/games/current"
    const val CREATE_GAME_URL = "/api/games"
    const val START_GAME_URL = "/api/games/start"

    fun getGameSegmentsUrl(gameId: String): String = "/api/games/$gameId/segments"

}