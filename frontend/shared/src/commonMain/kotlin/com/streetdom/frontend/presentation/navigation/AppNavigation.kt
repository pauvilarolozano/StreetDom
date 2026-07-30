package com.streetdom.frontend.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.streetdom.frontend.presentation.screens.login.LoginScreen
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
                {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen({ navController.navigate(Screen.Register.route) })
        }

        composable(Screen.Register.route) {
            RegisterScreen( { navController.navigate(Screen.Login.route) })
        }
    }
}