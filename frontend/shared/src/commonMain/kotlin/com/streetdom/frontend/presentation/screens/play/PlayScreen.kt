package com.streetdom.frontend.presentation.screens.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.streetdom.frontend.presentation.screens.play.map.MapConfig
import com.streetdom.frontend.presentation.screens.play.map.MapView
import org.koin.compose.koinInject

@Composable
fun PlayScreen(
    onBack: () -> Unit) {

    val mapConfig: MapConfig = koinInject()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StreetDom - Jugando") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("⬅️")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MapView(Modifier.fillMaxSize(), mapConfig)
        }
    }
}
