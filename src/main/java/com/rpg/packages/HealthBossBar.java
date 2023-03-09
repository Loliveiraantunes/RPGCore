package com.rpg.packages;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HealthBossBar {

    public static void openBossHealthBar(Player player, Entity target){

        if(target instanceof LivingEntity)
            player.sendMessage("Looking at "+ target.getName());
    }
}
