package com.rpg.listeners;

import com.rpg.service.ActionBarService;
import com.rpg.service.EntityHungerService;
import com.rpg.service.PlayerMoveService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ActionsEventListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRegainHealthEvent(EntityRegainHealthEvent event){
         ActionBarService.updateActionBar(event.getEntity());
         ActionBarService.onPlayerRegainHealth(event);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        PlayerMoveService.onPlayerMove(event.getPlayer());
        ActionBarService.updateActionBar(event.getPlayer());
    }

    @EventHandler
    public void onHungerAttackEvent(FoodLevelChangeEvent event){
        EntityHungerService.checkHungerLevel(event);
    }

    @EventHandler
    public void onFoodRegenerateEvent(PlayerItemConsumeEvent event){
        EntityHungerService.onFoodRegenerateEvent(event);
    }
}
