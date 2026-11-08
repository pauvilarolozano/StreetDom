package com.streetdom.frontend.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.session.AuthenticatedSessionInitializer
import com.streetdom.frontend.domain.useCase.AuthUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel(
    private val authUseCase: AuthUseCase,
    private val sessionInitializer: AuthenticatedSessionInitializer,
) : ViewModel() {

    private val _events = MutableSharedFlow<SplashEvent>()
    val events = _events.asSharedFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val authenticationJob = async {
                try {
                    authUseCase.authenticatedSession()
                } catch (_: Exception) {
                    false
                }
            }

            delay(1500.milliseconds)

            val isAuthenticated = try {
                authenticationJob.await()
            } catch (_: Exception) {
                false
            }

            if (isAuthenticated) {
                try {
                    sessionInitializer.initialize()
                } catch (_: Exception) {
                    // Ignore initialization errors so navigation proceeds safely
                }
                _events.emit(SplashEvent.Authenticated)
            } else {
                _events.emit(SplashEvent.Unauthenticated)
            }
        }
    }
}
