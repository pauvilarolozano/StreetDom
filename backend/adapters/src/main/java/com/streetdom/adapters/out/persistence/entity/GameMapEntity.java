package com.streetdom.adapters.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "game_map")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameMapEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;
}



















