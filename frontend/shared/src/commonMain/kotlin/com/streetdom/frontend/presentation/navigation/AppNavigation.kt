package com.streetdom.frontend.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.streetdom.frontend.presentation.screens.home.HomeScreen
import com.streetdom.frontend.presentation.screens.login.LoginScreen
import com.streetdom.frontend.presentation.screens.play.PlayScreen
import com.streetdom.frontend.presentation.screens.register.RegisterScreen
import com.streetdom.frontend.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onAuthenticated = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                onUnauthenticated = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPlay = {
                    navController.navigate(Screen.Play.route)
                },
                onNavigateToRanking = {},
                onNavigateToInventory = {},
                onNavigateToProfile = {},
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Play.route) {
            PlayScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}