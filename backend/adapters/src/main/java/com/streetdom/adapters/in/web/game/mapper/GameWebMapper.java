package com.streetdom.adapters.in.web.game.mapper;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import com.streetdom.adapters.in.web.game.request.StartGameRequest;
import com.streetdom.adapters.in.web.game.response.StartGameResponse;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.geography.Location;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class GameWebMapper {

    private final PlayerWebMapper playerWebMapper;

    public StartGameResponse toResponse(StartGameResult result) {
        return new StartGameResponse(
                result.gameId(),
                result.gameMapId(),
                result.state(),
                result.startedAt(),
                playerWebMapper.toResponse(result.player())
        );
    }

    public StartGameCommand toCommand(StartGameRequest request, UUID userId) {

        GpsPositionRequest gps = request.gpsPosition();
        GpsPosition gpsPosition = new GpsPosition(
                new Location(
                        gps.latitude(),
                        gps.longitude()
                ),
                gps.recordedAt(),
                gps.accuracyMeters()
        );

        return new StartGameCommand(userId, gpsPosition);
    }
}
