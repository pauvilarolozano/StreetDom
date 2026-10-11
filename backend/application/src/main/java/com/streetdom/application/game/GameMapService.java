package com.streetdom.application.game;

import com.streetdom.application.game.command.CreateGameMapCommand;
import com.streetdom.application.game.result.CreateGameMapResult;
import com.streetdom.application.port.out.game.GameMapRepository;
import com.streetdom.domain.model.game.GameMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMapService implements GameMapUseCase {

    private final GameMapRepository gameMapRepository;

    @Override
    public CreateGameMapResult createGameMap(CreateGameMapCommand command) {

        GameMap newGameMap = GameMap.create(command.name());
        gameMapRepository.save(newGameMap);

        return new CreateGameMapResult(
                newGameMap.getId(),
                newGameMap.getName()
        );
    }
}
