package com.rpg.repository.interfaces;

import com.rpg.domain.PlayerStatus;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public interface PlayerStatusRepository {

    PlayerStatus getPlayerStatus(Player player);

    PlayerStatus createPlayerStatus(Player player);

    void setExperience(Player player , LivingEntity entity);
}
