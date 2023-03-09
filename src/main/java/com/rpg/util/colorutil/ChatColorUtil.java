package com.rpg.util.colorutil;

import org.bukkit.ChatColor;

public class ChatColorUtil {

    public static String boldText(String msg){
        return ChatColor.BOLD+msg;
    }

    public static String boldText(String msg, ChatColor color){
        return color+""+ChatColor.BOLD+msg;
    }

    public static  String textColor(String msg, ChatColor color){
        return  color+""+msg;
    }

}
