package com.rpg.listeners;

import com.rpg.service.PlayerJoinService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class JoinEventListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEven(PlayerJoinEvent event){
        PlayerJoinService.onPlayerJoinEven(event);
    }

}
