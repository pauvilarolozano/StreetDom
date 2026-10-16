package com.streetdom.application.game.usecases;

import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.result.CreateGameResult;
import com.streetdom.application.game.result.StartGameResult;

public interface GameUseCase {
    CreateGameResult createGame(CreateGameCommand command);
    StartGameResult startGame(StartGameCommand command);
}
