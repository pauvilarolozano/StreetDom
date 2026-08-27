package com.streetdom.frontend.presentation.screens.splash

sealed interface SplashEvent {
    data object Authenticated : SplashEvent
    data object Unauthenticated : SplashEvent
}