package com.streetdom.adapters.in.web.game.controller;

import com.streetdom.adapters.in.web.game.mapper.GameWebMapper;
import com.streetdom.adapters.in.web.game.request.CreateGameRequest;
import com.streetdom.adapters.in.web.game.request.StartGameRequest;
import com.streetdom.adapters.in.web.game.request.FinishGameRequest;
import com.streetdom.adapters.in.web.game.response.FinishGameResponse;
import com.streetdom.application.game.command.FinishGameCommand;
import com.streetdom.application.game.result.FinishGameResult;
import com.streetdom.adapters.in.web.game.response.CreateGameResponse;
import com.streetdom.adapters.in.web.game.response.StartGameResponse;
import com.streetdom.adapters.in.web.game.response.GameSegmentsResponse;
import com.streetdom.adapters.in.web.game.response.CurrentGameResponse;
import com.streetdom.application.game.query.GetCurrentGameQuery;
import com.streetdom.application.game.query.GetGameSegmentsQuery;
import com.streetdom.application.game.result.GameSegmentsResult;
import com.streetdom.application.game.usecases.GameSegmentUseCase;
import com.streetdom.adapters.in.web.security.SecurityUser;
import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.result.CreateGameResult;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.application.game.usecases.GameUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameUseCase gameUseCase;
    private final GameSegmentUseCase gameSegmentUseCase;
    private final GameWebMapper mapper;

    @GetMapping("/current")
    public ResponseEntity<CurrentGameResponse> getCurrentGame(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        return gameUseCase.getCurrentGame(new GetCurrentGameQuery(securityUser.getId()))
                .map(result -> ResponseEntity.ok(mapper.toResponse(result)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{gameId}/segments")
    public ResponseEntity<GameSegmentsResponse> getGameSegments(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        GameSegmentsResult result = gameSegmentUseCase.getGameSegments(new GetGameSegmentsQuery(securityUser.getId(), gameId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }


    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame(
            @Valid @RequestBody CreateGameRequest request,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {

        CreateGameCommand command = mapper.toCommand(request, securityUser.getId());
        CreateGameResult result = gameUseCase.createGame(command);
        CreateGameResponse response = new CreateGameResponse(result.gameId(),result.state());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/start")
    public ResponseEntity<StartGameResponse> startGame(
            @Valid @RequestBody StartGameRequest request,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        StartGameCommand command = mapper.toCommand(request, securityUser.getId());
        StartGameResult result = gameUseCase.startGame(command);
        StartGameResponse response = mapper.toResponse(result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/finish")
    public ResponseEntity<FinishGameResponse> finishGame(
            @Valid @RequestBody FinishGameRequest request,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        FinishGameCommand command = mapper.toCommand(request, securityUser.getId());
        FinishGameResult result = gameUseCase.finishGame(command);
        return ResponseEntity.ok(mapper.toResponse(result));
    }

}
