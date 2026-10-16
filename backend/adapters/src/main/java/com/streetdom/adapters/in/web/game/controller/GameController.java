package com.streetdom.adapters.in.web.game.controller;

import com.streetdom.adapters.in.web.game.mapper.GameWebMapper;
import com.streetdom.adapters.in.web.game.request.CreateGameRequest;
import com.streetdom.adapters.in.web.game.request.StartGameRequest;
import com.streetdom.adapters.in.web.game.response.CreateGameResponse;
import com.streetdom.adapters.in.web.game.response.StartGameResponse;
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

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameUseCase gameUseCase;
    private final GameWebMapper mapper;


    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame(@Valid @RequestBody CreateGameRequest request) {

        CreateGameCommand command = new CreateGameCommand(request.gameMapId());
        CreateGameResult result = gameUseCase.createGame(command);
        CreateGameResponse response = new CreateGameResponse(result.gameId(),result.gameMapId(),result.state());

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
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
