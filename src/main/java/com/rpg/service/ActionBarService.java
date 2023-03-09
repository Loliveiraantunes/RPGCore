package com.rpg.service;

import com.rpg.RPGCore;
import com.rpg.util.ProtocolUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ActionBarService {
    private static final ConfigPlayers configPlayers = ConfigPlayers.getInstance();
    private static final Plugin plugin =  RPGCore.getPlugin();
    private static final  Map<UUID,String > noticeStatus = new HashMap<>();
    private static final FileConfiguration configLeveling = RPGCore.getConfigLeveling().getConfig();
    private static final boolean LIFE_SCALE_ENABLE = configLeveling.getBoolean("Config.LIFE_SCALE_ENABLE");
    private static final String HEALTH = "health";


    public static void updateActionBar(Entity entity){

        if(!PlayerCheckinService.getPlayerCheckin(entity))
            return;

        Player player = (Player) entity;
        StringBuilder displayText = new StringBuilder();
        String notice = noticeStatus.get(player.getUniqueId());
        buildHealthStatus(displayText,player);
        displayText.append( notice != null ? notice : "                    ");
        buildManaStatus(displayText,player);
        ProtocolUtil.displayMessage(player,displayText.toString() );
    }

    private static void buildHealthStatus(StringBuilder displayText, Player player){
        int health = configPlayers.getConfiguration(player).getInt(HEALTH);
        int maxHealth = configPlayers.getConfiguration(player).getInt("maxHealth");

        displayText.append(ChatColorUtil.textColor("? "+health+" ? "+maxHealth, ChatColor.RED));
    }

    public static void buildNoticeStatus(Player player, String message){
         noticeStatus.put(player.getUniqueId(), message);
         player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1F, 1F);
         plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> noticeStatus.put(player.getUniqueId(),"                    "), 60);
    }

    private static void buildManaStatus(StringBuilder displayText, Player player){
        int currentMana = configPlayers.getConfiguration(player).getInt("mana");
        int maxMana = configPlayers.getConfiguration(player).getInt("maxMana");

        displayText.append(ChatColorUtil.textColor("? "+currentMana+" ? "+maxMana, ChatColor.AQUA));
    }

    public static void onPlayerRegainHealth(EntityRegainHealthEvent event){

        if(!(event.getEntity() instanceof Player player))
            return;

        if(!PlayerCheckinService.getPlayerCheckin(player) || !LIFE_SCALE_ENABLE)
            return;

        double maxHealth = getPlayerMaxHealth(player) ;
        double playerMaxHealth = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();

        ConfigurationSection playerSection = configPlayers.getConfiguration(player);
        ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");
        int constitution =  Objects.requireNonNull(attributeSection).getInt("constitution");

        event.setAmount((float)( (1 * playerMaxHealth ) + constitution)/ maxHealth);

        configPlayers.getConfiguration(player).set(HEALTH,(float)(player.getHealth() * maxHealth) / (playerMaxHealth));

        if((player.getHealth() + event.getAmount()) >= playerMaxHealth)
            configPlayers.getConfiguration(player).set(HEALTH,maxHealth);

        configPlayers.saveChanges();
    }

    private static double getPlayerMaxHealth(Player player){
        return configPlayers.getConfiguration(player).getInt("maxHealth");
    }

}
