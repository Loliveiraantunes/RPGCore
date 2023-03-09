package com.rpg.service;

import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class ReportService {


    private static ConfigPlayers configPlayers = ConfigPlayers.getInstance();

    public static void showLevelPlayersList(Integer page, Player player){
        Set<String> allPlayers  = Objects.requireNonNull(configPlayers.getConfig().getConfigurationSection("Status")).getKeys(false);
        Map<Integer, String> playersLevel = new TreeMap<>(Collections.reverseOrder());

        int index = 1;
        allPlayers.stream().forEach(ap -> {
            String name = Objects.requireNonNull(configPlayers.getConfig().getConfigurationSection("Status." + ap)).getString("playerName");
            Integer level = Objects.requireNonNull(configPlayers.getConfig().getConfigurationSection("Status." + ap)).getInt("level");
            playersLevel.put(level,name);
        });

        Set set = playersLevel.entrySet();
        Iterator i = set.iterator();


        int startListValue = 1;
        int finishListValue = 10;

        if(page > 1){
            startListValue = 10 * (page-1);
            finishListValue = startListValue * page;
        }

        player.sendMessage(ChatColorUtil.boldText(TranslateUtil.getKey("PLAYER_LEVEL_LIST"),ChatColor.WHITE));
        player.sendMessage(ChatColorUtil.boldText("==============================",ChatColor.GOLD));

        while (i.hasNext()) {
            Map.Entry playerStatus = (Map.Entry)i.next();

            if(index >= startListValue && index <= finishListValue )
                player.sendMessage( ChatColorUtil.boldText( ChatColor.GOLD+""+index+" - "+ playerStatus.getValue().toString(), ChatColor.GRAY) + ChatColor.GOLD+" . . . "+ChatColorUtil.boldText( playerStatus.getKey().toString(), ChatColor.GREEN ));
            index ++;
        }


        if((playersLevel.size()/10) > 1){
            player.sendMessage(ChatColorUtil.boldText("==============================",ChatColor.GOLD));
            player.sendMessage(ChatColorUtil.boldText(TranslateUtil.getKey("TOTAL_PAGES"),ChatColor.WHITE)+"    ("+page+"/"+(playersLevel.size()/10)+")                     ");
        }

    }



}
