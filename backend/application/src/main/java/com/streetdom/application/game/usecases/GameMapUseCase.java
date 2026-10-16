package com.streetdom.application.game.usecases;

import com.streetdom.application.game.command.CreateGameMapCommand;
import com.streetdom.application.game.result.CreateGameMapResult;

public interface GameMapUseCase {
    CreateGameMapResult createGameMap(CreateGameMapCommand command);
}
