package com.streetdom.adapters.out.valhalla;

import com.streetdom.adapters.out.valhalla.model.Coordinate;
import com.wdtinc.mapbox_vector_tile.VectorTile.Tile.Feature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValhallaTileGeometryDecoder {

    private static final int COMMAND_BITS = 3;
    private static final int MOVE_TO = 1;
    private static final int LINE_TO = 2;

    public List<Coordinate> decode(Feature feature, int zoom, int tileX, int tileY, int extent) {

        List<TilePoint> tilePoints = decodeTilePoints(feature);
        List<Coordinate> coordinates = new ArrayList<>();

        for (TilePoint point : tilePoints) {
            coordinates.add(toLatLon(point.x(), point.y(), zoom, tileX, tileY, extent));
        }

        return coordinates;
    }

    private List<TilePoint> decodeTilePoints(Feature feature) {

        List<Integer> geometry = feature.getGeometryList();
        List<TilePoint> points = new ArrayList<>();

        int x = 0;
        int y = 0;
        int index = 0;

        while (index < geometry.size()) {

            int commandInteger = geometry.get(index++);
            int command = commandInteger & 0x7;
            int commandCount = commandInteger >> COMMAND_BITS;

            if (command != MOVE_TO && command != LINE_TO) {
                throw new IllegalArgumentException("Unsupported MVT command: " + command);
            }

            for (int i = 0; i < commandCount; i++) {
                int deltaX = decodeZigZag(geometry.get(index++));
                int deltaY = decodeZigZag(geometry.get(index++));

                x += deltaX;
                y += deltaY;

                points.add(new TilePoint(x, y));
            }
        }

        return points;
    }

    public Coordinate toLatLon(double localX, double localY, int zoom, int tileX, int tileY, int extent) {

        double tilesPerAxis = Math.pow(2, zoom);
        double globalX = tileX + localX / extent;
        double globalY = tileY + localY / extent;

        double longitude = globalX / tilesPerAxis * 360.0 - 180.0;
        double mercatorY = Math.PI - 2.0 * Math.PI * globalY / tilesPerAxis;
        double latitude = Math.toDegrees(Math.atan(Math.sinh(mercatorY)));

        return new Coordinate(latitude, longitude);
    }

    private int decodeZigZag(int value) {
        return (value >>> 1) ^ -(value & 1);
    }

    private record TilePoint(
            int x,
            int y
    ) {}
}