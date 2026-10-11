package com.streetdom.adapters.in.web.game;

import com.streetdom.adapters.in.web.game.response.PlayerResponse;
import com.streetdom.application.game.result.PlayerResult;
import org.springframework.stereotype.Component;

@Component
public class PlayerWebMapper {

    public PlayerResponse toResponse(PlayerResult result) {
        return new PlayerResponse(
                result.userId(),
                result.gameId(),
                result.role(),
                result.joinedAt()
        );
    }


}
