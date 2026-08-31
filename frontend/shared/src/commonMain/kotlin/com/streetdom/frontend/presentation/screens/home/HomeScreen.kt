package com.streetdom.frontend.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.location.LOCATION
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToPlay: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogoutSuccess: () -> Unit = {}
) {
    val viewModel: HomeViewModel = koinViewModel()

    val permissionsController = rememberPermissionsController()
    val scope = rememberCoroutineScope()

    var showLocationAccessDialog by remember { mutableStateOf(false) }
    var showLocationBlockedDialog by remember { mutableStateOf(false) }
    var showLocationDisabledDialog by remember { mutableStateOf(false) }

    HandleHomeEvents(
        viewModel = viewModel,
        onNavigateToPlay = onNavigateToPlay,
        onLocationDisabled = { showLocationDisabledDialog = true },
        onLogoutSuccess = onLogoutSuccess
    )

    HomeContent(
        uiState = viewModel.uiState,
        onPlayClick = {
            scope.launch {
                handlePlayClick(
                    permissionsController = permissionsController,
                    onGranted = viewModel::onPlayClick,
                    onNotGranted = { showLocationAccessDialog = true },
                    onDeniedAlways = { showLocationBlockedDialog = true }
                )
            }
        },
        onRankingClick = onNavigateToRanking,
        onInventoryClick = onNavigateToInventory,
        onProfileClick = onNavigateToProfile,
        onLogoutClick = viewModel::onLogoutClick
    )

    LocationAccessInfoDialog(
        visible = showLocationAccessDialog,
        onDismiss = { showLocationAccessDialog = false },
        onContinue = {
            showLocationAccessDialog = false

            scope.launch {
                requestLocationPermission(
                    permissionsController = permissionsController,
                    onGranted = onNavigateToPlay,
                    onDeniedAlways = { showLocationBlockedDialog = true }
                )
            }
        }
    )

    LocationPermissionBlockedDialog(
        visible = showLocationBlockedDialog,
        onDismiss = { showLocationBlockedDialog = false },
        onOpenSettings = {
            showLocationBlockedDialog = false
            
            scope.launch {
                permissionsController.openAppSettings()
            }
        }
    )

    LocationDisabledDialog(
        visible = showLocationDisabledDialog,
        onDismiss = { showLocationDisabledDialog = false }
    )
}

@Composable
private fun rememberPermissionsController(): PermissionsController {
    val factory = rememberPermissionsControllerFactory()

    val permissionsController = remember(factory) {
        factory.createPermissionsController()
    }

    BindEffect(permissionsController)

    return permissionsController
}

private suspend fun handlePlayClick(
    permissionsController: PermissionsController,
    onGranted: () -> Unit,
    onNotGranted: () -> Unit,
    onDeniedAlways: () -> Unit
) {
    val status = permissionsController.getPermissionState(
        Permission.LOCATION
    )

    when (status) {
        PermissionState.Granted -> onGranted()
        PermissionState.DeniedAlways -> onDeniedAlways()
        else -> onNotGranted()
    }
}

private suspend fun requestLocationPermission(
    permissionsController: PermissionsController,
    onGranted: () -> Unit,
    onDeniedAlways: () -> Unit
) {
    try {
        permissionsController.providePermission(
            Permission.LOCATION
        )

        onGranted()

    } catch (_: DeniedAlwaysException) {
        onDeniedAlways()

    } catch (_: DeniedException) {
        // User denied the permission.
        // Stay on Home.
    }
}

@Composable
private fun LocationAccessInfoDialog(
    onDismiss: () -> Unit,
    visible: Boolean,
    onContinue: () -> Unit
) {
    if (!visible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Location Required")
        },
        text = {
            Text(
                "StreetDom needs your location to play and conquer the streets."
            )
        },
        confirmButton = {
            TextButton(onClick = onContinue) {
                Text("Continue")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun LocationPermissionBlockedDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    if (!visible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Permission Blocked")
        },
        text = {
            Text(
                "You have blocked location access. " +
                        "Please enable it in the application settings."
            )
        },
        confirmButton = {
            TextButton(onClick = onOpenSettings) {
                Text("Open Settings")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun LocationDisabledDialog(
    visible: Boolean,
    onDismiss: () -> Unit
) {
    if (!visible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Location Services Disabled")
        },
        text = {
            Text(
                "Your device's location services are turned off. " +
                        "Please enable GPS to continue playing."
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
private fun HandleHomeEvents(
    viewModel: HomeViewModel,
    onNavigateToPlay: () -> Unit,
    onLocationDisabled: () -> Unit,
    onLogoutSuccess: () -> Unit,

    ) {
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                HomeEvent.NavigateToPlay -> onNavigateToPlay()
                HomeEvent.LocationDisabled -> onLocationDisabled()
                HomeEvent.LogoutSuccess -> onLogoutSuccess()
            }
        }
    }
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onPlayClick: () -> Unit,
    onRankingClick: () -> Unit,
    onInventoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("StreetDom")
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Text("🚪")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "👤",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        uiState.currentUser?.username ?: "Unknown",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${uiState.coins}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "🪙",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onPlayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "🗺️",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        "PLAY",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onRankingClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "🏆",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text("Ranking")
                    }
                }

                OutlinedButton(
                    onClick = onInventoryClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "🎒",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text("Inventory")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onProfileClick) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "⚙️",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Profile / Settings")
                }
            }
        }
    }
}