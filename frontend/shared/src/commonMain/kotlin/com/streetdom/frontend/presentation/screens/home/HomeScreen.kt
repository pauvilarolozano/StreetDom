package com.streetdom.frontend.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToRanking: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogoutSuccess: () -> Unit = {}
) {
    val viewModel: HomeViewModel = koinViewModel()
    
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                HomeEvent.LogoutSuccess -> onLogoutSuccess()
            }
        }
    }
    
    HomeContent(
        uiState = viewModel.uiState,
        onPlayClick = viewModel::onPlayClick,
        onRankingClick = onNavigateToRanking,
        onInventoryClick = onNavigateToInventory,
        onProfileClick = onNavigateToProfile,
        onLogoutClick = viewModel::onLogoutClick
    )
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
                title = { Text("StreetDom") },
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("👤", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(uiState.currentUser?.username ?: "Unknown", style = MaterialTheme.typography.titleMedium)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${uiState.coins}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("🪙", style = MaterialTheme.typography.titleMedium)
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🗺️", style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("JUGAR / PLAY", style = MaterialTheme.typography.headlineMedium)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onRankingClick,
                    modifier = Modifier.weight(1f).height(80.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🏆", style = MaterialTheme.typography.titleLarge)
                        Text("Ranking")
                    }
                }

                OutlinedButton(
                    onClick = onInventoryClick,
                    modifier = Modifier.weight(1f).height(80.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎒", style = MaterialTheme.typography.titleLarge)
                        Text("Inventario")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Perfil / Ajustes rápido
            TextButton(onClick = onProfileClick) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚙️", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Perfil / Ajustes")
                }
            }
        }
    }
}