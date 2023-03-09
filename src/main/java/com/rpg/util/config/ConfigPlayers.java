package com.rpg.util.config;

import com.rpg.RPGCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigPlayers {

    private final Plugin plugin = RPGCore.getPlugin();

    private final File playersFile = new File(plugin.getDataFolder(), "Players/db.yml");

    private FileConfiguration playersConfiguration = new YamlConfiguration();

    public static ConfigPlayers instance;


    private FileConfiguration configLeveling = RPGCore.getConfigLeveling().getConfig();


    private final int START_LEVEL = configLeveling.getInt("Config.XP_PLAYER_LEVEL_START");
    private final double INITIAL_MAX_EXPERIENCE = configLeveling.getDouble("Config.XP_PLAYER_LEVEL_UP_START_REQUIRED");
    private final double GOLD_PLAYER_START = configLeveling.getDouble("Config.GOLD_PLAYER_START");


    public ConfigPlayers() {
        CreateConnectionConfig();
    }

    private void CreateConnectionConfig() {
        try {
            if (!playersFile.exists()) {
                RPGCore.logInfo("[RPGCore] Players Config File . . .");
                playersConfiguration.save(playersFile);
                RPGCore.logInfo("[RPGCore] Players Config File: 100%");
            }
            playersConfiguration = YamlConfiguration.loadConfiguration(playersFile);
        } catch (IOException e) {
            RPGCore.logInfo("[RPGCore] Players File Configuration Error:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public File getFile() {
        return playersFile;
    }

    public FileConfiguration getConfig() {
        return playersConfiguration;
    }

    public void saveChanges(){
        try {
            playersConfiguration.save(playersFile);
        } catch (IOException e) {
            RPGCore.logInfo("[RPGCore] Error to save File Players DB:\n" + e.getMessage());
        }
    }


    public void createPlayersSection(Player player) throws IOException {
        playersConfiguration = YamlConfiguration.loadConfiguration(playersFile);
        ConfigurationSection configSection = playersConfiguration.getConfigurationSection("Status");

        if (configSection == null) {
            RPGCore.logInfo("[RPGCore] Creating Players Configuration . . .");
            configSection = playersConfiguration.createSection("Status");
        }

        if(configSection.getConfigurationSection(player.getUniqueId().toString()) == null)
            configSection.createSection(player.getUniqueId().toString());

        ConfigurationSection playerSection = configSection.getConfigurationSection(player.getUniqueId().toString());

        if (!playerSection.contains("checkin"))
            playerSection.set("checkin", false);

        if (!playerSection.contains("health"))
            playerSection.set("health", 20);

        if (!playerSection.contains("maxHealth"))
            playerSection.set("maxHealth", 20);

        if (!playerSection.contains("mana"))
            playerSection.set("mana", 8);

        if (!playerSection.contains("maxMana"))
            playerSection.set("maxMana", 8);

        if (!playerSection.contains("class"))
            playerSection.set("class", "");

        if(!playerSection.contains("playerName"))
            playerSection.set("playerName",player.getName());

        if(!playerSection.contains("balance"))
            playerSection.set("balance",GOLD_PLAYER_START);

        if(!playerSection.contains("level"))
            playerSection.set("level",START_LEVEL);

        if(!playerSection.contains("currentXp"))
            playerSection.set("currentXp",0);

        if(!playerSection.contains("maxXp"))
            playerSection.set("maxXp",INITIAL_MAX_EXPERIENCE);

        if(!playerSection.contains("core"))
            playerSection.set("core",0);

        ConfigurationSection attributesSection = playerSection.getConfigurationSection("Attributes");
        if (attributesSection == null) {
            attributesSection = playerSection.createSection("Attributes");
        }

        if(!attributesSection.contains("strength"))
            attributesSection.set("strength",0);

        if(!attributesSection.contains("dexterity"))
            attributesSection.set("dexterity",0);

        if(!attributesSection.contains("constitution"))
            attributesSection.set("constitution",0);

        if(!attributesSection.contains("intelligence"))
            attributesSection.set("intelligence",0);

        if(!attributesSection.contains("wisdom"))
            attributesSection.set("wisdom",0);

        if(!attributesSection.contains("charisma"))
            attributesSection.set("charisma",0);

        playersConfiguration.save(playersFile);
    }

    public static ConfigPlayers getInstance() {
        if(instance == null)
            instance = new ConfigPlayers();
        return instance;
    }

    public ConfigurationSection getConfiguration(Player player){
        try {
            createPlayersSection(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ConfigurationSection) playersConfiguration.get("Status."+player.getUniqueId().toString());
    }
}
