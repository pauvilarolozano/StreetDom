package com.streetdom.frontend.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun HomePreview() {
    HomeContent(
        uiState = HomeUiState(),
        onPlayClick = {},
        onRankingClick = {},
        onInventoryClick = {},
        onProfileClick = {},
        onLogoutClick = {}
    )
}