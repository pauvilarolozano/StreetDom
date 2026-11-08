package com.streetdom.adapters.in.web.game;

import com.streetdom.adapters.in.web.common.ErrorResponse;
import com.streetdom.domain.exception.game.EmptyGameAreaException;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.exception.game.GameNotInCreatedStateException;
import com.streetdom.domain.exception.game.GameNotInProgressException;
import com.streetdom.domain.exception.game.NotEnoughPlayersException;
import com.streetdom.domain.exception.game.PlayerNotFoundException;
import com.streetdom.domain.exception.game.PlayerAlreadyInActiveGameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GameExceptionHandler {

    @ExceptionHandler(GameNotInCreatedStateException.class)
    public ResponseEntity<ErrorResponse> handleGameNotCreated(GameNotInCreatedStateException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "GAME_NOT_IN_CREATED_STATE",
                        "Only a game in CREATED state can be started"
                ));
    }

    @ExceptionHandler(GameNotInProgressException.class)
    public ResponseEntity<ErrorResponse> handleGameNotInProgress(GameNotInProgressException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "GAME_NOT_IN_PROGRESS",
                        "Movements can only be processed for a game in progress"
                ));
    }

    @ExceptionHandler(PlayerAlreadyInActiveGameException.class)
    public ResponseEntity<ErrorResponse> handlePlayerAlreadyInActiveGame(PlayerAlreadyInActiveGameException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "PLAYER_ALREADY_IN_ACTIVE_GAME",
                        "The user already participates in a game in progress"
                ));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlayerNotFound(PlayerNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "PLAYER_NOT_FOUND",
                        "The player was not found in the requested game"
                ));
    }

    @ExceptionHandler(EmptyGameAreaException.class)
    public ResponseEntity<ErrorResponse> handleEmptyGameArea(EmptyGameAreaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "EMPTY_GAME_AREA",
                        "There are no street segments available in the game area"
                ));
    }





    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleJoinGame(GameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "GAME_NOT_FOUND",
                        "The requested game was not found"
                ));
    }

    @ExceptionHandler(NotEnoughPlayersException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughPlayers(NotEnoughPlayersException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "NOT_ENOUGH_PLAYERS",
                        "There are not enough players to start the game"
                ));
    }

}
