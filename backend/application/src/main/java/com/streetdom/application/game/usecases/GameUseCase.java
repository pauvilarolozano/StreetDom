package com.streetdom.application.game.usecases;

import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.command.FinishGameCommand;
import com.streetdom.application.game.result.FinishGameResult;
import com.streetdom.application.game.result.CreateGameResult;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.application.game.query.GetCurrentGameQuery;
import com.streetdom.application.game.result.CurrentGameResult;

import java.util.Optional;

public interface GameUseCase {
    Optional<CurrentGameResult> getCurrentGame(GetCurrentGameQuery query);

    CreateGameResult createGame(CreateGameCommand command);
    StartGameResult startGame(StartGameCommand command);
    FinishGameResult finishGame(FinishGameCommand command);
}
