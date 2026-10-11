package com.streetdom.adapters.in.web.interaction;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import com.streetdom.application.interaction.ProcessMovementCommand;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.geography.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProcessMovementWebMapper {

    public ProcessMovementCommand toCommand(
            MovementUpdateRequest request,
            UUID userId
    ) {
        List<GpsPosition> positions = request.positions()
                .stream()
                .map(this::toGpsPosition)
                .toList();

        return new ProcessMovementCommand(userId, positions);
    }

    private GpsPosition toGpsPosition(GpsPositionRequest request) {
        return new GpsPosition(
                new Location(
                        request.latitude(),
                        request.longitude()
                ),
                request.recordedAt(),
                request.accuracyMeters()
        );
    }
}