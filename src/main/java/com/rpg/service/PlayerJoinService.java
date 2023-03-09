package com.rpg.service;

import com.rpg.RPGCore;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigRPG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PlayerJoinService {
    private static final ConfigRPG configRPG = RPGCore.getConfigRPG();
    private static final Plugin plugin = RPGCore.getPlugin();
    private static final boolean CREDITS_ON_JOIN =  Objects.requireNonNull(configRPG.getConfig().getConfigurationSection("Config")).getBoolean("CREDITS_ON_JOIN");


    public  static  void onPlayerJoinEven(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!PlayerCheckinService.getPlayerCheckin(player))
            return;

        LoaderPlayerStatusService.createScoreBoardAutoUpdate(event.getPlayer());

        if(CREDITS_ON_JOIN){
            player.sendMessage(ChatColorUtil.boldText("======================", ChatColor.GOLD));
            player.sendMessage(ChatColorUtil.boldText("[RPGCore] ", ChatColor.GREEN));
            player.sendMessage(ChatColorUtil.textColor(" Loaded Version: "+plugin.getDescription().getVersion(), ChatColor.GREEN));
            player.sendMessage(ChatColorUtil.textColor(" Author: "+plugin.getDescription().getAuthors(), ChatColor.GREEN));
            player.sendMessage(ChatColorUtil.textColor(" Description: \n "+ChatColor.GRAY+plugin.getDescription().getDescription(), ChatColor.GREEN));
            player.sendMessage(ChatColorUtil.boldText("======================", ChatColor.GOLD));
        }

    }


}
