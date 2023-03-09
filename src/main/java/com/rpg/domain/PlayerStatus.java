package com.rpg.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String playerUUID;
    private String playerName;
    private Double currentXp;
    private Double maxXp;
    private Integer level;
    private Double balance;
    private Integer core;
    private Double health;
    private Double maxHealth;
    private Double mana;
    private Double maxMana;

}
