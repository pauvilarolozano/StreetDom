package com.streetdom.frontend.presentation.screens.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.streetdom.frontend.presentation.screens.play.map.MapConfig
import com.streetdom.frontend.presentation.screens.play.map.MapView
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    onBack: () -> Unit
) {
    val mapConfig: MapConfig = koinInject()

    // Configuración de permisos encapsulada
    LocationPermissionSetup()

    PlayContent(
        onBack = onBack,
        mapConfig = mapConfig
    )
}

@Composable
private fun LocationPermissionSetup() {
    val factory = rememberPermissionsControllerFactory()
    val permissionsController = remember(factory) {
        factory.createPermissionsController()
    }
    BindEffect(permissionsController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayContent(
    onBack: () -> Unit,
    mapConfig: MapConfig
) {
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
