package com.streetdom.frontend.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.useCase.AuthUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<SplashEvent>()
    val events = _events.asSharedFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val authenticationJob = async {
                authUseCase.authenticatedSession()
            }

            delay(1500.milliseconds)

            when (authenticationJob.await()) {
                true -> _events.emit(SplashEvent.Authenticated)
                false -> _events.emit(SplashEvent.Unauthenticated)
            }
        }
    }
}