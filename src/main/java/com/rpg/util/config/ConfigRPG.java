package com.rpg.util.config;

import com.rpg.RPGCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigRPG {

    private  Plugin plugin = RPGCore.getPlugin();
    private  File rpgFile = new File(plugin.getDataFolder(), "config.yml");
    private  FileConfiguration ConfigFileConfiguration = new YamlConfiguration();

    public ConfigRPG() {
        CreateConnectionConfig();
    }

    private void CreateConnectionConfig() {
        RPGCore.logInfo("[RPGCore] Checking Config File . . .");
        try {
            if (!rpgFile.exists()) {
                RPGCore.logInfo("[RPGCore] Creating Config File . . .");
                ConfigFileConfiguration.save(rpgFile);
            }
             ConfigFileConfiguration = YamlConfiguration.loadConfiguration(rpgFile);

            createConfigSection();

            ConfigFileConfiguration.save(rpgFile);
            RPGCore.logInfo("[RPGCore] Config File Configuration: 100%");
            RPGCore.logInfo("[RPGCore] Config File: 100%");
        } catch (IOException e) {
            RPGCore.logInfo("[RPGCore] Config File Configuration Error:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createConfigSection() {

        ConfigurationSection configSection = ConfigFileConfiguration.getConfigurationSection("Config");

        if (configSection == null){
            RPGCore.logInfo("[RPGCore] Creating Config File Configuration . . .");
            configSection = ConfigFileConfiguration.createSection("Config");
        }

        if (!configSection.contains("HUNGRY_DISABLED"))
            configSection.set("HUNGRY_DISABLED", false);

        if (!configSection.contains("FOOD_REGENERATION"))
            configSection.set("FOOD_REGENERATION", false);

        if (!configSection.contains("FOOD_REGENERATION_TIME"))
            configSection.set("FOOD_REGENERATION_TIME", 1);

        if (!configSection.contains("ROLL_CHANCE"))
            configSection.set("ROLL_CHANCE", 5);

        if (!configSection.contains("MAX_FALL_DAMAGE_REDUCER"))
            configSection.set("MAX_FALL_DAMAGE_REDUCER", 10);

        if (!configSection.contains("DODGE_CHANCE"))
            configSection.set("DODGE_CHANCE", 1);

        if (!configSection.contains("CREDITS_ON_JOIN"))
            configSection.set("CREDITS_ON_JOIN", true);


    }


    public  File getFile() {
        return rpgFile;
    }

    public FileConfiguration getConfig() {
        return ConfigFileConfiguration;
    }

}
