package com.rpg.repository.interfaces;

import org.bukkit.entity.Player;

public interface PlayerCheckinRepository {

    boolean getPlayerCheckin(Player player);

    void setPlayerCheckin(Player player);

}
