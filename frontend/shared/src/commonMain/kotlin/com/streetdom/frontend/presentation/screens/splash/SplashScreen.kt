package com.streetdom.frontend.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    val viewModel: SplashViewModel = koinViewModel()

    HandleSplashEvents(viewModel, onAuthenticated, onUnauthenticated)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("StreetDom")
    }
}

@Composable
private fun HandleSplashEvents(
    viewModel: SplashViewModel,
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                SplashEvent.Authenticated -> onAuthenticated()
                SplashEvent.Unauthenticated -> onUnauthenticated()
            }
        }
    }
}
