package com.streetdom.adapters.in.web.game.controller;

import com.streetdom.adapters.in.web.game.request.CreateGameMapRequest;
import com.streetdom.adapters.in.web.game.response.CreateGameMapResponse;
import com.streetdom.application.game.command.CreateGameMapCommand;
import com.streetdom.application.game.result.CreateGameMapResult;
import com.streetdom.application.game.usecases.GameMapUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-maps")
@RequiredArgsConstructor
public class GameMapController {

    private final GameMapUseCase gameMapUseCase;

    @PostMapping
    public ResponseEntity<CreateGameMapResponse> createGameMap(@Valid @RequestBody CreateGameMapRequest request) {

        CreateGameMapCommand command = new CreateGameMapCommand(request.name());
        CreateGameMapResult result = gameMapUseCase.createGameMap(command);
        CreateGameMapResponse response = new CreateGameMapResponse(result.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
