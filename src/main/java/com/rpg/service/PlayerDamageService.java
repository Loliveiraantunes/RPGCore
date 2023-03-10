package com.rpg.service;

import com.rpg.RPGCore;
import com.rpg.packages.DamageIndicator;
import com.rpg.packages.HealthBossBar;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Random;

public class PlayerDamageService {

    private static final ConfigPlayers configPlayers = ConfigPlayers.getInstance();
    private static final FileConfiguration configLeveling = RPGCore.getConfigLeveling().getConfig();
    private  static final FileConfiguration configuration = RPGCore.getConfigRPG().getConfig();
    private static final Plugin plugin =  RPGCore.getPlugin();
    private static final int MAX_LEVEL = configLeveling.getInt("Config.MAX_LEVEL");
    private static final boolean LIFE_SCALE_ENABLE = configLeveling.getBoolean("Config.LIFE_SCALE_ENABLE");
    private static final int ROLL_CHANCE = configuration.getInt("Config.ROLL_CHANCE");
    private static final int DODGE_CHANCE = configuration.getInt("Config.DODGE_CHANCE");
    private static final int MAX_FALL_DAMAGE_REDUCER = configuration.getInt("Config.MAX_FALL_DAMAGE_REDUCER");
    private static final Random RANDOM = new Random();

    public static void onPlayerTakeDamage( Double damage, EntityDamageEvent event){

        if(!(event.getEntity() instanceof Player))
            return;

        Player player = ((Player) event.getEntity()).getPlayer();
        if (player == null || !PlayerCheckinService.getPlayerCheckin(player) || !LIFE_SCALE_ENABLE)
            return;

        double maxHealth =  configPlayers.getConfiguration(player).getInt("maxHealth");
        double newDamage = (damage * Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue())/maxHealth;

        ConfigurationSection playerSection = configPlayers.getConfiguration(player);
        ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");

        float constitution =  Objects.requireNonNull(attributeSection).getInt("constitution");
        int dexterity =  attributeSection.getInt("dexterity");

        switch (event.getCause()) {
            case FALL -> {
                int minimalChance = ROLL_CHANCE + (dexterity / 2);
                if (RANDOM.nextInt(99) + 1 <= minimalChance) {
                    ActionBarService.buildNoticeStatus(player, ChatColorUtil.boldText("  ▒▒▒ " + TranslateUtil.getKey("ROLL") + " ▒▒▒  ", ChatColor.GREEN));
                    if (event.getDamage() > MAX_FALL_DAMAGE_REDUCER) {
                        newDamage = newDamage - MAX_FALL_DAMAGE_REDUCER;
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK, PROJECTILE ->
                    dodgeDamageChance(DODGE_CHANCE + (dexterity / 2), player, event);
            case POISON -> {
                float resistancePoison = constitution / MAX_LEVEL;
                newDamage = newDamage - resistancePoison;
            }
            default -> { }
        }

        event.setDamage(newDamage);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            configPlayers.getConfiguration(player).set("health",(int) Math.ceil(((player.getHealth() * maxHealth)/ Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue())));
            configPlayers.saveChanges();
        }, 0);
    }



    private static void dodgeDamageChance(int minimalChanceDodge, Player player, EntityDamageEvent event){
        if(new Random().nextInt(99) + 1 <= minimalChanceDodge){
            ActionBarService.buildNoticeStatus(player, ChatColorUtil.boldText("  ▒▒▒ "+ TranslateUtil.getKey("DODGE") +" ▒▒▒  ",ChatColor.GREEN));
            event.setCancelled(true);
        }
    }

    public static void onPlayerDie(PlayerRespawnEvent event){
        if (!PlayerCheckinService.getPlayerCheckin(event.getPlayer()))
            return;

        if(!LIFE_SCALE_ENABLE)
            return;

        double maxHealth =  configPlayers.getConfiguration(event.getPlayer()).getInt("maxHealth");
        configPlayers.getConfiguration(event.getPlayer()).set("health", maxHealth);
        configPlayers.saveChanges();

        LoaderPlayerStatusService.createScoreBoardAutoUpdate(event.getPlayer());
    }

    public static void onPlayerHit(EntityDamageByEntityEvent event){

            if(event.getEntity().hasMetadata("DAMAGE_INDICATOR")){
                event.setCancelled(true);
                return;
            }

            Player player = null;
            if((event.getDamager() instanceof Player))
                player = (Player) event.getDamager();

            if(player == null && event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof  Player)
                player = (Player) ((Arrow) event.getDamager()).getShooter();

            if ( player == null || !PlayerCheckinService.getPlayerCheckin(player))
                return;

            ConfigurationSection playerSection = configPlayers.getConfiguration(player);
            ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");

            float strength =  Objects.requireNonNull(attributeSection).getInt("strength");
            float dexterity =  attributeSection.getInt("dexterity");

        switch (event.getCause()) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> {
                event.setDamage(event.getDamage() + (strength / 2));
                entityDamage(event, event.getDamage());
            }
            case PROJECTILE -> {
                event.setDamage(event.getDamage() + (dexterity / 2));
                entityDamage(event, event.getDamage());
            }
            default -> {
            }
        }

    }

    private  static void entityDamage(EntityDamageByEntityEvent event, double damage){
        DecimalFormat df = new DecimalFormat("#.##");
        new DamageIndicator().displayDamageIndicator(event, df.format(damage));
    }

    public static void  showHealthBossBarWhenHit(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getDamager();

        HealthBossBar.openBossHealthBar(player,event.getEntity());
    }

    public static void  showHealthBossBarWhenProjectile(ProjectileHitEvent event){
        if(!(event.getEntity().getShooter() instanceof Player))
            return;

        Player player = (Player) event.getEntity().getShooter();

        HealthBossBar.openBossHealthBar(player,event.getHitEntity());
    }


}
