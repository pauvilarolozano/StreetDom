package com.streetdom.frontend.presentation.screens.play

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.streetdom.frontend.domain.model.GpsPosition
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
    val gpsPosition by viewModel.gpsPosition.collectAsState()

    PlayContent(
        onBack = onBack,
        mapConfig = mapConfig,
        uiState = uiState,
        gpsPosition = gpsPosition,
        onReloadMap = viewModel::reloadMap,
        onToggleTracking = viewModel::onToggleTracking
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayContent(
    onBack: () -> Unit,
    mapConfig: MapConfig,
    uiState: PlayUiState,
    gpsPosition: GpsPosition?,
    onReloadMap: () -> Unit,
    onToggleTracking: () -> Unit
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
                gpsPosition = gpsPosition ?: uiState.currentGpsPosition,
                segments = uiState.currentGame?.segments.orEmpty(),
                mapConfig = mapConfig
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Button(
                onClick = onToggleTracking,
                enabled = uiState.currentGame != null || uiState.isTrackingActive,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isTrackingActive) {
                        MaterialTheme.colorScheme.error
                    } else {
                        Color(0xFF2E7D32)
                    },
                    contentColor = if (uiState.isTrackingActive) {
                        MaterialTheme.colorScheme.onError
                    } else {
                        Color.White
                    }
                )
            ) {
                Text(if (uiState.isTrackingActive) "Parar rastreo" else "Iniciar rastreo")
            }
        }
    }
}
