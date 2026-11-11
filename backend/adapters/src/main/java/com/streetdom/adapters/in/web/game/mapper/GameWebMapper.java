package com.streetdom.adapters.in.web.game.mapper;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import com.streetdom.adapters.in.web.game.request.CreateGameRequest;
import com.streetdom.adapters.in.web.game.request.StartGameRequest;
import com.streetdom.adapters.in.web.game.request.FinishGameRequest;
import com.streetdom.adapters.in.web.game.response.FinishGameResponse;
import com.streetdom.application.game.command.FinishGameCommand;
import com.streetdom.application.game.result.FinishGameResult;
import com.streetdom.adapters.in.web.game.response.StartGameResponse;
import com.streetdom.adapters.in.web.game.response.GameSegmentsResponse;
import com.streetdom.adapters.in.web.game.response.GameSegmentResponse;
import com.streetdom.adapters.in.web.game.response.CurrentGameResponse;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.application.game.result.GameSegmentsResult;
import com.streetdom.application.game.result.GameSegmentResult;
import com.streetdom.application.game.result.CurrentGameResult;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.geography.Location;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameWebMapper {

    private final PlayerWebMapper playerWebMapper;

    public CurrentGameResponse toResponse(CurrentGameResult result) {
        return new CurrentGameResponse(
                result.gameId(),
                result.state(),
                result.createdAt(),
                result.startedAt(),
                result.finishedAt(),
                playerWebMapper.toResponse(result.player()),
                result.segments().stream().map(this::toResponse).toList()
        );
    }

    public GameSegmentsResponse toResponse(GameSegmentsResult result) {
        return new GameSegmentsResponse(
                result.gameId(),
                result.segments().stream().map(this::toResponse).toList()
        );
    }

    private GameSegmentResponse toResponse(GameSegmentResult segment) {
        return new GameSegmentResponse(
                segment.streetSegmentId(),
                segment.geometry().stream()
                        .map(location -> new GameSegmentResponse.Position(location.latitude(), location.longitude()))
                        .toList(),
                segment.state()
        );
    }

    public StartGameResponse toResponse(StartGameResult result) {
        return new StartGameResponse(
                result.gameId(),
                result.state(),
                result.startedAt(),
                playerWebMapper.toResponse(result.player())
        );
    }

    public FinishGameResponse toResponse(FinishGameResult result) {
        return new FinishGameResponse(result.gameId(), result.state(), result.finishedAt());
    }

    public FinishGameCommand toCommand(FinishGameRequest request, UUID userId) {
        return new FinishGameCommand(userId, request.gameId());
    }

    public StartGameCommand toCommand(StartGameRequest request, UUID userId) {
        return new StartGameCommand(userId, request.gameId());
    }

    public CreateGameCommand toCommand(CreateGameRequest request, UUID userId) {

        GpsPositionRequest gps = request.gpsPosition();
        GpsPosition gpsPosition = new GpsPosition(
                new Location(
                        gps.latitude(),
                        gps.longitude()
                ),
                gps.recordedAt(),
                gps.accuracyMeters()
        );

        return new CreateGameCommand(userId, gpsPosition);
    }
}
