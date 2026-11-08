package com.streetdom.frontend.domain.session

import com.streetdom.frontend.domain.repository.GameRepository

class AuthenticatedSessionInitializer(
    private val gameRepository: GameRepository,
) {

    suspend fun initialize() {
        try {
            gameRepository.loadCurrentGame()
        } catch (_: Exception) {
            // Se ignoran los errores de carga inicial para que el arranque de la app nunca se quede bloqueado
        }
    }
}
