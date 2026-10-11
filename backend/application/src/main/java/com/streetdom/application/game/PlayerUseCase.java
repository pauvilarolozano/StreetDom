package com.streetdom.application.game;

import com.streetdom.application.game.command.AddPlayerToGameCommand;
import com.streetdom.application.game.result.PlayerResult;

public interface PlayerUseCase {
    PlayerResult joinGame(AddPlayerToGameCommand command);
}
