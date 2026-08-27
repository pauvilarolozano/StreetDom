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
@Table(name = "zones")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ZoneEntity {

    @Id
    private UUID id;

    @Column(name = "h3_index", nullable = false, unique = true)
    private String h3Index;

}
