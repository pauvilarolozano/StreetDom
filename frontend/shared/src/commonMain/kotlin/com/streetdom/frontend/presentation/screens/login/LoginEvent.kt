package com.streetdom.frontend.presentation.screens.login

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
}