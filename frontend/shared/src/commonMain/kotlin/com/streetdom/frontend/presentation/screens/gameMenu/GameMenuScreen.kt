package com.streetdom.frontend.presentation.screens.gameMenu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameMenuScreen(
    onBack: () -> Unit,
    onStartGame: () -> Unit,
) {
    val viewModel: GameMenuViewModel = koinViewModel()
    val uiState = viewModel.uiState

    GameMenuContent(
        uiState = uiState,
        onBack = onBack,
        onCreateGame = viewModel::onCreateGameClick,
        onDeleteGame = viewModel::onDeleteGameClick,
        onStartGame = {
            viewModel.onStartGameClick(onStartGame)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameMenuContent(
    uiState: GameMenuUiState,
    onBack: () -> Unit,
    onCreateGame: () -> Unit,
    onDeleteGame: () -> Unit,
    onStartGame: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú de Juego") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("⬅️")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Partida Individual",
                style = MaterialTheme.typography.headlineMedium,
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text("🗺️", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "1. Generar Mapa y Partida",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    Text(
                        text = "Genera las calles y la partida a partir de tu posición GPS.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    if (uiState.hasCurrentGame) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.small,
                        ) {
                            Text(
                                text = "✅ Partida y mapa creados correctamente",
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }

                    if (uiState.errorMessage != null) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    if (uiState.hasCurrentGame) {
                        Button(
                            onClick = onDeleteGame,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isCreatingGame,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError,
                            ),
                        ) {
                            if (uiState.isCreatingGame) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.onError,
                                )
                            } else {
                                Text("Acabar Partida")
                            }
                        }
                    } else {
                        Button(
                            onClick = onCreateGame,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isCreatingGame,
                        ) {
                            if (uiState.isCreatingGame) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Text("Crear Partida y Mapa")
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text("🚀", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "2. Iniciar Partida",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    Text(
                        text = "Abre el mapa e inicia el recorrido de la ciudad.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Button(
                        onClick = onStartGame,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = uiState.hasCurrentGame && !uiState.isCreatingGame,
                    ) {
                        Text("Iniciar Partida")
                    }
                }
            }

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "⚙️ Configuración del Mapa",
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = "Próximamente: selección de radio, dificultad y modos de juego.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
