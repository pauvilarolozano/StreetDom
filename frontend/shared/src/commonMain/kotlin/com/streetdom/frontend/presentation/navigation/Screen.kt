package com.streetdom.frontend.presentation.navigation

sealed class Screen(val route: String) {

    data object Splash: Screen("splash")
    data object Login: Screen("login")
    data object Register: Screen("register")
    data object Home: Screen("home")
    data object Play: Screen("play")
}