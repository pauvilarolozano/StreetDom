package com.streetdom.frontend.presentation.screens.home

sealed interface HomeEvent {
    data object NavigateToPlay : HomeEvent
    data object LocationDisabled : HomeEvent
    data object LogoutSuccess : HomeEvent
}