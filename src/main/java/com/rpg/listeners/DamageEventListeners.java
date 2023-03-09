package com.rpg.listeners;

import com.rpg.service.MobKillService;
import com.rpg.service.PlayerDamageService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DamageEventListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTakeDamage(EntityDamageEvent event){
        PlayerDamageService.onPlayerTakeDamage(event.getDamage(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event){
        PlayerDamageService.onPlayerDie(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityKiller(EntityDeathEvent event){
        new MobKillService().mobKillEvent(event);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerHit(EntityDamageByEntityEvent event){
        PlayerDamageService.onPlayerHit(event);
        PlayerDamageService.showHealthBossBarWhenHit(event);
    }
}
