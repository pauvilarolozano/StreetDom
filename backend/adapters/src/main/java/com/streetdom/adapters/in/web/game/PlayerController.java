package com.streetdom.adapters.in.web.game;

import com.streetdom.adapters.in.web.game.response.PlayerResponse;
import com.streetdom.application.game.command.AddPlayerToGameCommand;
import com.streetdom.adapters.in.web.security.SecurityUser;
import com.streetdom.application.game.PlayerUseCase;
import com.streetdom.application.game.result.PlayerResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerUseCase playerUseCase;
    private final PlayerWebMapper playerWebMapper;

    @PostMapping("/{gameId}/players")
    public ResponseEntity<PlayerResponse> addPlayerToGame(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        AddPlayerToGameCommand command = new AddPlayerToGameCommand(securityUser.getId(), gameId);
        PlayerResult result = playerUseCase.joinGame(command);
        PlayerResponse response = playerWebMapper.toResponse(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
