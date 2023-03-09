package com.rpg.service;

import com.rpg.RPGCore;
import com.rpg.packages.HealthBossBar;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerMoveService {


    private static ConfigPlayers configPlayers = ConfigPlayers.getInstance();
    private static FileConfiguration configLeveling = RPGCore.getConfigLeveling().getConfig();
    private static int MAX_LEVEL = configLeveling.getInt("Config.MAX_LEVEL");

    public static void onPlayerMove(Player player){

        if(!PlayerCheckinService.getPlayerCheckin(player))
            return;

        ConfigurationSection playerSection = configPlayers.getConfiguration(player);
        ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");

        int dexterity =  attributeSection.getInt("dexterity");

        float movimentSpeed = (float) dexterity/MAX_LEVEL;
        if(movimentSpeed < 0.12){
            player.setWalkSpeed((float) (0.2+(movimentSpeed/2)));
        }else{
            player.setWalkSpeed((float) 0.32);
        }

    }

    public static void getTargetNearArea(Player player){
        HealthBossBar.openBossHealthBar(player, getTarget(player, player.getNearbyEntities(10,10,10)));
    }


    public static Player getTargetPlayer(final Player player) {
        return getTarget(player, player.getWorld().getPlayers());
    }

    public static Entity getTargetEntity(final Entity entity) {
        return getTarget(entity, entity.getWorld().getEntities());
    }

    public static <T extends Entity> T getTarget(final Entity entity, final Iterable<T> entities) {
        if (entity == null)  return null;

        T target = null;
        final double threshold = 1;

        for (final T other : entities) {
            final Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n)  .lengthSquared() < threshold  && n.normalize().dot(  entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null || target.getLocation().distanceSquared(  entity.getLocation()) > other.getLocation() .distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }

}
