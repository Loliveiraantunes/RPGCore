package com.rpg.util;

import com.rpg.RPGCore;
import org.bukkit.entity.Player;


public class TranslateUtil {
    private static final String LOCAL_LANGUAGE = RPGCore.getConfigTranslateFiles().getConfig().getString("local-language").toUpperCase();

    public static String getKey(String key){
        return RPGCore.getConfigTranslateFiles().getMessage(LOCAL_LANGUAGE, key.toLowerCase());
    }

    public static String getKey(String key, Player player){
        return RPGCore.getConfigTranslateFiles().getMessage(LOCAL_LANGUAGE, key.toLowerCase()).replace("{$playerName}",player.getName()).replace("{$break}","/n");
    }
}
