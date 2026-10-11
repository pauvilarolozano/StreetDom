package com.streetdom.adapters.in.web.game;

import com.streetdom.adapters.in.web.game.request.CreateGameRequest;
import com.streetdom.adapters.in.web.game.response.CreateGameResponse;
import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.GameUseCase;
import com.streetdom.application.game.result.CreateGameResult;
import com.streetdom.application.game.result.StartGameResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameUseCase gameUseCase;

    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame(@Valid @RequestBody CreateGameRequest request) {

        CreateGameCommand command = new CreateGameCommand(request.gameMapId());
        CreateGameResult result = gameUseCase.createGame(command);
        CreateGameResponse response = new CreateGameResponse(result.gameId(),result.gameMapId(),result.state());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{gameId}/start")
    public ResponseEntity<String> StartGame(@PathVariable UUID gameId) {
        StartGameCommand command = new StartGameCommand(gameId);
        StartGameResult result = gameUseCase.startGame(command);
        //TODO: create response

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("TODO");
    }

}
