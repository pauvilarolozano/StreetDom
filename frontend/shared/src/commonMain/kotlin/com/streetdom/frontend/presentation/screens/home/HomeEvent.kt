package com.streetdom.frontend.presentation.screens.home

sealed interface HomeEvent {
    data object LogoutSuccess : HomeEvent
}