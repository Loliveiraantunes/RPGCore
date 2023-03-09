package com.rpg.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlayerCheckin {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String playerUUID;

}
