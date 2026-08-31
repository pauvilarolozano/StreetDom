package com.streetdom.frontend.presentation.screens.play.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.streetdom.frontend.domain.model.Location
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    location: Location?,
    mapConfig: MapConfig
) {

    val cameraState = rememberCameraState()


    LaunchedEffect(location) {
        location?.let {
            cameraState.animateTo(
                CameraPosition(
                    target = Position(
                        it.longitude,
                        it.latitude
                    ),
                    zoom = 18.0
                )
            )
        }
    }

    MaplibreMap(
        modifier = modifier,
        cameraState = cameraState,
        baseStyle = BaseStyle.Uri(
            "https://api.maptiler.com/maps/streets-v4/style.json?key=${mapConfig.mapTilerApiKey}"
        )
    )
}