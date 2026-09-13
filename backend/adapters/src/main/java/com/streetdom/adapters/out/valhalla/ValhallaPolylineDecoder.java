package com.streetdom.adapters.out.valhalla;

import com.streetdom.domain.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValhallaPolylineDecoder {

    private static final double PRECISION = 1e6;

    public List<Location> decode(String encodedShape) {

        List<Location> locations = new ArrayList<>();

        int index = 0;
        long latitude = 0;
        long longitude = 0;

        while (index < encodedShape.length()) {

            DecodedValue latValue = decodeValue(encodedShape, index);
            latitude += latValue.value();
            index = latValue.nextIndex();

            DecodedValue lonValue = decodeValue(encodedShape, index);
            longitude += lonValue.value();
            index = lonValue.nextIndex();

            locations.add(
                    new Location(
                            latitude / PRECISION,
                            longitude / PRECISION
                    )
            );
        }

        return locations;
    }

    private DecodedValue decodeValue(String encoded, int startIndex) {

        long result = 0;
        int shift = 0;
        int index = startIndex;
        int current;

        do {
            current = encoded.charAt(index++) - 63;
            result |= (long) (current & 0x1F) << shift;
            shift += 5;
        } while (current >= 0x20);

        long value = (result & 1) != 0
                ? ~(result >> 1)
                : result >> 1;

        return new DecodedValue(value, index);
    }

    private record DecodedValue(
            long value,
            int nextIndex
    ) {}
}
