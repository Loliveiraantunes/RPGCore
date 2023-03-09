package com.rpg.service;

import com.rpg.repository.impl.PlayerStatusImpl;
import com.rpg.repository.interfaces.PlayerStatusRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKillService {
    private final PlayerStatusRepository playerStatusRepository = new PlayerStatusImpl();

    public void mobKillEvent(EntityDeathEvent event) {

        if(!(event.getEntity().getKiller() instanceof Player))
            return;

        Player player = event.getEntity().getKiller();
        if(!PlayerCheckinService.getPlayerCheckin(player))
            return;
        playerStatusRepository.setExperience(player, event.getEntity());
    }

}
