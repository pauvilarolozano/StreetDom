package com.streetdom.adapters.in.web.matchmaking;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import com.streetdom.application.matchmaking.command.JoinMatchmakingCommand;
import com.streetdom.application.matchmaking.result.JoinMatchmakingResult;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.geography.Location;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MatchmakingWebMapper {

    public JoinMatchmakingCommand toCommand(
            JoinMatchmakingRequest request,
            UUID userId
    ) {
        GpsPositionRequest gps = request.gpsPosition();

        GpsPosition gpsPosition = new GpsPosition(
                new Location(
                        gps.latitude(),
                        gps.longitude()
                ),
                gps.recordedAt(),
                gps.accuracyMeters()
        );

        return new JoinMatchmakingCommand(
                userId,
                gpsPosition
        );
    }

    public JoinMatchmakingResponse toResponse(JoinMatchmakingResult result) {
        return new JoinMatchmakingResponse(
                result.status(),
                result.gameId()
        );
    }
}