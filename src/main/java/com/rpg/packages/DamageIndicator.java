package com.rpg.packages;

import com.comphenix.protocol.events.PacketContainer;
import com.rpg.RPGCore;
import com.rpg.util.colorutil.ChatColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class DamageIndicator {
    public static final Map<UUID,Entity> damageIndicatorsEntities = new HashMap<>();

    public  void displayDamageIndicator(EntityDamageByEntityEvent damageEvent , String lastDamage){

        Entity entity = damageEvent.getEntity();
        Location location = entity.getLocation();

        Random random = new Random();
        double start = -1.3;
        double end = 1.3;
        double varX = start + (random.nextDouble() * (end - start));
        double varZ = start + (random.nextDouble() * (end - start));


        location.setX( location.getX() + varX);
        location.setY(location.getY()-0.3);
        location.setZ(location.getZ() +varZ);

        buildArmorStand( entity, location, ChatColorUtil.boldText("-"+ lastDamage , ChatColor.RED));
    }

    private void buildArmorStand(Entity entity,Location location , String damage){

        BukkitScheduler scheduler =  Bukkit.getServer().getScheduler();

        World world = entity.getWorld();
        ArmorStand armorStand = (ArmorStand) world.spawnEntity( location, EntityType.ARMOR_STAND);
        armorStand.setInvulnerable(true);
        armorStand.setCollidable(false);
        armorStand.setMetadata("DAMAGE_INDICATOR", new FixedMetadataValue(RPGCore.getPlugin(),"DAMAGE_INDICATOR"));
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.setCollidable(false);

        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(damage);

        damageIndicatorsEntities.put(armorStand.getUniqueId(),armorStand);

        scheduler.runTaskTimer(RPGCore.getPlugin(), () -> {
            Location loc = armorStand.getLocation();
            loc.setY(loc.getY() -0.1);
            armorStand.teleport(loc);
        } ,0 , 1);

        scheduler.scheduleSyncDelayedTask(RPGCore.getPlugin(),() ->{
            armorStand.remove();
            damageIndicatorsEntities.remove(armorStand.getUniqueId());
        } , 40);
    }


}
