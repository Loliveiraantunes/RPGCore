package com.rpg.repository.impl;

import com.rpg.repository.interfaces.PlayerCheckinRepository;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCheckinImpl implements PlayerCheckinRepository {

    private final ConfigPlayers configPlayers = ConfigPlayers.getInstance();
    private final Map<UUID, Boolean> playerCheckin = new HashMap<>();

    @Override
    public boolean getPlayerCheckin(Player player) {

        if(playerCheckin.get(player.getUniqueId()) == null || !playerCheckin.get(player.getUniqueId()) )
            playerCheckin.put(player.getUniqueId() , getCheckinConfiguration(player));

        return playerCheckin.get(player.getUniqueId());
    }

    @Override
    public void setPlayerCheckin(Player player) {
        configPlayers.getConfiguration(player).set("checkin",true);
        configPlayers.saveChanges();
    }

    private boolean getCheckinConfiguration(Player player){
        return configPlayers.getConfiguration(player).getBoolean("checkin");
    }

}
