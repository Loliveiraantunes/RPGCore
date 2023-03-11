package com.rpg.util.config;

import com.rpg.RPGCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Warden;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigLeveling {

    private final Plugin plugin = RPGCore.getPlugin();
    private final File levelingFile = new File(plugin.getDataFolder(), "leveling.yml");
    private FileConfiguration levelingFileConfiguration = new YamlConfiguration();

    public ConfigLeveling() {
        CreateConnectionConfig();
    }

    private void CreateConnectionConfig() {
        RPGCore.logInfo("[RPGCore] Checking Leveling Files . . .");
        try {
            if (!levelingFile.exists()) {
                RPGCore.logInfo("[RPGCore] Creating Leveling File . . .");
                levelingFileConfiguration.save(levelingFile);
                RPGCore.logInfo("[RPGCore] Leveling File: 100%");
            }

             levelingFileConfiguration = YamlConfiguration.loadConfiguration(levelingFile);

            createConfigHeader();
            createConfigSection();
            createMobExperienceSection();



            levelingFileConfiguration.save(levelingFile);
            RPGCore.logInfo("[RPGCore] Leveling File Configuration: 100%");

            RPGCore.logInfo("[RPGCore] Leveling Files: 100%");
        } catch (IOException e) {
            RPGCore.logInfo("[RPGCore] Leveling File Configuration Error:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private  void createConfigHeader(){
        List<String> header = new ArrayList<>();
        header.add(" \n" +
                "**************************************************************************************\n"+
                "* CONFIGURATION INFO                                                                 *\n"+
                "**************************************************************************************\n"+
                "*   XP_BASE_MULTIPLIER: Used to calculate the next goal of experience increasing.    *\n"+
                "*   MOB_XP_BASE: Additional experience reward for each mob killed.                   *\n"+
                "*   PLAYER_XP_BASE: Additional experience reward for each player killed.             *\n"+
                "*   LEVEL_XP_MULTIPLIER: Multiplies base experience by creature level.               *\n"+
                "*   LEVEL_UP_GOLD_REWARD: Gold reward for leveling up.                               *\n"+
                "*   LEVEL_ADDITIONAL_PERCENT_EVENTS: Additional percentage of experience in events.  *\n"+
                "*   XP_PLAYER_LEVEL_UP_START_REQUIRED: Value required for first level up.            *\n"+
                "*   XP_PLAYER_LEVEL_START: initial player level value.                               *\n"+
                "*   GOLD_PLAYER_START: initial player gold value.                                    *\n"+
                "*   CORE_PER_LEVEL: Add core value when player reach next level.                     *\n"+
                "*   LIFE_SCALE_ENABLE: Enable life scale by level, increasing player life.           *\n"+
                "*   LIFE_RECEIVED_PER_LEVEL: Amount of life that will increase per level.            *\n"+
                "*   MOB EXPERIENCE: Added value in experience when killing the creature by the type. *\n"+
                "**************************************************************************************\n");


        levelingFileConfiguration.options().setHeader(header);
    }


    private void createConfigSection() {

        ConfigurationSection configSection = levelingFileConfiguration.getConfigurationSection("Config");

        if (configSection == null){
            RPGCore.logInfo("[RPGCore] Creating Leveling File Configuration . . .");
            configSection = levelingFileConfiguration.createSection("Config");
        }
           

        if (!configSection.contains("XP_BASE_MULTIPLIER"))
            configSection.set("XP_BASE_MULTIPLIER", 1.5);

        if (!configSection.contains("MOB_XP_BASE"))
            configSection.set("MOB_XP_BASE", 50);

        if (!configSection.contains("PLAYER_XP_BASE"))
            configSection.set("PLAYER_XP_BASE", 100);

        if (!configSection.contains("LEVEL_XP_MULTIPLIER"))
            configSection.set("LEVEL_XP_MULTIPLIER", false);

        if (!configSection.contains("LEVEL_UP_GOLD_REWARD"))
            configSection.set("LEVEL_UP_GOLD_REWARD", 50);

        if (!configSection.contains("LEVEL_ADDITIONAL_PERCENT_EVENTS"))
            configSection.set("LEVEL_ADDITIONAL_PERCENT_EVENTS", 5);

        if (!configSection.contains("XP_PLAYER_LEVEL_UP_START_REQUIRED"))
            configSection.set("XP_PLAYER_LEVEL_UP_START_REQUIRED", 550);

        if (!configSection.contains("XP_PLAYER_LEVEL_START"))
            configSection.set("XP_PLAYER_LEVEL_START", 1);

        if (!configSection.contains("GOLD_PLAYER_START"))
            configSection.set("GOLD_PLAYER_START", 500);

        if (!configSection.contains("CORE_PER_LEVEL"))
            configSection.set("CORE_PER_LEVEL", 1);

        if (!configSection.contains("MAX_LEVEL"))
            configSection.set("MAX_LEVEL", 60);

        if (!configSection.contains("LIFE_SCALE_ENABLE"))
            configSection.set("LIFE_SCALE_ENABLE", true);

        if (!configSection.contains("LIFE_RECEIVED_PER_LEVEL"))
            configSection.set("LIFE_RECEIVED_PER_LEVEL", 2);

    }

    private void createMobExperienceSection() {
        ConfigurationSection mobExpercieSection = levelingFileConfiguration.getConfigurationSection("MobExperience");

        if (mobExpercieSection == null){
            RPGCore.logInfo("[RPGCore] Setup All Mobs XP . . .");
            mobExpercieSection = levelingFileConfiguration.createSection("MobExperience");
        }

        if (!mobExpercieSection.contains("abstract_horse"))
            mobExpercieSection.set("abstract_horse", 10);

        if (!mobExpercieSection.contains("allay"))
            mobExpercieSection.set("allay", 50);

        if (!mobExpercieSection.contains("axolotl"))
            mobExpercieSection.set("axolotl", 10);

        if (!mobExpercieSection.contains("bee"))
            mobExpercieSection.set("bee", 5);

        if (!mobExpercieSection.contains("bat"))
            mobExpercieSection.set("bat", 5);

        if (!mobExpercieSection.contains("blaze"))
            mobExpercieSection.set("blaze", 30);

        if (!mobExpercieSection.contains("cave_spider"))
            mobExpercieSection.set("cave_spider", 20);

        if (!mobExpercieSection.contains("chicken"))
            mobExpercieSection.set("chicken", 5);

        if (!mobExpercieSection.contains("cow"))
            mobExpercieSection.set("cow", 10);

        if (!mobExpercieSection.contains("cod"))
            mobExpercieSection.set("cod", 3);

        if (!mobExpercieSection.contains("creeper"))
            mobExpercieSection.set("creeper", 20);

        if (!mobExpercieSection.contains("dolphin"))
            mobExpercieSection.set("dolphin", 10);

        if (!mobExpercieSection.contains("donkey"))
            mobExpercieSection.set("donkey", 10);

        if (!mobExpercieSection.contains("drowned"))
            mobExpercieSection.set("drowned", 20);

        if (!mobExpercieSection.contains("elder_guardian"))
            mobExpercieSection.set("elder_guardian", 90);

        if (!mobExpercieSection.contains("ender_dragon"))
            mobExpercieSection.set("ender_dragon", 5000);

        if (!mobExpercieSection.contains("ender_man"))
            mobExpercieSection.set("ender_man", 50);

        if (!mobExpercieSection.contains("endermite"))
            mobExpercieSection.set("endermite", 5);

        if (!mobExpercieSection.contains("evoker"))
            mobExpercieSection.set("evoker", 15);

        if (!mobExpercieSection.contains("fox"))
            mobExpercieSection.set("fox", 5);

        if (!mobExpercieSection.contains("frog"))
            mobExpercieSection.set("frog", 5);

        if (!mobExpercieSection.contains("ghast"))
            mobExpercieSection.set("ghast", 45);

        if (!mobExpercieSection.contains("giant"))
            mobExpercieSection.set("giant", 300);

        if (!mobExpercieSection.contains("glow_squid"))
            mobExpercieSection.set("glow_squid", 300);

        if (!mobExpercieSection.contains("guardian"))
            mobExpercieSection.set("guardian", 20);

        if (!mobExpercieSection.contains("golem"))
            mobExpercieSection.set("golem", 10);

        if (!mobExpercieSection.contains("goat"))
            mobExpercieSection.set("goat", 10);

        if (!mobExpercieSection.contains("horse"))
            mobExpercieSection.set("horse", 10);

        if (!mobExpercieSection.contains("hoglin"))
            mobExpercieSection.set("hoglin", 15);

        if (!mobExpercieSection.contains("husk"))
            mobExpercieSection.set("husk", 20);

        if (!mobExpercieSection.contains("illusioner"))
            mobExpercieSection.set("illusioner", 60);

        if (!mobExpercieSection.contains("iron_golem"))
            mobExpercieSection.set("iron_golem", 60);

        if (!mobExpercieSection.contains("llama"))
            mobExpercieSection.set("llama", 10);

        if (!mobExpercieSection.contains("magma_cube"))
            mobExpercieSection.set("magma_cube", 20);

        if (!mobExpercieSection.contains("mule"))
            mobExpercieSection.set("mule", 20);

        if (!mobExpercieSection.contains("mushroom_cow"))
            mobExpercieSection.set("mushroom_cow", 10);

        if (!mobExpercieSection.contains("ocelot"))
            mobExpercieSection.set("ocelot", 5);

        if (!mobExpercieSection.contains("panda"))
            mobExpercieSection.set("panda", 10);

        if (!mobExpercieSection.contains("parrot"))
            mobExpercieSection.set("parrot", 10);

        if (!mobExpercieSection.contains("phantom"))
            mobExpercieSection.set("phantom", 15);

        if (!mobExpercieSection.contains("pig"))
            mobExpercieSection.set("pig", 10);

        if (!mobExpercieSection.contains("piglin"))
            mobExpercieSection.set("piglin", 20);

        if (!mobExpercieSection.contains("piglin_brute"))
            mobExpercieSection.set("piglin_brute", 30);

        if (!mobExpercieSection.contains("pillager"))
            mobExpercieSection.set("pillager", 25);

        if (!mobExpercieSection.contains("pig_zombie"))
            mobExpercieSection.set("pig_zombie", 20);

        if (!mobExpercieSection.contains("polar_bear"))
            mobExpercieSection.set("polar_bear", 20);

        if (!mobExpercieSection.contains("pufferfish"))
            mobExpercieSection.set("pufferfish", 5);

        if (!mobExpercieSection.contains("rabbit"))
            mobExpercieSection.set("rabbit", 5);

        if (!mobExpercieSection.contains("ravager"))
            mobExpercieSection.set("ravager", 50);

        if (!mobExpercieSection.contains("salmon"))
            mobExpercieSection.set("salmon", 3);

        if (!mobExpercieSection.contains("sheep"))
            mobExpercieSection.set("sheep", 10);

        if (!mobExpercieSection.contains("shulker"))
            mobExpercieSection.set("shulker", 35);

        if (!mobExpercieSection.contains("silverfish"))
            mobExpercieSection.set("silverfish", 15);

        if (!mobExpercieSection.contains("skeleton"))
            mobExpercieSection.set("skeleton", 15);

        if (!mobExpercieSection.contains("skeleton_horse"))
            mobExpercieSection.set("skeleton_horse", 15);

        if (!mobExpercieSection.contains("slime"))
            mobExpercieSection.set("slime", 5);

        if (!mobExpercieSection.contains("snowman"))
            mobExpercieSection.set("snowman", 5);

        if (!mobExpercieSection.contains("spider"))
            mobExpercieSection.set("spider", 20);

        if (!mobExpercieSection.contains("squid"))
            mobExpercieSection.set("squid", 10);

        if (!mobExpercieSection.contains("strider"))
            mobExpercieSection.set("strider", 15);

        if (!mobExpercieSection.contains("stray"))
            mobExpercieSection.set("stray", 5);

        if (!mobExpercieSection.contains("turtle"))
            mobExpercieSection.set("turtle", 5);

        if (!mobExpercieSection.contains("tropical_fish"))
            mobExpercieSection.set("tropical_fish", 3);

        if (!mobExpercieSection.contains("trader_llama"))
            mobExpercieSection.set("trader_llama", 15);

        if (!mobExpercieSection.contains("vex"))
            mobExpercieSection.set("vex", 5);

        if (!mobExpercieSection.contains("villager"))
            mobExpercieSection.set("villager", 15);

        if (!mobExpercieSection.contains("vindicator"))
            mobExpercieSection.set("vindicator", 15);

        if (!mobExpercieSection.contains("wandering_trader"))
            mobExpercieSection.set("wandering_trader", 20);

        if (!mobExpercieSection.contains("warden"))
            mobExpercieSection.set("warden", 450);

        if (!mobExpercieSection.contains("water_bob"))
            mobExpercieSection.set("water_bob", 15);

        if (!mobExpercieSection.contains("witch"))
            mobExpercieSection.set("witch", 30);

        if (!mobExpercieSection.contains("wither"))
            mobExpercieSection.set("wither", 250);

        if (!mobExpercieSection.contains("wither_skeleton"))
            mobExpercieSection.set("wither_skeleton", 40);

        if (!mobExpercieSection.contains("wolf"))
            mobExpercieSection.set("wolf", 40);

        if (!mobExpercieSection.contains("zombie"))
            mobExpercieSection.set("zombie", 20);

        if (!mobExpercieSection.contains("zoglin"))
            mobExpercieSection.set("zoglin", 15);

        if (!mobExpercieSection.contains("zombie_horse"))
            mobExpercieSection.set("zombie_horse", 10);

        if (!mobExpercieSection.contains("zombie_villager"))
            mobExpercieSection.set("zombie_villager", 30);

        if (!mobExpercieSection.contains("zombified_piglin"))
            mobExpercieSection.set("zombified_piglin", 30);

        RPGCore.logInfo("[RPGCore] All Mobs: 100%");
    }
    public FileConfiguration getConfig() {
        return levelingFileConfiguration;
    }

}
