package com.streetdom.frontend.presentation.screens.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.streetdom.frontend.domain.model.Location
import com.streetdom.frontend.presentation.screens.play.map.MapConfig
import com.streetdom.frontend.presentation.screens.play.map.MapView
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    onBack: () -> Unit
) {
    val viewModel: PlayViewModel = koinViewModel()
    val mapConfig: MapConfig = koinInject()
    val uiState = viewModel.uiState
    val location by viewModel.location.collectAsState()

    PlayContent(
        onBack = onBack,
        mapConfig = mapConfig,
        uiState = uiState,
        location = location,
        onReloadMap = viewModel::reloadMap
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayContent(
    onBack: () -> Unit,
    mapConfig: MapConfig,
    uiState: PlayUiState,
    location: Location?,
    onReloadMap: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StreetDom - Jugando") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("⬅️")
                    }
                },
                actions = {
                    IconButton(onClick = onReloadMap) {
                        Text("🔄")
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
            MapView(
                modifier = Modifier.fillMaxSize(),
                location = location ?: uiState.currentLocation,
                segments = uiState.currentGame?.segments.orEmpty(),
                mapConfig = mapConfig
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
