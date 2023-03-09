package com.rpg.service;

import com.rpg.repository.impl.PlayerCheckinImpl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerCheckinService {
    private static PlayerCheckinImpl playerCheckin  =  new PlayerCheckinImpl();

    public static void setPlayerCheckin(Player player){
        playerCheckin.setPlayerCheckin(player);
    }

    public static boolean getPlayerCheckin(Player player){
        return playerCheckin.getPlayerCheckin(player);
    }

    public static boolean getPlayerCheckin(Entity entity){
        if(!(entity instanceof Player))
            return false;
        return playerCheckin.getPlayerCheckin((Player) entity);
    }

}
