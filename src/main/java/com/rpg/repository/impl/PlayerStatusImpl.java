package com.rpg.repository.impl;

import com.rpg.RPGCore;
import com.rpg.domain.PlayerStatus;
import com.rpg.repository.interfaces.PlayerStatusRepository;
import com.rpg.service.LoaderPlayerStatusService;
import com.rpg.service.PlayerCheckinService;
import com.rpg.util.ItemFactoryUtil;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Objects;

public class PlayerStatusImpl implements PlayerStatusRepository {

    private ConfigPlayers configPlayers = ConfigPlayers.getInstance();
    private FileConfiguration configLeveling = RPGCore.getConfigLeveling().getConfig();


    private final double XP_BASE_MULTIPLIER = configLeveling.getDouble("Config.XP_BASE_MULTIPLIER");
    private final double MOB_XP_BASE = configLeveling.getDouble("Config.MOB_XP_BASE");
    private final double PLAYER_XP_BASE = configLeveling.getDouble("Config.PLAYER_XP_BASE");
    private final double LEVEL_UP_GOLD_REWARD = configLeveling.getDouble("Config.LEVEL_UP_GOLD_REWARD");
    private final double LEVEL_ADDITIONAL_PERCENT_EVENTS = configLeveling.getDouble("Config.LEVEL_ADDITIONAL_PERCENT_EVENTS");
    private final int CORE_PER_LEVEL = configLeveling.getInt("Config.CORE_PER_LEVEL");
    private final int MAX_LEVEL = configLeveling.getInt("Config.MAX_LEVEL");

    private final boolean LIFE_SCALE_ENABLE = configLeveling.getBoolean("Config.LIFE_SCALE_ENABLE");
    private final double LIFE_RECEIVED_PER_LEVEL = configLeveling.getDouble("Config.LIFE_RECEIVED_PER_LEVEL");


    private final ConfigurationSection MOB_EXPERIENCE_SECTION = configLeveling.getConfigurationSection("MobExperience");

    @Override
    public PlayerStatus getPlayerStatus(Player player) {

        if(configPlayers.getConfiguration(player) == null)
            return null;

        ConfigurationSection playerSection = configPlayers.getConfiguration(player);

        PlayerStatus playerStatus = new PlayerStatus();

        playerStatus.setPlayerName(player.getName());
        playerStatus.setPlayerUUID(player.getUniqueId().toString());
        playerStatus.setBalance(playerSection.getDouble("balance"));
        playerStatus.setLevel(playerSection.getInt("level"));
        playerStatus.setCurrentXp(playerSection.getDouble("currentXp"));
        playerStatus.setMaxXp(playerSection.getDouble("maxXp"));
        playerStatus.setCore(playerSection.getInt("core"));
        playerStatus.setMaxHealth(playerSection.getDouble("maxHealth"));
        playerStatus.setHealth(playerSection.getDouble("health"));
        playerStatus.setMaxMana(playerSection.getDouble("maxMana"));
        playerStatus.setMana(playerSection.getDouble("mana"));

        return  playerStatus;
    }

    @Override
    public PlayerStatus createPlayerStatus(Player player) {
        try {
            if( configPlayers.getConfig().get("Status."+player.getUniqueId()) == null)
                configPlayers.createPlayersSection(player);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  getPlayerStatus(player);
    }

    @Deprecated
    @Override
    public void setExperience(Player player, LivingEntity entity) {

        ConfigurationSection playerSection = configPlayers.getConfiguration(player);
        ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");
        int constitution =  attributeSection.getInt("constitution");

        PlayerStatus playerStatus = getPlayerStatus(player);
        if(playerStatus == null)
            return;

        if(playerStatus.getLevel() >= MAX_LEVEL){
            playerStatus.setCurrentXp(playerStatus.getMaxXp());
            setPlayerStatus(playerStatus,player);
            LoaderPlayerStatusService.createScoreBoardAutoUpdate(player);
            return;
        }

        if(entity instanceof Player) {
            playerStatus.setCurrentXp(playerStatus.getCurrentXp() + (PLAYER_XP_BASE) * (1+(LEVEL_ADDITIONAL_PERCENT_EVENTS/100)) );
        }
        else{
            playerStatus.setCurrentXp(playerStatus.getCurrentXp() + (MOB_XP_BASE) * (1+(LEVEL_ADDITIONAL_PERCENT_EVENTS/100)) );
            playerStatus.setCurrentXp(playerStatus.getCurrentXp() + getExperienceByEntityType(entity));
        }

        if(playerStatus.getCurrentXp() >= playerStatus.getMaxXp()){
            playerStatus.setCurrentXp(0D);
            playerStatus.setLevel(playerStatus.getLevel() + 1);
            playerStatus.setMaxXp(playerStatus.getMaxXp() * XP_BASE_MULTIPLIER);
            playerStatus.setBalance(playerStatus.getBalance() + LEVEL_UP_GOLD_REWARD);
            playerStatus.setCore(playerStatus.getCore() + CORE_PER_LEVEL);
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1F , 1F);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1F , 1F);

            if(LIFE_SCALE_ENABLE){
                playerStatus.setMaxHealth(playerStatus.getMaxHealth() + LIFE_RECEIVED_PER_LEVEL + constitution);
                calculateCurrentHealth(player , playerStatus.getMaxHealth() );
            }


            player.sendMessage(ChatColorUtil.boldText("=========================================",ChatColor.WHITE));
            player.sendMessage(ChatColorUtil.boldText(TranslateUtil.getKey("LEVEL_UP") + " " + playerStatus.getLevel() ,ChatColor.GREEN));
            player.sendMessage(ChatColorUtil.boldText("=========================================",ChatColor.WHITE));
        }

        setPlayerStatus(playerStatus , player);
        LoaderPlayerStatusService.createScoreBoardAutoUpdate(player);
    }

    private Double getExperienceByEntityType(LivingEntity entity){
        return MOB_EXPERIENCE_SECTION.getDouble(entity.getType().toString().toLowerCase());
    }

    private void setPlayerStatus(PlayerStatus playerStatus, Player player){
        ConfigurationSection playerSection = configPlayers.getConfiguration(player);

        playerSection.set("balance", playerStatus.getBalance());
        playerSection.set("level", playerStatus.getLevel());
        playerSection.set("currentXp", playerStatus.getCurrentXp());
        playerSection.set("maxXp", playerStatus.getMaxXp());
        playerSection.set("core", playerStatus.getCore());
        playerSection.set("maxHealth",playerStatus.getMaxHealth());
        playerSection.set("health",playerStatus.getHealth());
        playerSection.set("maxMana",playerStatus.getMaxMana());
        playerSection.set("mana",playerStatus.getMana());

        configPlayers.saveChanges();
    }


    public static void calculateCurrentHealth(Player player, Double currentMaxHealth ){
        if (!PlayerCheckinService.getPlayerCheckin(player))
            return;

        double health =  ConfigPlayers.getInstance().getConfiguration(player).getInt("health");
        double playerHealthConverted = (health * Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()) / currentMaxHealth;
        player.setHealth(playerHealthConverted);
    }

    public static void calculateCurrentHealth(Player player){
        if (!PlayerCheckinService.getPlayerCheckin(player))
            return;

        double maxHealth =  ConfigPlayers.getInstance().getConfiguration(player).getInt("maxHealth");
        double currentHealth = player.getHealth() * maxHealth/  Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        double playerHealthConverted = (currentHealth *  Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()) / maxHealth;

        player.setHealth( playerHealthConverted );
    }

}
