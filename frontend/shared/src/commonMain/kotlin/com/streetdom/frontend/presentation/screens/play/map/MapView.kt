package com.streetdom.frontend.presentation.screens.play.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.streetdom.frontend.domain.model.GpsPosition
import com.streetdom.frontend.domain.model.StreetSegment
import com.streetdom.frontend.domain.model.StreetSegmentState
import kotlinx.serialization.json.JsonObject
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.LineLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.LineString
import org.maplibre.spatialk.geojson.Position
import org.maplibre.spatialk.geojson.dsl.addFeature
import org.maplibre.spatialk.geojson.dsl.buildFeatureCollection

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    gpsPosition: GpsPosition?,
    segments: List<StreetSegment>,
    mapConfig: MapConfig
) {
    val cameraState = rememberCameraState()

    LaunchedEffect(gpsPosition) {
        gpsPosition?.let {
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

    val cleanFeatures = remember(segments) {
        val cleanSegments = segments.filter { it.state == StreetSegmentState.CLEAN }
        buildFeatureCollection<LineString, JsonObject?> {
            cleanSegments.forEach { segment ->
                if (segment.geometry.size >= 2) {
                    val positions = segment.geometry.map { Position(it.longitude, it.latitude) }
                    addFeature(LineString(positions))
                }
            }
        }
    }

    val infectedFeatures = remember(segments) {
        val infectedSegments = segments.filter { it.state == StreetSegmentState.INFECTED }
        buildFeatureCollection<LineString, JsonObject?> {
            infectedSegments.forEach { segment ->
                if (segment.geometry.size >= 2) {
                    val positions = segment.geometry.map { Position(it.longitude, it.latitude) }
                    addFeature(LineString(positions))
                }
            }
        }
    }

    MaplibreMap(
        modifier = modifier,
        cameraState = cameraState,
        baseStyle = BaseStyle.Uri(
            "https://api.maptiler.com/maps/streets-v4/style.json?key=${mapConfig.mapTilerApiKey}"
        )
    ) {
        if (cleanFeatures.isNotEmpty()) {
            val cleanSource = rememberGeoJsonSource(data = GeoJsonData.Features(cleanFeatures))
            LineLayer(
                id = "clean-streets-layer",
                source = cleanSource,
                color = const(Color(0xFF00E676)),
                width = const(6.dp)
            )
        }

        if (infectedFeatures.isNotEmpty()) {
            val infectedSource = rememberGeoJsonSource(data = GeoJsonData.Features(infectedFeatures))
            LineLayer(
                id = "infected-streets-layer",
                source = infectedSource,
                color = const(Color(0xFFFF1744)),
                width = const(6.dp)
            )
        }
    }
}
