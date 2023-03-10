package com.rpg.packages;

import com.rpg.RPGCore;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class HealthBossBar {
    private static final HashMap<UUID,BossBar> playerTargetBossBar = new HashMap<>();
    public static void openBossHealthBar(Player player, Entity target){

        if(excludedEntities(target))
            return;

        if(target instanceof LivingEntity)
          createBossBar(target.getName(),BarColor.RED,BarStyle.SOLID,player,(LivingEntity) target);
    }

    private static boolean excludedEntities(Entity target){
        if(target instanceof ArmorStand)
            return  true;

        if(target instanceof EnderDragon)
            return  true;

        if(target instanceof Wither)
            return  true;

        return  false;
    }
    static HashMap<UUID,Integer> taskIDByPlayer = new HashMap<>();
    private static void createBossBar(String title, BarColor barColor, BarStyle barStyle, Player player, LivingEntity entity){

       if(playerTargetBossBar.get(player.getUniqueId()) != null)
           clearBossBar(playerTargetBossBar.get(player.getUniqueId()), player);

       double progressbar = entity.getHealth() / Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();


        BossBar bb = Bukkit.createBossBar( ChatColorUtil.boldText(title, ChatColor.RED), barColor, barStyle, BarFlag.values());
        bb.addPlayer(player);
        bb.setProgress(progressbar);
        bb.setVisible(true);
        playerTargetBossBar.put(player.getUniqueId(), bb);

        long startTime = System.currentTimeMillis();

        if( taskIDByPlayer.get(player.getUniqueId()) != null && taskIDByPlayer.get(player.getUniqueId()) != 0)
            Bukkit.getScheduler().cancelTask(taskIDByPlayer.get(player.getUniqueId()));

        taskIDByPlayer.put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(RPGCore.getPlugin(), () -> {

            long endTime = System.currentTimeMillis();
            long secondsRemaning = Math.round(((startTime+11000) - endTime)/1000);

            if((endTime - startTime) > 10000){
                clearBossBar(bb, player);
                Bukkit.getScheduler().cancelTask(taskIDByPlayer.get(player.getUniqueId()));
                taskIDByPlayer.remove(player.getUniqueId());
            }
            if (!entity.isDead()) {
                bb.setTitle(ChatColorUtil.boldText(title, ChatColor.RED) + " - " + secondsRemaning + TranslateUtil.getKey("SEC"));
                bb.setProgress(entity.getHealth() / Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            } else {
                clearBossBar(bb, player);
                Bukkit.getScheduler().cancelTask(taskIDByPlayer.get(player.getUniqueId()));
                taskIDByPlayer.remove(player.getUniqueId());
            }

        }, 0L, 20L));
    }

  private static void clearBossBar(BossBar bossBar, Player player){
        bossBar.setProgress(0);
        bossBar.removeAll();
        bossBar.setVisible(false);
        playerTargetBossBar.remove(player.getUniqueId(), bossBar);
  }

  public static void clearAllBossBar(){

      playerTargetBossBar.forEach( (uuid, bossBar) -> {
          bossBar.setProgress(0);
          bossBar.removeAll();
          bossBar.setVisible(false);
      });
      playerTargetBossBar.clear();
  }

}
