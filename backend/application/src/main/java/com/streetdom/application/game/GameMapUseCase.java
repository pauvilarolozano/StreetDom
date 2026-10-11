package com.streetdom.application.game;

import com.streetdom.application.game.command.CreateGameMapCommand;
import com.streetdom.application.game.result.CreateGameMapResult;

public interface GameMapUseCase {
    CreateGameMapResult createGameMap(CreateGameMapCommand command);
}
