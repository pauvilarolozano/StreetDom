package com.streetdom.frontend.presentation.screens.play.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.style.BaseStyle

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    mapConfig: MapConfig
) {
    MaplibreMap(
        modifier = modifier,
        baseStyle = BaseStyle.Uri(
            "https://api.maptiler.com/maps/streets-v4/style.json?key=${mapConfig.mapTilerApiKey}"
        )
    )
}