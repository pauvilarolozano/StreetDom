package com.streetdom.adapters.out.persistence.game;

import com.streetdom.domain.model.game.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public Player toDomain(PlayerEntity entity) {
        return Player.builder()
                .userId(entity.getPlayerId().getUserId())
                .gameId(entity.getPlayerId().getGameId())
                .role(entity.getRole())
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public PlayerEntity toEntity(Player player) {
        return PlayerEntity.builder()
                .playerId(new PlayerId(player.getUserId(), player.getGameId()))
                .role(player.getRole())
                .joinedAt(player.getJoinedAt())
                .build();
    }
}
