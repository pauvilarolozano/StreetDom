package com.streetdom.adapters.in.web.mapper;

import com.streetdom.adapters.in.web.request.GpsPositionRequest;
import com.streetdom.adapters.in.web.request.MovementUpdateRequest;
import com.streetdom.application.command.ProcessMovementCommand;
import com.streetdom.domain.model.geography.GpsPosition;
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
                request.latitude(),
                request.longitude(),
                request.recordedAt(),
                request.accuracyMeters()
        );
    }
}