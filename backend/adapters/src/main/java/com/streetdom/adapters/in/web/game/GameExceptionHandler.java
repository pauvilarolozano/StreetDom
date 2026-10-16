package com.streetdom.adapters.in.web.game;

import com.streetdom.adapters.in.web.common.ErrorResponse;
import com.streetdom.domain.exception.game.EmptyGameAreaException;
import com.streetdom.domain.exception.game.GameMapAlreadyAssignedException;
import com.streetdom.domain.exception.game.GameMapNotFoundException;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.exception.game.NotEnoughPlayersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GameExceptionHandler {

    @ExceptionHandler(EmptyGameAreaException.class)
    public ResponseEntity<ErrorResponse> handleEmptyGameArea(EmptyGameAreaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "EMPTY_GAME_AREA",
                        "There are no street segments available in the game area"
                ));
    }

    @ExceptionHandler(GameMapNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameMapNotFound(GameMapNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "GAME_MAP_NOT_FOUND",
                        "The requested game map was not found"
                ));
    }

    @ExceptionHandler(GameMapAlreadyAssignedException.class)
    public ResponseEntity<ErrorResponse> handleGameMapAlreadyAssigned(GameMapAlreadyAssignedException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "GAME_MAP_ALREADY_ASSIGNED",
                        "The requested game map is already assigned to another game"
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
