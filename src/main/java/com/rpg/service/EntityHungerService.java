package com.rpg.service;

import com.rpg.RPGCore;
import com.rpg.util.config.ConfigRPG;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class EntityHungerService {

    private static final ConfigRPG configRPG = RPGCore.getConfigRPG();
    private static final boolean HUNGRY_DISABLED =  Objects.requireNonNull(configRPG.getConfig().getConfigurationSection("Config")).getBoolean("HUNGRY_DISABLED");
    private static final boolean FOOD_REGENERATION =  Objects.requireNonNull(configRPG.getConfig().getConfigurationSection("Config")).getBoolean("FOOD_REGENERATION");
    private static final int FOOD_REGENERATION_TIME =  Objects.requireNonNull(configRPG.getConfig().getConfigurationSection("Config")).getInt("FOOD_REGENERATION_TIME");
    public static void checkHungerLevel(FoodLevelChangeEvent event){

        if(!PlayerCheckinService.getPlayerCheckin(event.getEntity()) || !HUNGRY_DISABLED)
            return;

        Player player = (Player) event.getEntity();
        player.setFoodLevel(20);
        event.setCancelled(true);
    }

    public  static  void onFoodRegenerateEvent(PlayerItemConsumeEvent event){
        if(!PlayerCheckinService.getPlayerCheckin(event.getPlayer()) || !FOOD_REGENERATION)
            return;

        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*FOOD_REGENERATION_TIME , 1));
    }


}
